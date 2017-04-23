package com.aiblockchain.server.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import com.aiblockchain.server.websocket.WebSocketServerInitializer;

/**
 * WebSocketServer - web socket server.
 * @author Athi
 *
 */
public final class WebSocketServer {

    private static final int DEFAULT_PORT = 8083;
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);    

    public static void main(String[] args) throws Exception {
        int port = 0;
        int noOfParams = args.length;
        System.out.println ("Main() Args : " + noOfParams + ", port : " + args[0]);
        
        if (noOfParams > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = DEFAULT_PORT;
        }
        
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
}
