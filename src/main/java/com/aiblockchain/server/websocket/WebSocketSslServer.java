/**
 * 
 */
package com.aiblockchain.server.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @author Athi
 *
 */
public class WebSocketSslServer {
	private final int port;

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
			.childHandler(new WebSocketSslServerInitializer());

			Channel ch = b.bind(port).sync().channel();
			System.out.println("Web socket server started at port " + port + '.');
			System.out.println("Open your browser and navigate to https://localhost:" + port + '/');
			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8443;
		}

		String keyStoreFilePath = SystemPropertyUtil.get("keystore.file.path");
		if (keyStoreFilePath == null || keyStoreFilePath.isEmpty()) {
			System.out.println("ERROR: System property keystore.file.path not set. Exiting now!");
			System.exit(1);
		}

		String keyStoreFilePassword = SystemPropertyUtil.get("keystore.file.password");
		if (keyStoreFilePassword == null || keyStoreFilePassword.isEmpty()) {
			System.out.println("ERROR: System property keystore.file.password not set. Exiting now!");
			System.exit(1);
		}

		new WebSocketSslServer(port).run();
	}
}
