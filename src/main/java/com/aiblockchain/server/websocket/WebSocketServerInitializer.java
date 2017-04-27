package com.aiblockchain.server.websocket;

import java.util.logging.Logger;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;


/**
 * WebSocketServerInitializer - intializes the channel.
 * @author Athi
 *
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
	private Logger logger = Logger.getLogger("WebSocketServerInitializer");
	
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
    	logger.info("Initializing Channel : " + ch.toString());
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast("AIBChandler", new AIBlockChainServerHandler());        
        //pipeline.addLast("handler", new BlockTickerServerHandler());
    }
}
