package com.aiblockchain.server.websocket.ssl;


import javax.net.ssl.SSLEngine;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;


/**
 * WebSocketServerInitializer - intializes the channel.
 * @author Athi
 *
 */
public class WebSocketSslServerInitializer extends ChannelInitializer<SocketChannel> {
	private Logger logger = Logger.getLogger(WebSocketSslServerInitializer.class);
	
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
    	logger.info("Initializing server Ssl Channel : " + ch.toString());
        ChannelPipeline pipeline = ch.pipeline();

        SSLEngine engine = WebSocketSslServerContextFactory.getContext().createSSLEngine();
        engine.setUseClientMode(false);
        
        pipeline.addFirst("ssl", new SslHandler(engine));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("decoder", new HttpRequestDecoder());        
        //pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        //pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast("sslhandler", new WebSocketSslServerHandler());  
        //pipeline.addLast("AIBCOutboundhandler", new AIBlockChainServerOutboundHandler());
    }
}
