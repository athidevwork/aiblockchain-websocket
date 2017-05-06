/**
 *
 */
package com.aiblockchain.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

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
import com.aiblockchain.server.websocket.WebsocketClientEndpoint;
import com.google.gson.Gson;

/**
 * @author Athi
 *
 */
public class HanaClient {
	//static Logger logger = Logger.getLogger("HanaClient");
	private static final Logger LOGGER = Logger.getLogger(HanaClient.class);
	
	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 20000;
	 
    // stateless JSON serializer/deserializer
    private static Gson gson = new Gson();		
	private static HanaUtil util = new HanaUtil();
	private static int transactionCount = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {	   
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
                    	if (jsonObj.has("result")) {
                    		
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
                            } else {
                              LOGGER.error("unexpected server result " + resultString);
                            }
                    		LOGGER.info(jsonObj);
                    		String result = jsonObj.getString("result");
                    		//System.out.println(result);
                    		
                    		HanaItems hanaItems = gson.fromJson(result, HanaItems.class);
                    		
                    		List<HanaBlockItem> blockItemsList = util.getBlockItems(hanaItems);
                    	    for (HanaBlockItem item : blockItemsList) {
                        		HanaBlockItem blockItem = util.getBlockItem(gson, item);                     	    
                        	    util.getTransactionItems(gson, blockItem);
                    	    }
                    	}
                    	else if (jsonObj.has("hanaTransactionItems")) {
                    		System.out.println(jsonObj);
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
                    		System.out.println(jsonObj);
                    }
                   LOGGER.info("**************************************************************************************");
                   LocalDateTime currentTime = LocalDateTime.now();
                   LOGGER.info("Transaction " + ++transactionCount + ", Current DateTime: " + currentTime);
                }
            });

      // send message to websocket
      final String getNewBlockMessage = "{\"command\":\"getnewblock\", \"blockNumber\":\"1\", \"numberOfBlocks\":\"2\"}";
      LOGGER.info("sending request: " + getNewBlockMessage);
      clientEndPoint.sendMessage(getNewBlockMessage);

      //final String startBlockMessage = "{\"command\":\"startblock\", \"blockNumber\":\"1\"}";
      //LOGGER.info("sending request: " + startBlockMessage);
      //clientEndPoint.sendMessage(startBlockMessage);

      LOGGER.info("waiting three minutes for new blocks...");
      Thread.sleep(180000);
            // wait for messages from websocket
            //Thread.sleep(100000);
            //System.out.println("Waking up on Main thread");
            try {
                Object lock = new Object();
                synchronized (lock) {
                    while (true) {
                        lock.wait();
                    }
                }
            } catch (InterruptedException ex) {
            	System.err.println("InterruptedException exception: " + ex.getMessage());
            }
            System.out.println("End of program");
        //} catch (InterruptedException ex) {
            //System.err.println("Thread InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
      LOGGER.info("done");

    } catch (InterruptedException ex) {
      LOGGER.error("InterruptedException exception: " + ex.getMessage());
    //} catch (URISyntaxException ex) {
      //LOGGER.error("URISyntaxException exception: " + ex.getMessage());
    }
  }

