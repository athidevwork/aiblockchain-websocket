/**
 *
 */
package com.aiblockchain.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.aiblockchain.model.hana.HanaBlockInfo;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.model.hana.HanaItems.HanaTransactionItem;
import com.aiblockchain.model.hana.HanaTransactionInfo;
import com.aiblockchain.model.hana.HanaTransactionInputInfo;
import com.aiblockchain.model.hana.HanaTransactionOutputInfo;
import com.aiblockchain.model.hana.util.HanaUtil;
import com.aiblockchain.server.websocket.fault.ClientResponse;
import com.aiblockchain.server.websocket.fault.DiamondResponse;
import com.aiblockchain.server.websocket.fault.FaultData;
import com.aiblockchain.server.websocket.fault.FaultResponse;
import com.aiblockchain.server.websocket.fault.ResponseData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
				JsonObject element = null;
				JsonElement resultWrapper = null;
				JsonParser parser = new JsonParser();				
				
				public void handleMessage(String message) {
					LOGGER.debug("handleMessage() in client:" + message);
					JSONObject jsonObj = new JSONObject(message);

					if (jsonObj.has("hanaObjectType")) {
						String objectType = jsonObj.getString("hanaObjectType");
						String hanaInfo = jsonObj.getString("hanaObject");

						// switch based on type to map to appropriate data sent
						// over the wire.
						switch (objectType) {
						case "HanaBlockInfo":
							HanaBlockInfo hanaBlockInfo = gson.fromJson(hanaInfo.toString(), HanaBlockInfo.class);
							LOGGER.info("Block = " + hanaBlockInfo);
							break;
						case "HanaTransactionInfo":
							HanaTransactionInfo hanaTransInfo = gson.fromJson(hanaInfo.toString(),
									HanaTransactionInfo.class);
							LOGGER.info("Transaction = " + hanaTransInfo);
							break;
						case "HanaTransactionInputInfo":
							JSONArray jsonArray = new JSONArray(hanaInfo);
							if (jsonArray.length() > 0) {
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObject = jsonArray.getJSONObject(i);
									String jsonStr = jsonObject.toString();
									HanaTransactionInputInfo hanaTransInputInfo = gson.fromJson(jsonStr,
											HanaTransactionInputInfo.class);
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
									HanaTransactionOutputInfo hanaTransOutputInfo = gson.fromJson(jsonStr,
											HanaTransactionOutputInfo.class);
									LOGGER.debug("Transaction Output = " + hanaTransOutputInfo);
								}
							}
							break;
						default:
							break;
						}
					} else {
						LOGGER.debug("jsonObj : " + jsonObj);
						if (jsonObj.has("resultType")) {
							String resultType = jsonObj.getString("resultType");
							switch (resultType) {
							case "HanaItems":
								/*
								 * JSONObject resultObj = (JSONObject)
								 * jsonObj.getJSONObject("result"); if
								 * (resultObj.has("hanaBlockItems")) {
								 * JSONObject hanaBlockItems =
								 * resultObj.getJSONObject("hanaBlockItems");
								 * LOGGER.info("BlockItem = " + hanaBlockItems);
								 * } else
								 */
								LOGGER.info("hanaBlockItems: " + jsonObj);
								break;
							case "HanaBlockInfo":
								JSONObject resultObj1 = jsonObj.getJSONObject("responseData");
								if (resultObj1.has("hanaBlockInfo")) {
									JSONObject hanaBlockInfo = (JSONObject) resultObj1.get("hanaBlockInfo");
									LOGGER.info("Block = " + hanaBlockInfo);
								}
								break;
							case "FaultResponse":
								FaultResponse faultResponse = gson.fromJson(message, FaultResponse.class);
								System.out.println("Fault Response: " + faultResponse);
								//ClientResponseImpl clientResponse = gson.fromJson(message, ClientResponseImpl.class);
								//System.out.println("Client Response: " + clientResponse);
								
								/*ClientResponse clientResponse = gson.fromJson(message, ClientResponse.class);
								System.out.println("Client Response: " + clientResponse);
								
								element = (JsonObject)parser.parse(message);
								resultWrapper = element.get("responseData");
								ResponseData faultResponse = gson.fromJson(resultWrapper, ResponseData.class);
								System.out.println("Response Data: " + faultResponse);
	
								resultWrapper = element.getAsJsonObject("responseData").get("result");
								FaultResponse resp = gson.fromJson(resultWrapper, FaultResponse.class);
								System.out.println("Fault Result: " + resp);*/
								
								/*FaultResponse resp = (FaultResponse) faultResponse.getResult();
								System.out.println("Fault Response fault id: " + resp.getFaultIds());
								System.out.println("Fault Response txn id: " + resp.getTxnIds());*/
								
								//String value = jsonObj.valueToString("FaultResponse");
								//FaultResponse faultResponse = (FaultResponse) clientResponse.getResult();
								//System.out.println("Fault Response: " + faultResponse);
								
								/*if (jsonObj.has("faultData")) {
									JSONObject faultData = (JSONObject) jsonObj.get("faultData");
									JSONArray txnArr = faultData.getJSONArray("txids");
									List<String> faultTxns = new ArrayList<String>();
									for (int i = 0; i < txnArr.length(); i++) {
										faultTxns.add(txnArr.getString(i));
									}
									LOGGER.debug("Txn Id's from Blockchain : " + faultTxns);
								} else
									LOGGER.info("Fault Response " + jsonObj);*/
								break;
							case "DiamondResponse":
								DiamondResponse diamondResponse = gson.fromJson(message, DiamondResponse.class);
								System.out.println("Diamond Response: " + diamondResponse);
								//ClientResponseImpl diamondResponse = gson.fromJson(message, ClientResponseImpl.class);
								//System.out.println("Diamond map Response: " + diamondResponse);
								
								/*element = (JsonObject)parser.parse(message);
								resultWrapper = element.get("result");
								DiamondResponse diamondResponse = gson.fromJson(resultWrapper, DiamondResponse.class);
								System.out.println("Diamond Response: " + diamondResponse);*/
								
								/*if (jsonObj.has("diamondData")) {
									JSONObject diamondData = (JSONObject) jsonObj.get("diamondData");
									JSONArray diamondArr = diamondData.getJSONArray("diamondids");
									List<String> diamondIds = new ArrayList<String>();
									for (int i = 0; i < diamondArr.length(); i++) {
										diamondIds.add(diamondArr.getString(i));
									}
									LOGGER.debug("Diamond Id's from Blockchain : " + diamondIds);
								} else
									LOGGER.info("Diamond Response " + jsonObj);*/
								break;
							}
						} else
							LOGGER.info(jsonObj);
					}
					LocalDateTime currentTime = LocalDateTime.now();
					LOGGER.info("Transaction " + transactionCount++ + ", Current DateTime: " + currentTime);
					LOGGER.info(
							"**************************************************************************************");
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
					int commandPos = 0;
					@Override
					public void run() {
						// whatever you need to do every 5 seconds
						String[] arr = { "addFault", "saveDiamond", "getnewblock", "getnewtestblock" };
						//Random random = new Random();
						// randomly selects an index from the arr
						//int select = random.nextInt(arr.length);
						int select = 0;

						if (commandPos == arr.length)
							commandPos = 0;
						else {
							select = commandPos++;
						}
						
						String selectedCommand = arr[select];
						LOGGER.info("Random String selected: " + selectedCommand);
						String newMessage = null;
						switch (selectedCommand) {
						case "getnewblock":
						case "getnewtestblock":
							newMessage = "{\"command\":\"" + selectedCommand
									+ "\", \"blockNumber\":\"1\", \"numberOfBlocks\":\"2\"}";
							break;
						case "addFault":
							JSONObject faultRequest = new JSONObject();
							faultRequest.put("command", selectedCommand);
							JSONObject faultDataJO = new JSONObject();

							JSONArray faultIds = new JSONArray();
							faultIds.put("value1");
							faultIds.put("value2");
							faultIds.put("value3");
							faultDataJO.put("faultIds", faultIds);
							faultRequest.put("faultData", faultDataJO);
							newMessage = faultRequest.toString();
							break;
						case "saveDiamond":
							JSONObject saveDiamondRequest = new JSONObject();
							saveDiamondRequest.put("command", selectedCommand);
							JSONObject saveDiamondJO = new JSONObject();

							JSONArray saveDiamondId = new JSONArray();
							saveDiamondId.put("value1");
							saveDiamondId.put("value2");
							saveDiamondJO.put("diamondIds", saveDiamondId);
							saveDiamondRequest.put("diamondData", saveDiamondJO);
							newMessage = saveDiamondRequest.toString();
							break;
						}
						LOGGER.info("sending request: " + newMessage);
						clientEndPoint.sendMessage(newMessage);
					}
				};

				timer.schedule(myTask, 10000, 10000);
				/*
				 * Object lock = new Object(); synchronized (lock) { while
				 * (true) { lock.wait(); } }
				 */
			} catch (InterruptedException ex) {
				System.err.println("InterruptedException exception: " + ex.getMessage());
			}
			// LOGGER.info("End of program");
		} catch (URISyntaxException ex) {
			LOGGER.info("URISyntaxException exception: " + ex.getMessage());
			LOGGER.info("done");
		}

		// testHanaBlock();
	}

	private static void testFaultData() {
		String faultData = "{\"status\":\"success\",\"resultType\":\"FaultResponse\",\"result\":[[\"value1\",\"value2\",\"value3\"],[\"06012c65c2ce2237d4ab00570b337b453af45bbf490ed908fc64cded99331ef3\",\"cc903acb3dcb96b067b1a482cb60df65c3767eace43f15b6348fae909fa990bd\",\"ad7738df1b07ff06918a8d7fb02229a9ec98949a2288fa49763eba161658d017\"]]}";
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
					HanaTransactionInputInfo hanaTransInputInfo = gson.fromJson(jsonStr,
							HanaTransactionInputInfo.class);
					System.out.println("Transaction Input = " + hanaTransInputInfo);
				}
			}

			jsonArray = new JSONArray(util.getTransactionOutputInfo(item));
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String jsonStr = jsonObject.toString();
					HanaTransactionOutputInfo hanaTransOutputInfo = gson.fromJson(jsonStr,
							HanaTransactionOutputInfo.class);
					System.out.println("Transaction Output = " + hanaTransOutputInfo);
				}
			}
		}
		l.info("End of procedure");
	}
}
