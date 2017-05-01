package com.aiblockchain.server.websocket;


import com.aiblockchain.client.AIBlockChainListenerClient;
import com.aiblockchain.client.AbstractAPIAdapter;
import com.aiblockchain.server.StringUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;


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
  private static Logger logger = Logger.getLogger(WebSocketServer.class);
  // the AI blockchain listener client 
  private static AIBlockChainListenerClient aiBlockChainListenerClient;
  
  public WebSocketServer(final int port) {
    logger.info("initializing the AI Block Chain Web Socket Server ...");
    System.out.println(StringUtils.log(logger) + "initializing the AI Block Chain Web Socket Server ...");
    
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
      System.out.println(StringUtils.log(logger) + "Web Socket Server started in port: " + port);
      ch.closeFuture().sync();
    } catch (InterruptedException ex) {
      // ignore
    	logger.info("Got an interruped exception" + ex);
    	System.out.println(StringUtils.log(logger) + "Got an interruped exception" + ex);
    } finally {
      logger.info("Web Socket Server shutdown started");
      System.out.println(StringUtils.log(logger) + "Web Socket Server shutdown started");
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  /** Gets the the AI blockchain listener client singleton instance.
   * 
   * @return the the AI blockchain listener client
   */
  public static AIBlockChainListenerClient getAIBlockChainListenerClient() {
    return aiBlockChainListenerClient;
  }
  
  /** Initialize the AI blockchain listener client from the software agent system.
   * 
   * @param apiAdapter the API adapter supplied by the software agent system
   */
  public static void intializeAIBlockChainListenerClient(final AbstractAPIAdapter apiAdapter) {
    //Preconditions
    assert apiAdapter != null : "apiAdapter must not be null";
    
    // construct the singleton instance of the listener client
    aiBlockChainListenerClient = AIBlockChainListenerClient.getInstance();
    // insert the API adapter dependency
    aiBlockChainListenerClient.setApiAdapter(apiAdapter);
    logger.info("the AI blockchain listener client is initialized");
    System.out.println(StringUtils.log(logger) + "the AI blockchain listener client is initialized");
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
