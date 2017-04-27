package com.aiblockchain.server.websocket;

import java.util.logging.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/** The AI blockchain system first calls intializeAIBlockChainListenerClient to create the singleton listener object and to
 * inject a dependency for its API adapter. 
 * 
 * Then the system constructs the WebSocketServer instance with the API port number. This constructor does not return until 
 * the application is terminated, so the system uses a separate dedicated thread for that purpose.
 * 
 * @author Athi, reed
 */
public final class WebSocketServer {

  public static final int DEFAULT_PORT = 20000;
  private static Logger logger = Logger.getLogger("WebSocketServer");
  
  public WebSocketServer(final int port) {
    logger.info("initializing the AI Block Chain Web Socket Server ...");
    
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
              .channel(NioServerSocketChannel.class)
              .handler(new LoggingHandler(LogLevel.INFO))
              .childHandler(new WebSocketServerInitializer());

      Channel ch = b.bind(port).sync().channel();
      logger.info("Web Socket Server started in port: " + port);
      ch.closeFuture().sync();
    } catch (InterruptedException ex) {
      // ignore
    	logger.info("Got an interruped exception" + ex);
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
