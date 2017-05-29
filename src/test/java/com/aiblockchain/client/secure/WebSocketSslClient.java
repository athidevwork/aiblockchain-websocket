package com.aiblockchain.client.secure;

import java.net.URI;

/**
 * @author Athi
 *
 */
public class WebSocketSslClient {
	private WebSocketSslClient() {
	}

	public static void main(String... args) throws Exception {
		URI uri;
		if (args.length > 0) {
			uri = new URI(args[0]);
		} else {
			uri = new URI("wss://localhost:20443/websocket");
		}

		new WebSocketClientRunner(uri).run();
	}
}
