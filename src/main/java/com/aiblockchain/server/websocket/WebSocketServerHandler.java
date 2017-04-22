package com.aiblockchain.server.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.json.JSONObject;

import com.aiblockchain.server.websocket.dto.Response;
import com.aiblockchain.server.websocket.entity.Client;
import com.aiblockchain.server.websocket.service.MessageService;
import com.aiblockchain.server.websocket.service.RequestService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.netty.handler.codec.http.HttpHeaderNames.HOST;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    // websocket uri
    private static final String WEBSOCKET_PATH = "/websocket";

    // ChannelGroup
    private static Map<Integer, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

    // code
    private static final String HTTP_REQUEST_STRING = "request";

    private Client client = null;

    private WebSocketServerHandshaker handshaker;

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // Handle a bad request.
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.method() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        if ("/favicon.ico".equals(req.uri()) || ("/".equals(req.uri()))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.uri());
        Map<String, List<String>> parameters = queryStringDecoder.parameters();

        if (parameters.size() == 0 || !parameters.containsKey(HTTP_REQUEST_STRING)) {
            System.err.printf(HTTP_REQUEST_STRING + "Request string not found");
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }

        client = RequestService.clientRegister(parameters.get(HTTP_REQUEST_STRING).get(0));
        if (client.getRoomId() == 0) {
            System.err.printf("client room not found");
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }

        //ChannelGroup
        if (!channelGroupMap.containsKey(client.getRoomId())) {
            channelGroupMap.put(client.getRoomId(), new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        }
        // channelGroupMap
        channelGroupMap.get(client.getRoomId()).add(ctx.channel());

        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, true);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            ChannelFuture channelFuture = handshaker.handshake(ctx.channel(), req);

            // Check for success
            if (channelFuture.isSuccess()) {
                if (client.getId() == 0) {
                    System.out.println(ctx.channel() + " Client Channel : ");
                    return;
                }
            }
        }
    }

    private void broadcast(ChannelHandlerContext ctx, WebSocketFrame frame) {

        if (client.getId() == 0) {
            Response response = new Response(1001, "broadcast msg: ");
            String msg = new JSONObject(response).toString();
            ctx.channel().write(new TextWebSocketFrame(msg));
            return;
        }

        String request = ((TextWebSocketFrame) frame).text();
        System.out.println(" Channel : " + ctx.channel() + request);

        Response response = MessageService.sendMessage(client, request);
        String msg = new JSONObject(response).toString();
        if (channelGroupMap.containsKey(client.getRoomId())) {
            channelGroupMap.get(client.getRoomId()).writeAndFlush(new TextWebSocketFrame(msg));
        }

    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }

        broadcast(ctx, frame);
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpHeaderUtil.setContentLength(res, res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaderUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Handler added for Remote Address: " 
        					+ incoming.remoteAddress() + " ---" + incoming.id());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (client != null && channelGroupMap.containsKey(client.getRoomId())) {
            channelGroupMap.get(client.getRoomId()).remove(ctx.channel());
            System.out.println("Handler removed for Remote Address: " 
					+ ctx.channel().remoteAddress() + " ---" + ctx.channel().id());
        }
    }

    private static String getWebSocketLocation(FullHttpRequest req) {
        String location = "ws://" + req.headers().get(HOST) + WEBSOCKET_PATH;
        System.out.println("\nWeb Socket Context = " + location + "\n");
        return location;
    }
}
