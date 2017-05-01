package com.aiblockchain.server.websocket;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import org.apache.log4j.Logger;


/**
 * WebSocketServerInitializer - intializes the channel.
 * @author Athi
 *
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
	private Logger logger = Logger.getLogger(WebSocketServerInitializer.class);
	
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
    	logger.info("Initializing Channel : " + ch.toString());
    	System.out.println("Initializing Channel : " + ch.toString());
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast("AIBChandler", new AIBlockChainServerHandler());        
        //pipeline.addLast("handler", new BlockTickerServerHandler());
    }
}
