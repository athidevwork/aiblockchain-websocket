package com.aiblockchain.server.websocket;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

import org.apache.log4j.Logger;


/**
 * WebSocketServerInitializer - intializes the channel.
 * @author Athi
 *
 */
public class WebSocketSslServerInitializer extends ChannelInitializer<SocketChannel> {
	private Logger logger = Logger.getLogger(WebSocketSslServerInitializer.class);
	
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
    	logger.info("Initializing Ssl Channel : " + ch.toString());
        ChannelPipeline pipeline = ch.pipeline();

        /*SSLEngine engine = WebSocketSslServerSslContext.getInstance().serverContext().createSSLEngine();
        engine.setUseClientMode(false);*/
        
        //pipeline.addLast("ssl", new SslHandler(engine));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("decoder", new HttpRequestDecoder());        
        //pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        //pipeline.addLast(new WebSocketServerCompressionHandler());
        //pipeline.addLast("handler", new WebSocketSslServerHandler());  
        //pipeline.addLast("AIBCOutboundhandler", new AIBlockChainServerOutboundHandler());
    }
}
