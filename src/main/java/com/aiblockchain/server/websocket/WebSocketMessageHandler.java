package com.aiblockchain.server.websocket;

import io.netty.channel.ChannelHandlerContext;

/**
 * WebSocketMessageHandler
 * Created by Athi
 */
public interface WebSocketMessageHandler {
   public String handleMessage(ChannelHandlerContext ctx, String frameText);
}
