/**
 *
 */
package com.aiblockchain.client;

import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.model.hana.HanaTransactionInputInfo;
import com.aiblockchain.model.hana.HanaTransactionOutputInfo;
import com.aiblockchain.server.websocket.WebsocketClientEndpoint;
import com.google.gson.Gson;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * @author Athi
 *
 */
public class HanaClient {

  // the logger
  private static final Logger LOGGER = Logger.getLogger(HanaClient.class);

  /**
   * @param args
   */
  public static void main(String[] args) {
    try {
      // stateless JSON serializer/deserializer
      Gson gson = new Gson();

      // open websocket
      //final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8083/wsticker"));
      final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:20000/wsticker"));
      // add listener
      clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
        @Override
        public void handleMessage(final String message) {
          LOGGER.info("message: " + message);
          JSONObject jsonObj = new JSONObject(message);
          LOGGER.info("jsonObj: " + jsonObj);
          
          if (jsonObj.has("result")) {
            // the message is a response to a request from the client
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
          } else if (message.startsWith("{\"hanaBlockInfo\":")) {
            // the message is initiatied by the server
            final HanaBlockItem hanaBlockItem = gson.fromJson(message, HanaBlockItem.class);
            LOGGER.info("new block notification, hanaBlockItem: " + hanaBlockItem);
          }

          LOGGER.info("*******************************************");
        }
      });

      // send message to websocket
      final String getNewBlockMessage = "{\"command\":\"getnewblock\", \"blockNumber\":\"1\", \"numberOfBlocks\":\"2\"}";
      LOGGER.info("sending request: " + getNewBlockMessage);
      clientEndPoint.sendMessage(getNewBlockMessage);

      final String startBlockMessage = "{\"command\":\"startblock\", \"blockNumber\":\"1\"}";
      //LOGGER.info("sending request: " + startBlockMessage);
      //clientEndPoint.sendMessage(startBlockMessage);

      LOGGER.info("waiting three minutes for new blocks...");
      Thread.sleep(180000);
      LOGGER.info("done");

    } catch (InterruptedException ex) {
      LOGGER.error("InterruptedException exception: " + ex.getMessage());
    } catch (URISyntaxException ex) {
      LOGGER.error("URISyntaxException exception: " + ex.getMessage());
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
