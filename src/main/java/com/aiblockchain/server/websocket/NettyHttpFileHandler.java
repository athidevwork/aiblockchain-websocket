package com.aiblockchain.server.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpChunkedInput;
import static io.netty.handler.codec.http.HttpHeaderNames.CACHE_CONTROL;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.DATE;
import static io.netty.handler.codec.http.HttpHeaderNames.EXPIRES;
import static io.netty.handler.codec.http.HttpHeaderNames.IF_MODIFIED_SINCE;
import static io.netty.handler.codec.http.HttpHeaderNames.LAST_MODIFIED;
import static io.netty.handler.codec.http.HttpHeaderNames.LOCATION;
import io.netty.handler.codec.http.HttpHeaderUtil;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;
import javax.activation.MimetypesFileTypeMap;
import org.apache.log4j.Logger;

/**
 * Created by Athi.
 */
public class NettyHttpFileHandler {
   private static final Logger logger = Logger.getLogger(NettyHttpFileHandler.class);

   public    static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
   public    static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
   public    static final int HTTP_CACHE_SECONDS = 60;
   private   static final Object _lock = new Object();
   protected static MimetypesFileTypeMap mimeTypesMap;

   private String staticFileDir = "./webapp";

   // all methods static, no need for constructor
   public NettyHttpFileHandler() {
      synchronized(_lock) {
         if (mimeTypesMap == null) {
            InputStream is = this.getClass().getResourceAsStream("/META-INF/server.mime.types");
            if (is != null) {
               mimeTypesMap = new MimetypesFileTypeMap(is);
            } else {
               logger.error("Cannot load mime types!");
            }
         }
      }
   }
   
   public void sendFile(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
      // handle static files
      final String uri = req.uri();
      final String path = sanitizeUri(uri);

      if (path == null) {
         sendError(ctx, HttpResponseStatus.FORBIDDEN);
         return;
      }

      File file = new File(path);
      if ((!file.exists()) && "/index.html".equals(uri)) {
         file = new File(sanitizeUri("/index.html"));
      }

      if (!file.exists() || file.isHidden() || !file.exists() || file.isDirectory()) {
         sendError(ctx, HttpResponseStatus.NOT_FOUND);
         return;
      }

      if (!file.isFile()) {
         sendError(ctx, HttpResponseStatus.FORBIDDEN);
         return;
      }

      String contentType = mimeTypesMap.getContentType(file.getPath());
      if ("application/octet-stream".equals(contentType)) {
         file = new File(sanitizeUri("/index.html"));
      }

      // Cache Validation
      String ifModifiedSince = req.headers().getAndConvert(IF_MODIFIED_SINCE);
      if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
         SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
         Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);

