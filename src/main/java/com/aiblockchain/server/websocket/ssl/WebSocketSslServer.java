/**
 * 
 */
package com.aiblockchain.server.websocket.ssl;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.aiblockchain.server.StringUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @author Athi
 *
 */
public class WebSocketSslServer {
	private final int port;
	public static final int DEFAULT_PORT = 20001;
	private static Logger logger = Logger.getLogger(WebSocketSslServer.class);

	public WebSocketSslServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new WebSocketSslServerInitializer());
			
			Channel ch = b.bind(port).sync().channel();
			System.out.println("Web socket server started at port " + port + '.');
			System.out.println("For websocket test, Open your browser and navigate to https://localhost:" + port + '/');
			ch.closeFuture().sync();
		} catch (InterruptedException ex) {
			// ignore
			logger.info("Got an interruped exception" + ex);
			System.out.println(StringUtils.log(logger) + "Got an interruped exception" + ex);
		} finally {
			logger.info("Web Socket Server shutdown started");
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			logger.info("Main() Args : " + args + ", port : " + args[0]);
			port = Integer.parseInt(args[0]);
		} else {
			logger.info("Main() Args : " + args);
			//port = 8443;
			port = DEFAULT_PORT;
		}

		/*
		 * Set properties in code or as parameter for java -Djavax.net.ssl.keyStore=path/to/keystore.jks
		 */
		
		/*String keyStoreFilePath = SystemPropertyUtil.get("keystore.file.path");
		if (keyStoreFilePath == null || keyStoreFilePath.isEmpty()) {
			System.out.println("ERROR: System property keystore.file.path not set. Exiting now!");
			System.exit(1);
		}

		String keyStoreFilePassword = SystemPropertyUtil.get("keystore.file.password");
		if (keyStoreFilePassword == null || keyStoreFilePassword.isEmpty()) {
			System.out.println("ERROR: System property keystore.file.password not set. Exiting now!");
			System.exit(1);
		}*/

		PropertyConfigurator.configure("src/main/resources/log4j.properties");
		
		new WebSocketSslServer(port).run();
	}
}
