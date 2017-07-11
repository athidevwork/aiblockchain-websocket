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
import java.util.ArrayList;
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
		LocalDateTime startTime = null;

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
					LOGGER.debug("handleMessage() in client:" + message);
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
						LOGGER.debug("jsonObj : " + jsonObj);
						if (jsonObj.has("resultType")) {
							String resultType = jsonObj.getString("resultType");
							switch (resultType) {
							case "HanaItems":
								/*JSONObject resultObj = (JSONObject) jsonObj.getJSONObject("result");
								if (resultObj.has("hanaBlockItems")) {								
									JSONObject hanaBlockItems = resultObj.getJSONObject("hanaBlockItems");
									LOGGER.info("BlockItem = " + hanaBlockItems);
								}
								else*/
									LOGGER.info("hanaBlockItems: " + jsonObj);
								break;
							case "HanaBlockInfo":
								JSONObject resultObj1 = jsonObj.getJSONObject("result");
								if (resultObj1.has("hanaBlockInfo")) {
									JSONObject hanaBlockInfo = (JSONObject) resultObj1.get("hanaBlockInfo");
									LOGGER.info("Block = " + hanaBlockInfo);
								}
								break;
							case "FaultResponse":
								if (jsonObj.has("faultData")) {
									JSONObject faultData = (JSONObject) jsonObj.get("faultData");
							    	JSONArray txnArr = faultData.getJSONArray("txids");  
									List<String> faultTxns = new ArrayList<String>();
							    	for(int i = 0; i < txnArr.length(); i++){
							    		faultTxns.add(txnArr.getString(i));
							    	}
							    	LOGGER.debug("Txn Id's from Blockchain : " + faultTxns);
								}
								else
									LOGGER.info("Fault Response " + jsonObj);
								break;
							}
						}
						else
							LOGGER.info(jsonObj);
					}
					LocalDateTime currentTime = LocalDateTime.now();
					LOGGER.info("Transaction " + transactionCount++ + ", Current DateTime: " + currentTime);
					LOGGER.info("**************************************************************************************");					
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
				    	String [] arr = {"add", "getnewblock", "getnewtestblock", "add"};
				    	Random random = new Random();
				    	// randomly selects an index from the arr
				    	int select = random.nextInt(arr.length); 

				    	String selectedCommand = arr[select];
				    	LOGGER.info("Random String selected: " + selectedCommand); 
				    	String newMessage = null;
				    	switch (selectedCommand) {
				    	case "getnewblock":	
				    	case "getnewtestblock":				    	
				    		newMessage = "{\"command\":\"" + selectedCommand + "\", \"blockNumber\":\"1\", \"numberOfBlocks\":\"2\"}";	    		
				    		break;
				    	case "add":
				    		JSONObject faultRequest = new JSONObject();
				    		faultRequest.put("command", selectedCommand);
				    		JSONObject faultDataJO = new JSONObject();
				    		
				    		JSONArray txnIds = new JSONArray();
				    		txnIds.put("value1");
				    		txnIds.put("value2");
				    		txnIds.put("value3");
				    		faultDataJO.put("txnids", txnIds);
				    		faultRequest.put("faultData", faultDataJO);
				    		newMessage = faultRequest.toString();
				    		break;				    		
				    	}
				    	LOGGER.info("sending request: " + newMessage);
				    	clientEndPoint.sendMessage(newMessage);
				    }
				};

				timer.schedule(myTask, 10000, 10000);
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