         // Only compare up to the second because the datetime format we send to the client
         // does not have milliseconds
         long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
         long fileLastModifiedSeconds = file.lastModified() / 1000;
         if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
            sendNotModified(ctx);
            return;
         }
      }

      RandomAccessFile raf;
      try {
         raf = new RandomAccessFile(file, "r");
      } catch (FileNotFoundException ignore) {
         sendError(ctx, HttpResponseStatus.NOT_FOUND);
         return;
      }

      long fileLength = raf.length();

      HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
      HttpHeaderUtil.setContentLength(response, fileLength);
      setContentTypeHeader(response, file);
      setDateAndCacheHeaders(response, file);
      
      if (HttpHeaderUtil.isKeepAlive(req)) {
         response.headers().set(CONNECTION, KEEP_ALIVE);
      }

      // Write the initial line and the header.
      ctx.write(response);

      // Write the content.
      ChannelFuture sendFileFuture;
      ChannelFuture lastContentFuture;
      if (ctx.pipeline().get(SslHandler.class) == null) {
         sendFileFuture = ctx.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength), ctx.newProgressivePromise());
         // Write the end marker.
         lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
      } else {
         sendFileFuture = ctx.write(new HttpChunkedInput(new ChunkedFile(raf, 0, fileLength, 8192)),
                         ctx.newProgressivePromise());
         // HttpChunkedInput will write the end marker (LastHttpContent) for us.
         lastContentFuture = sendFileFuture;
      }

      sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
         @Override
         public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
            if (total < 0) { // total unknown
               logger.error(future.channel() + " Transfer progress: " + progress);
            } else {
               logger.error(future.channel() + " Transfer progress: " + progress + " / " + total);
            }
         }

         @Override
         public void operationComplete(ChannelProgressiveFuture future) {
            logger.error(future.channel() + " Transfer complete.");
         }
      });

      // Decide whether to close the connection or not.
      if (!HttpHeaderUtil.isKeepAlive(req)) {
         // Close the connection when the whole content is written out.
         lastContentFuture.addListener(ChannelFutureListener.CLOSE);
      }
   }

   public void sendRedirect(ChannelHandlerContext ctx, String newUri) {
      FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
      response.headers().set(LOCATION, newUri);

      // Close the connection as soon as the error message is sent.
      ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
   }

   public void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
      FullHttpResponse response = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8));
      response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

      // Close the connection as soon as the error message is sent.
      ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
   }

   /**
    * When file timestamp is the same as what the browser is sending up, send a "304 Not Modified"
    *
    * @param ctx
    *            Context
    */
   public void sendNotModified(ChannelHandlerContext ctx) {
      FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_MODIFIED);
      setDateHeader(response);

      // Close the connection as soon as the error message is sent.
      ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
   }

   /**
    * Sets the Date header for the HTTP response
    *
    * @param response
    *            HTTP response
    */
   public void setDateHeader(FullHttpResponse response) {
      SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
      dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

      Calendar time = new GregorianCalendar();
      response.headers().set(DATE, dateFormatter.format(time.getTime()));
   }

   /**
    * Sets the Date and Cache headers for the HTTP Response
    *
    * @param response
    *            HTTP response
    * @param fileToCache
    *            file to extract content type
    */
   public void setDateAndCacheHeaders(HttpResponse response, File fileToCache) {
      SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
      dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

      // Date header
      Calendar time = new GregorianCalendar();
      response.headers().set(DATE, dateFormatter.format(time.getTime()));

      // Add cache headers
      time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
      response.headers().set(EXPIRES, dateFormatter.format(time.getTime()));
      response.headers().set(CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
      response.headers().set(LAST_MODIFIED, dateFormatter.format(new Date(fileToCache.lastModified())));
   }

   /**
    * Sets the content type header for the HTTP Response
    *
    * @param response
    *            HTTP response
    * @param file
    *            file to extract content type
    */
   public void setContentTypeHeader(HttpResponse response, File file) {
      response.headers().set(CONTENT_TYPE, mimeTypesMap.getContentType(file.getPath()));
   }

   public void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
      if (res.status().code() != 200) {
         ByteBuf f = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
         res.content().clear();
         res.content().writeBytes(f);
         f.release();
      }

      //HttpHeaders.setContentLength(res, res.content().readableBytes());
      res.headers().addInt("Content-Length", res.content().readableBytes());
      ChannelFuture f1;
      f1 = ctx.channel().writeAndFlush(res);

      if (!HttpHeaderUtil.isKeepAlive(req) || res.status().code() != 200) {
         f1.addListener(ChannelFutureListener.CLOSE);
      }
   }

   private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

   public String sanitizeUri(String uri) {
      // Decode the path.
      try {
         uri = URLDecoder.decode(uri, "UTF-8");
      } catch (UnsupportedEncodingException e) {
         throw new Error(e);
      }

      if (uri.isEmpty() || uri.charAt(0) != '/') {
         return null;
      }

      // Convert file separators.
      uri = uri.replace('/', File.separatorChar);

      // Simplistic dumb security check.
      // You will have to do something serious in the production environment.
      if (uri.contains(File.separator + '.') ||
            uri.contains('.' + File.separator) ||
            uri.charAt(0) == '.' || uri.charAt(uri.length() - 1) == '.' ||
            INSECURE_URI.matcher(uri).matches()) {
         return null;
      }

      // Convert to absolute path.
      String path = staticFileDir + uri;
      logger.debug("current dir is " + Paths.get(".").toAbsolutePath().normalize().toString());
      logger.debug("path to current file is '" + path + "'");
      return path;
   }}
