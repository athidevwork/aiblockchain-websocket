/**
 * 
 */
package com.aiblockchain.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.aiblockchain.model.hana.HanaBlockInfo;
import com.aiblockchain.model.hana.HanaTransactionInfo;
import com.aiblockchain.model.hana.HanaTransactionInputInfo;
import com.aiblockchain.model.hana.HanaTransactionOutputInfo;
import com.aiblockchain.server.websocket.WebsocketClientEndpoint;
import com.google.gson.Gson;

/**
 * @author Athi
 *
 */
public class HanaClient {
	static Logger logger = Logger.getLogger("HanaClient");
	
	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 20000;
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {			
            // stateless JSON serializer/deserializer
            Gson gson = new Gson();		
		   
            String url = null;
            int noOfParams = args.length;

            if (noOfParams > 1) {
                logger.info("Main() Args : " + noOfParams + ", host : " + args[0] + ", port : " + args[1]);
                url = "ws://" + args[0] + ":" + args[1] + "/wsticker";
            } else if (noOfParams > 0) {
              logger.info("Main() Args : " + noOfParams + ", host : " + args[0]);
              url = "ws://" + args[0] + ":" + DEFAULT_PORT + "/wsticker";
            } else {
              logger.info("Main() Args : " + noOfParams);
              url = "ws://" + DEFAULT_HOST + ":" + DEFAULT_PORT + "/wsticker";
            }
            
            System.out.println("Client Endpoint = " + url);
            // open websocket
			//final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8083/wsticker"));
			//final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:20000/wsticker"));
			//final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://aicoin.dynds.org:20000/wsticker"));
			final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(url));
			
			// add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    //System.out.println(message);
                    JSONObject jsonObj = new JSONObject(message);
                    
                   if (jsonObj.has("hanaObjectType")) {
	                    String objectType = jsonObj.getString("hanaObjectType");
	                    //System.out.println("Object type = " + objectType);
	                    //JSONObject hanaInfo = jsonObj.getJSONObject("hanaObject");
	                    //System.out.println("Object type = " + hanaInfo);
	                    String hanaInfo = jsonObj.getString("hanaObject");
	                    //System.out.println("Object = " + hanaInfo);
	                    
	                    //switch based on type to map to appropriate data sent over the wire.
	                    switch (objectType) {
		                    case "HanaBlockInfo":
								HanaBlockInfo hanaBlockInfo = gson.fromJson(hanaInfo.toString(), HanaBlockInfo.class);
								System.out.println("Block = " + hanaBlockInfo);		                    	
	                    		break;
		                    case "HanaTransactionInfo":
		                    	HanaTransactionInfo hanaTransInfo = gson.fromJson(hanaInfo.toString(), HanaTransactionInfo.class);
								System.out.println("Transaction = " + hanaTransInfo);		                    	
	            				break;
		                    case "HanaTransactionInputInfo":
		                    	JSONArray jsonArray = new JSONArray(hanaInfo);
		                    	if (jsonArray.length() > 0) {
		                    		for (int i = 0; i < jsonArray.length(); i++) {
			                        	JSONObject jsonObject = jsonArray.getJSONObject(i);
			                        	String jsonStr = jsonObject.toString();
				                    	HanaTransactionInputInfo hanaTransInputInfo = gson.fromJson(jsonStr, HanaTransactionInputInfo.class);
										System.out.println("Transaction Input = " + hanaTransInputInfo);		                    			
		                    		}
		                    	}
	            				break;	 
		                    case "HanaTransactionOutputInfo":
		                    	JSONArray jsonofArray = new JSONArray(hanaInfo);
		                    	if (jsonofArray.length() > 0) {
		                    		for (int i = 0; i < jsonofArray.length(); i++) {
			                        	JSONObject jsonObject = jsonofArray.getJSONObject(i);
			                        	String jsonStr = jsonObject.toString();
			                        	HanaTransactionOutputInfo hanaTransOutputInfo = gson.fromJson(jsonStr, HanaTransactionOutputInfo.class);
										System.out.println("Transaction Output = " + hanaTransOutputInfo);		                    			
		                    		}
		                    	}	                    	
	            				break;		            				
	            			default:
	            				break;
	                    }	
                    }
                    else {
                    	System.out.println(jsonObj);
                    }
                   System.out.println("*******************************************");
                }
            });

            // send message to websocket
            //clientEndPoint.sendMessage("{\"command\":\"startblock\", \"blockNumber\":\"1\"}");
            clientEndPoint.sendMessage("{\"command\":\"getnewblock\", \"blockNumber\":\"1\", \"numberOfBlocks\":\"2\"}");

            // wait for messages from websocket
            Thread.sleep(50000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
	}
}
