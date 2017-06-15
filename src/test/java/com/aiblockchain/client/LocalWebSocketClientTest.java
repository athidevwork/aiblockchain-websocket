/**
 *
 */
package com.aiblockchain.client;

import com.aiblockchain.model.hana.HanaBlockInfo;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaTransactionInfo;
import com.aiblockchain.model.hana.HanaTransactionInputInfo;
import com.aiblockchain.model.hana.HanaTransactionOutputInfo;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.model.hana.HanaItems.HanaTransactionItem;
import com.aiblockchain.model.hana.util.HanaUtil;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Athi
 *
 */
public class LocalWebSocketClientTest {
	private static final Logger LOGGER = Logger.getLogger(LocalWebSocketClientTest.class);

	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 20000;	
	
	private static Gson gson = new Gson();		
	private static HanaUtil util = new HanaUtil();
	private static int transactionCount = 0;
	
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

		LOGGER.info("Client Endpoint = " + url);
		
		try {
			final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(url));
			
			clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
				public void handleMessage(String message) {					
					//LOGGER.debug(message);
					JSONObject jsonObj = new JSONObject(message);

					if (jsonObj.has("hanaObjectType")) {
						String objectType = jsonObj.getString("hanaObjectType");
						String hanaInfo = jsonObj.getString("hanaObject");

						//switch based on type to map to appropriate data sent over the wire.
						switch (objectType) {
						case "HanaBlockInfo":
							HanaBlockInfo hanaBlockInfo = gson.fromJson(hanaInfo.toString(), HanaBlockInfo.class);
							LOGGER.info("Block = " + hanaBlockInfo);		                    	
							break;
						case "HanaTransactionInfo":
							HanaTransactionInfo hanaTransInfo = gson.fromJson(hanaInfo.toString(), HanaTransactionInfo.class);
							LOGGER.info("Transaction = " + hanaTransInfo);		                    	
							break;
						case "HanaTransactionInputInfo":
							JSONArray jsonArray = new JSONArray(hanaInfo);
							if (jsonArray.length() > 0) {
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObject = jsonArray.getJSONObject(i);
									String jsonStr = jsonObject.toString();
									HanaTransactionInputInfo hanaTransInputInfo = gson.fromJson(jsonStr, HanaTransactionInputInfo.class);
									LOGGER.debug("Transaction Input = " + hanaTransInputInfo);		                    			
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
									LOGGER.debug("Transaction Output = " + hanaTransOutputInfo);		                    			
								}
							}	                    	
							break;		            				
						default:
							break;
						}	
					}
					else { 
						if (jsonObj.has("status")) {
							String status = jsonObj.getString("status");
							if (status.equalsIgnoreCase("success")) {
								JSONObject resultObj = jsonObj.getJSONObject("result");
								JSONObject hanaBlockInfo = (JSONObject) resultObj.get("hanaBlockInfo");
								LOGGER.info("Block = " + hanaBlockInfo);								
							}
							else
								LOGGER.info("result status : " + status);
						}
						/*if (jsonObj.has("result")) {
							String resultString = (String) jsonObj.get("result");
							LOGGER.info("result: " + resultString);
							if (resultString.startsWith("{\"hanaBlockItems\":")) {
								final HanaItems hanaItems = gson.fromJson(resultString, HanaItems.class);
								LOGGER.info("hanaItems: " + hanaItems);
								LOGGER.info("");
								LOGGER.info("hanaBlockItems...");
								for (final HanaItems.HanaBlockItem hanaBlockItem : hanaItems.getHanaBlockItems()) {
									final Object[] isValid = HanaItems.isHanaBlockItemValid(hanaBlockItem);
									if (!(boolean) isValid[0]) {
										LOGGER.error("************************************************");
										LOGGER.error("hanaBlockItem failed internal consistency checks for reason " + isValid[1]);
										LOGGER.error("************************************************");
									}
									LOGGER.info("  " + hanaBlockItem);
									LOGGER.info("");
									LOGGER.info("  hanaTransactionItems...");
									for (final HanaItems.HanaTransactionItem hanaTransactionItem : hanaBlockItem.getHanaTransactionItems()) {
										LOGGER.info("    " + hanaTransactionItem);
										LOGGER.info("");
										LOGGER.info("    hanaTransactionInputInfos...");
										for (final HanaTransactionInputInfo hanaTransactionInputInfo : hanaTransactionItem.getHanaTransactionInputInfos()) {
											LOGGER.info("      " + hanaTransactionInputInfo);
											LOGGER.info("");
										}
										LOGGER.info("    hanaTransactionOutputInfos...");
										for (final HanaTransactionOutputInfo hanaTransactionOutputInfo : hanaTransactionItem.getHanaTransactionOutputInfos()) {
											LOGGER.info("      " + hanaTransactionOutputInfo);
											LOGGER.info("");
										}
									}
								}
							} 
							else {
								LOGGER.error("unexpected server result " + resultString);
							}
							LOGGER.info(jsonObj);
							String result = jsonObj.getString("result");

							HanaItems hanaItems = gson.fromJson(result, HanaItems.class);

							List<HanaBlockItem> blockItemsList = util.getBlockItems(hanaItems);
							for (HanaBlockItem item : blockItemsList) {
								HanaBlockItem blockItem = util.getBlockItem(gson, item);                     	    
								util.getTransactionItems(gson, blockItem);
							}
						}*/
						else if (jsonObj.has("hanaTransactionItems")) {
							LOGGER.info(jsonObj);
							JSONArray jsonArray = jsonObj.getJSONArray("hanaTransactionItems");

							if (jsonArray.length() > 0) {
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObject = jsonArray.getJSONObject(i);
									String json2Str = jsonObject.toString();                    	        	
									HanaTransactionItem transactionItem = gson.fromJson(json2Str, HanaTransactionItem.class);
									util.getHanaTransactionItem(gson, util, transactionItem); 
								}
							}
						}
						else
							LOGGER.info(jsonObj);
					}
					LOGGER.info("**************************************************************************************");
					LocalDateTime currentTime = LocalDateTime.now();
					LOGGER.info("Transaction " + transactionCount++ + ", Current DateTime: " + currentTime);
				}
			});

			// send message to websocket
			final String getNewBlockMessage = "{\"command\":\"getnewtestblock\", \"blockNumber\":\"1\", \"numberOfBlocks\":\"2\"}";
			LOGGER.info("sending request: " + getNewBlockMessage);
			clientEndPoint.sendMessage(getNewBlockMessage);

			try {
				Thread.sleep(2000);
				
				Timer timer = new Timer();
				TimerTask myTask = new TimerTask() {
				    @Override
				    public void run() {
				    	// whatever you need to do every 5 seconds
				    	String [] arr = {"newFault", "getnewtestblock"};
				    	Random random = new Random();
				    	// randomly selects an index from the arr
				    	int select = random.nextInt(arr.length); 

				    	String selectedCommand = arr[select];
				    	LOGGER.info("Random String selected: " + selectedCommand); 
				    	String newMessage = null;
				    	switch (selectedCommand) {
				    	case "getnewtestblock":				    	
				    		newMessage = "{\"command\":\"" + selectedCommand + "\", \"blockNumber\":\"1\", \"numberOfBlocks\":\"2\"}";	    		
				    		break;
				    	case "newFault":
				    		//String message;
				    		JSONObject json = new JSONObject();
				    		json.put("command", selectedCommand);
				    		//JSONObject signatureObj = new JSONObject();
				    		JSONArray signatureObj = new JSONArray();
				    		signatureObj.put("value1");
				    		signatureObj.put("value2");
				    		signatureObj.put("value3");
				    		json.put("signatureList", signatureObj);
				    		newMessage = json.toString();
				    		//LOGGER.info("newMessage : " + newMessage);
				    		//newMessage = "{\"command\":\"" + selectedCommand + "\", \"signatureList\":\"2\"}";
				    		break;
				    	}
				    	LOGGER.info("sending request: " + newMessage);
				    	clientEndPoint.sendMessage(newMessage);
				    }
				};

				timer.schedule(myTask, 5000, 5000);
				/*Object lock = new Object();
				synchronized (lock) {
					while (true) {
						lock.wait();
					}
				}*/
			} catch (InterruptedException ex) {
				System.err.println("InterruptedException exception: " + ex.getMessage());
			}
			//LOGGER.info("End of program");
		} catch (URISyntaxException ex) {
			LOGGER.info("URISyntaxException exception: " + ex.getMessage());
			LOGGER.info("done");
		}			
		
    //testHanaBlock();
  }

  private static void testHanaBlock() {
    Logger l = Logger.getLogger(LocalWebSocketClientTest.class);
    Gson gson = new Gson();

    l.info("Start to get contents Hana Block Item");
    HanaUtil util = new HanaUtil();
    HanaBlockItem blockItem = HanaItems.makeTestHanaBlockItem();

    util.GetHanaBlockItem(blockItem);
    System.out.println(gson.fromJson(util.getHanaBlockInfo(blockItem), HanaBlockInfo.class));

    List<HanaTransactionItem> transactionList = util.getTransactionItem(blockItem);
    for (HanaTransactionItem item : transactionList) {
    	System.out.println(gson.fromJson(util.getTransactionInfo(item), HanaTransactionInfo.class));
    	
    	JSONArray jsonArray = new JSONArray(util.getTransactionInputInfo(item));
    	if (jsonArray.length() > 0) {
    		for (int i = 0; i < jsonArray.length(); i++) {
            	JSONObject jsonObject = jsonArray.getJSONObject(i);
            	String jsonStr = jsonObject.toString();
            	HanaTransactionInputInfo hanaTransInputInfo = gson.fromJson(jsonStr, HanaTransactionInputInfo.class);
				System.out.println("Transaction Input = " + hanaTransInputInfo);		                    			
    		}
    	}
    	
    	jsonArray = new JSONArray(util.getTransactionOutputInfo(item));
    	if (jsonArray.length() > 0) {
    		for (int i = 0; i < jsonArray.length(); i++) {
            	JSONObject jsonObject = jsonArray.getJSONObject(i);
            	String jsonStr = jsonObject.toString();
            	HanaTransactionOutputInfo hanaTransOutputInfo = gson.fromJson(jsonStr, HanaTransactionOutputInfo.class);
				System.out.println("Transaction Output = " + hanaTransOutputInfo);		                    			
    		}
    	}
    }
    l.info("End of procedure");
  }
}
