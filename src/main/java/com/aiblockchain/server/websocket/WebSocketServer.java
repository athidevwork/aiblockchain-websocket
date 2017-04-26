package com.aiblockchain.server.websocket;



import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;

public final class WebSocketServer {

  public static final int DEFAULT_PORT = 20000;
  private static Logger logger = Logger.getLogger(WebSocketServer.class);

  public WebSocketServer(final int port) {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
              .channel(NioServerSocketChannel.class)
              .handler(new LoggingHandler(LogLevel.INFO))
              .childHandler(new WebSocketServerInitializer());

      Channel ch = b.bind(port).sync().channel();
      ch.closeFuture().sync();
      logger.info("Web Socket Server started");
      System.out.println("Web Socket server started in port: " + port);
    } catch (InterruptedException ex) {
      // ignore
      System.out.println("Got an interruped exception" + ex);
    } finally {
      logger.info("Web Socket Server shutdown started");
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    int port = 0;
    int noOfParams = args.length;

    if (noOfParams > 0) {
      logger.info("Main() Args : " + noOfParams + ", port : " + args[0]);
      port = Integer.parseInt(args[0]);
    } else {
      logger.info("Main() Args : " + noOfParams);
      port = DEFAULT_PORT;
    }

    new WebSocketServer(port);
  }
}
