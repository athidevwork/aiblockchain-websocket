/**
 * 
 */
package com.aiblockchain.client.secure;

import java.net.URI;

import javax.net.ssl.SSLEngine;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslHandler;

/**
 * @author Athi
 *
 */
public class WebSocketClientRunner {
	private final URI uri;

	public WebSocketClientRunner(URI uri) {
		this.uri = uri;
	}

	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			// Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
			// If you change it to V00, ping is not supported and remember to change
			// HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
			final WebSocketClientHandler handler =
					new WebSocketClientHandler(
							WebSocketClientHandshakerFactory.newHandshaker(
									uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders()));

			final String protocol = uri.getScheme();
			int defaultPort;
			ChannelInitializer<SocketChannel> initializer;

			// Normal WebSocket
			if ("ws".equals(protocol)) {
				initializer = new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
						.addLast("http-codec", new HttpClientCodec())
						.addLast("aggregator", new HttpObjectAggregator(8192))
						.addLast("ws-handler", handler);
					}
				};

				defaultPort = 80;
				// Secure WebSocket
			} else if ("wss".equals(protocol)) {
				initializer = new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						SSLEngine engine = WebSocketSslClientContextFactory.getContext().createSSLEngine();
						engine.setUseClientMode(true);

						ch.pipeline()
						.addFirst("ssl", new SslHandler(engine))
						.addLast("http-codec", new HttpClientCodec())
						.addLast("aggregator", new HttpObjectAggregator(8192))
						.addLast("ws-handler", handler);
					}
				};

				defaultPort = 443;
			} else {
				throw new IllegalArgumentException("Unsupported protocol: " + protocol);
			}

			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.handler(initializer);

			int port = uri.getPort();
			// If no port was specified, we'll try the default port: https://tools.ietf.org/html/rfc6455#section-1.7
			if (uri.getPort() == -1) {
				port = defaultPort;
			}

			System.out.println("Uri = " + uri);
			System.out.println("Connecting to " + uri.getHost() + ":" + uri.getPort());
			Channel ch = b.connect(uri.getHost(), port).sync().channel();
			handler.handshakeFuture().sync();

            System.out.println("WebSocket Client sending message"); 
            for (int i = 0; i < 1000; i++) { 
                ch.write(new TextWebSocketFrame("Client Message #" + i)); 
            } 
            ch.flush();
            
            // Ping 
            System.out.println("WebSocket Client sending ping"); 
            ch.write(new PingWebSocketFrame(Unpooled.copiedBuffer(new byte[]{1, 2, 3, 4, 5, 6}))); 
            ch.flush();
            
            // Close 
            System.out.println("WebSocket Client sending close"); 
            ch.write(new CloseWebSocketFrame()); 
            ch.flush();
            
            // WebSocketClientHandler will close the connection when the server 
            // responds to the CloseWebSocketFrame. 
            ch.closeFuture().sync(); 
            
			/*BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				String msg = console.readLine();
				if (msg == null) {
					break;
				} else if ("bye".equals(msg.toLowerCase())) {
					ch.writeAndFlush(new CloseWebSocketFrame());
					ch.closeFuture().sync();
					break;
				} else if ("ping".equals(msg.toLowerCase())) {
					WebSocketFrame frame = new PingWebSocketFrame(Unpooled.copiedBuffer(new byte[]{8, 1, 8, 1}));
					ch.writeAndFlush(frame);
				} else {
					WebSocketFrame frame = new TextWebSocketFrame(msg);
					ch.writeAndFlush(frame);
				}
			}*/
		} finally {
			group.shutdownGracefully();
		}
	}
}
