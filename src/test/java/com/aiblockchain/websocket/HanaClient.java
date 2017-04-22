/**
 * 
 */
package com.aiblockchain.websocket;

import java.net.URI;
import java.net.URISyntaxException;

import com.aiblockchain.server.websocket.WebsocketClientEndpoint;

/**
 * @author Athi
 *
 */
public class HanaClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
			final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8083/wsticker"));
            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("{\"command\":\"startblock\", \"blockNumber\":\"1\"}");

            // wait for messages from websocket
            Thread.sleep(50000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
	}

}