//    the JSON string returned by the getnewblock command requesting 3 blocks starting at 1  
//  
//    {
//       "hanaBlockItems":[
//          {
//             "hanaBlockInfo":{
//                "blockNumber":1,
//                "blockVersion":4,
//                "blockMerkleRoot":"25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84",
//                "blockTime":"Mon Apr 24 20:45:07 UTC 2017",
//                "blockNoOftransactions":1
//             },
//             "hanaTransactionItems":[
//                {
//                   "hanaTransactionInfo":{
//                      "blockNumber":1,
//                      "transactionIndex":0,
//                      "transactionId":"25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84",
//                      "transactionNoOfInputs":1,
//                      "transactionNoOfOutputs":2
//                   },
//                   "hanaTransactionInputInfos":[
//                      {
//                         "blockNumber":1,
//                         "transactionIndex":0,
//                         "transactionId":"25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84",
//                         "parentTransactionId":"0",
//                         "transactionInputIndex":0,
//                         "indexIntoParentTransaction":0
//                      }
//                   ],
//                   "hanaTransactionOutputInfos":[
//                      {
//                         "blockNumber":1,
//                         "transactionIndex":0,
//                         "transactionId":"25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84",
//                         "transactionOutputIndex":0,
//                         "address":"ALN9sA56BRppK1ohiZirYHxK8F75zBvyBz",
//                         "amount":50.0
//                      },
//                      {
//                         "blockNumber":1,
//                         "transactionIndex":0,
//                         "transactionId":"25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84",
//                         "transactionOutputIndex":1,
//                         "address":"to unknown type",
//                         "amount":0.0
//                      }
//                   ]
//                }
//             ]
//          },
//          {
//             "hanaBlockInfo":{
//                "blockNumber":2,
//                "blockVersion":4,
//                "blockMerkleRoot":"f869863f47c5eb3e8e3f442d1d5a93ea51ba46d771e8e7300b74d08c6d1ba02f",
//                "blockTime":"Mon Apr 24 20:48:00 UTC 2017",
//                "blockNoOftransactions":1
//             },
//             "hanaTransactionItems":[
//                {
//                   "hanaTransactionInfo":{
//                      "blockNumber":2,
//                      "transactionIndex":0,
//                      "transactionId":"f869863f47c5eb3e8e3f442d1d5a93ea51ba46d771e8e7300b74d08c6d1ba02f",
//                      "transactionNoOfInputs":1,
//                      "transactionNoOfOutputs":1
//                   },
//                   "hanaTransactionInputInfos":[
//                      {
//                         "blockNumber":2,
//                         "transactionIndex":0,
//                         "transactionId":"f869863f47c5eb3e8e3f442d1d5a93ea51ba46d771e8e7300b74d08c6d1ba02f",
//                         "parentTransactionId":"0",
//                         "transactionInputIndex":0,
//                         "indexIntoParentTransaction":0
//                      }
//                   ],
//                   "hanaTransactionOutputInfos":[
//                      {
//                         "blockNumber":2,
//                         "transactionIndex":0,
//                         "transactionId":"f869863f47c5eb3e8e3f442d1d5a93ea51ba46d771e8e7300b74d08c6d1ba02f",
//                         "transactionOutputIndex":0,
//                         "address":"AQgru1vWLVWjt7SH7rzimZbu5xnFDnYQvt",
//                         "amount":50.0
//                      }
//                   ]
//                }
//             ]
//          },
//          {
//             "hanaBlockInfo":{
//                "blockNumber":3,
//                "blockVersion":4,
//                "blockMerkleRoot":"1b530b1cf21ce95a483bc82d3069046794dcb3169b6397582b037fd1676da6e8",
//                "blockTime":"Mon Apr 24 20:49:00 UTC 2017",
//                "blockNoOftransactions":1
//             },
//             "hanaTransactionItems":[
//                {
//                   "hanaTransactionInfo":{
//                      "blockNumber":3,
//                      "transactionIndex":0,
//                      "transactionId":"1b530b1cf21ce95a483bc82d3069046794dcb3169b6397582b037fd1676da6e8",
//                      "transactionNoOfInputs":1,
//                      "transactionNoOfOutputs":1
//                   },
//                   "hanaTransactionInputInfos":[
//                      {
//                         "blockNumber":3,
//                         "transactionIndex":0,
//                         "transactionId":"1b530b1cf21ce95a483bc82d3069046794dcb3169b6397582b037fd1676da6e8",
//                         "parentTransactionId":"0",
//                         "transactionInputIndex":0,
//                         "indexIntoParentTransaction":0
//                      }
//                   ],
//                   "hanaTransactionOutputInfos":[
//                      {
//                         "blockNumber":3,
//                         "transactionIndex":0,
//                         "transactionId":"1b530b1cf21ce95a483bc82d3069046794dcb3169b6397582b037fd1676da6e8",
//                         "transactionOutputIndex":0,
//                         "address":"AQTqW5xZaegAsFC7MMAhfyTUn44gf7CBK3",
//                         "amount":50.0
//                      }
//                   ]
//                }
//             ]
//          }
//       ]
//    }
//  
//  
//    Here is the JSON structure converted into the Java object HanaItems, that contains blocks,
//    transactions, inputs and outputs.  
//    
//  
//    [HanaClient] hanaItems: [HanaItems, 3 hanaBlockItems]
//    [HanaClient] 
//    [HanaClient] hanaBlockItems...
//    [HanaClient]   [HanaBlockItem [HanaBlockInfo
//      blockNumber: 1
//      blockVersion: 4
//      blockMerkleRoot: 25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84
//      blockTime: Mon Apr 24 20:45:07 UTC 2017
//      blockNoOftransactions: 1]]
//    [HanaClient] 
//    [HanaClient]   hanaTransactionItems...
//    [HanaClient]     [HanaTransactionItem [HanaTransactionInfo
//      blockNumber: 1
//      transactionIndex: 0
//      transactionId: 25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84
//      transactionNoOfInputs: 1
//      transactionNoOfOutputs: 2]]
//    [HanaClient] 
//    [HanaClient]     hanaTransactionInputInfos...
//    [HanaClient]       [HanaTransactionInputInfo
//      blockNumber: 1
//      transactionIndex: 0
//      transactionId: 25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84
//      parentTransactionId: 0
//      indexIntoParentTransaction: 0
//      transactionInputIndex: 0]
//    [HanaClient] 
//    [HanaClient]     hanaTransactionOutputInfos...
//    [HanaClient]       [HanaTransactionOutputInfo
//      blockNumber: 1
//      transactionIndex: 0
//      transactionId: 25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84
//      transactionOutputIndex: 0
//      address: ALN9sA56BRppK1ohiZirYHxK8F75zBvyBz
//      amount: 50.0]
//    [HanaClient] 
//    [HanaClient]       [HanaTransactionOutputInfo
//      blockNumber: 1
//      transactionIndex: 0
//      transactionId: 25404ba8f9e6ca2e19d3e8cee6b4a3ccd157143a5675529ac33d620485ca8f84
//      transactionOutputIndex: 1
//      address: to unknown type
//      amount: 0.0]
//    [HanaClient] 
//    [HanaClient]   [HanaBlockItem [HanaBlockInfo
//      blockNumber: 2
//      blockVersion: 4
//      blockMerkleRoot: f869863f47c5eb3e8e3f442d1d5a93ea51ba46d771e8e7300b74d08c6d1ba02f
//      blockTime: Mon Apr 24 20:48:00 UTC 2017
//      blockNoOftransactions: 1]]
//    [HanaClient] 
//    [HanaClient]   hanaTransactionItems...
//    [HanaClient]     [HanaTransactionItem [HanaTransactionInfo
//      blockNumber: 2
//      transactionIndex: 0
//      transactionId: f869863f47c5eb3e8e3f442d1d5a93ea51ba46d771e8e7300b74d08c6d1ba02f
//      transactionNoOfInputs: 1
//      transactionNoOfOutputs: 1]]
//    [HanaClient] 
//    [HanaClient]     hanaTransactionInputInfos...
//    [HanaClient]       [HanaTransactionInputInfo
//      blockNumber: 2
//      transactionIndex: 0
//      transactionId: f869863f47c5eb3e8e3f442d1d5a93ea51ba46d771e8e7300b74d08c6d1ba02f
//      parentTransactionId: 0
//      indexIntoParentTransaction: 0
//      transactionInputIndex: 0]
//    [HanaClient] 
//    [HanaClient]     hanaTransactionOutputInfos...
//    [HanaClient]       [HanaTransactionOutputInfo
//      blockNumber: 2
//      transactionIndex: 0
//      transactionId: f869863f47c5eb3e8e3f442d1d5a93ea51ba46d771e8e7300b74d08c6d1ba02f
//      transactionOutputIndex: 0
//      address: AQgru1vWLVWjt7SH7rzimZbu5xnFDnYQvt
//      amount: 50.0]
//    [HanaClient] 
//    [HanaClient]   [HanaBlockItem [HanaBlockInfo
//      blockNumber: 3
//      blockVersion: 4
//      blockMerkleRoot: 1b530b1cf21ce95a483bc82d3069046794dcb3169b6397582b037fd1676da6e8
//      blockTime: Mon Apr 24 20:49:00 UTC 2017
//      blockNoOftransactions: 1]]
//    [HanaClient] 
//    [HanaClient]   hanaTransactionItems...
//    [HanaClient]     [HanaTransactionItem [HanaTransactionInfo
//      blockNumber: 3
//      transactionIndex: 0
//      transactionId: 1b530b1cf21ce95a483bc82d3069046794dcb3169b6397582b037fd1676da6e8
//      transactionNoOfInputs: 1
//      transactionNoOfOutputs: 1]]
//    [HanaClient] 
//    [HanaClient]     hanaTransactionInputInfos...
//    [HanaClient]       [HanaTransactionInputInfo
//      blockNumber: 3
//      transactionIndex: 0
//      transactionId: 1b530b1cf21ce95a483bc82d3069046794dcb3169b6397582b037fd1676da6e8
//      parentTransactionId: 0
//      indexIntoParentTransaction: 0
//      transactionInputIndex: 0]
//    [HanaClient] 
//    [HanaClient]     hanaTransactionOutputInfos...
//    [HanaClient]       [HanaTransactionOutputInfo
//      blockNumber: 3
//      transactionIndex: 0
//      transactionId: 1b530b1cf21ce95a483bc82d3069046794dcb3169b6397582b037fd1676da6e8
//      transactionOutputIndex: 0
//      address: AQTqW5xZaegAsFC7MMAhfyTUn44gf7CBK3
//      amount: 50.0]
}
