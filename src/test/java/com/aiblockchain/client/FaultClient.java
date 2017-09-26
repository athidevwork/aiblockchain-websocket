/**
 * 
 */
package com.aiblockchain.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.aiblockchain.server.websocket.fault.FaultData;
import com.google.gson.Gson;

/**
 * @author Athi
 *
 */
public class FaultClient {
	private static final Logger LOGGER = Logger.getLogger(FaultClient.class);

	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 20000;
	private static Gson gson = new Gson();	
	private static Object lock = new Object();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = null;
		int noOfParams = args.length;

		if (noOfParams > 1) {
			LOGGER.info("Main() Args : " + noOfParams + ", host : " + args[0] + ", port : " + args[1]);
			url = "ws://" + args[0] + ":" + args[1] + "/wsticker";
		} else if (noOfParams > 0) {
			LOGGER.info("Main() Args : " + noOfParams + ", host : " + args[0]);
			url = "ws://" + args[0] + ":" + DEFAULT_PORT + "/wsticker";
		} else {
			LOGGER.info("Main() Args : " + noOfParams);
			url = "ws://" + DEFAULT_HOST + ":" + DEFAULT_PORT + "/wsticker";
		}

		LOGGER.info("Fault Client Endpoint = " + url);
		try {
			final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(url));
			
			// add listener
			clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
				public void handleMessage(String message) {										
					LOGGER.debug("Received Message from server: " + message);
					FaultData faultResponse = gson.fromJson(message, FaultData.class);
					LOGGER.debug("Fault Response: " + faultResponse);
					synchronized (lock) {
						lock.notify();// Will wake up lock.wait()
					}
				}
			});
			
			// send message to websocket
			JSONObject faultRequest = new JSONObject();
    		faultRequest.put("command", "add");
    		JSONObject faultDataJO = new JSONObject();
    		
    		JSONArray txnIds = new JSONArray();
    		txnIds.put(getHash("value1"));
    		txnIds.put(getHash("value2"));
    		txnIds.put(getHash("value3"));
    		txnIds.put(getHash("String4"));
    		faultDataJO.put("txnids", txnIds);
    		faultRequest.put("faultData", faultDataJO);
    		String newFaultMsg = faultRequest.toString();
			//LOGGER.info("sending fault request: " + newFaultMsg);
			clientEndPoint.sendMessage(newFaultMsg);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		synchronized (lock) {
		    try {
				lock.wait();
			} catch (InterruptedException e) {
				System.err.println("InterruptedException exception: " + e.getMessage());
				e.printStackTrace();
			} // Will block until lock.notify() is called on another thread.
		}
		/*try {
			Object lock = new Object();
			synchronized (lock) {
				while (true) {
					lock.wait();
				}
			}
		} catch (InterruptedException ex) {
			System.err.println("InterruptedException exception: " + ex.getMessage());
		}*/
		LOGGER.info("End of Fault Client program");
	}

	private static String getHash(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		messageDigest.update(str.getBytes());
	    byte[] hash = messageDigest.digest();
		StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < hash.length; ++i) {
	        String hex = Integer.toHexString(hash[i]);
	        if (hex.length() == 1) {
	            sb.append(0);
	            sb.append(hex.charAt(hex.length() - 1));
	        } else {
	            sb.append(hex.substring(hex.length() - 2));
	        }
	    }
	    return sb.toString();
	}
}
