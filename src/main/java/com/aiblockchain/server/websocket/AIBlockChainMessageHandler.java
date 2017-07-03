/**
 *
 */
package com.aiblockchain.server.websocket;

import com.aiblockchain.listener.AIBlockChainListener;
import com.aiblockchain.client.AbstractAPIAdapter;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.server.StringUtils;
import com.aiblockchain.server.websocket.fault.BlockRequest;
import com.aiblockchain.server.websocket.fault.BlockResponse;
import com.aiblockchain.server.websocket.fault.ClientResponse;
import com.aiblockchain.server.websocket.fault.ClientResponseImpl;
import com.google.gson.Gson;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * @author Athi
 *
 */
public class AIBlockChainMessageHandler implements WebSocketMessageHandler {

  Logger logger = Logger.getLogger(AIBlockChainMessageHandler.class);

  // stateless JSON serializer/deserializer
  private Gson gson = new Gson();

  public AIBlockChainMessageHandler() {
    //Preconditions
    assert AIBlockChainListener.getInstance() != null : "the AIBlockChainListener must be constructed before the WebSocketServer";

    AIBlockChainListener.getInstance().addHanaListener();
  }

  @Override
  public String handleMessage(ChannelHandlerContext ctx, String frameText) {
    AIBlockChainListener.getInstance().setChannel(ctx.channel());

    logger.info("Block Server handleMessage " + frameText);
    logger.info(StringUtils.log(logger) + "Block Server handleMessage " + frameText);

    JSONObject jobj = new JSONObject(frameText);
    logger.info("Json = " + jobj.toString());
    String apiCommand = jobj.get("command").toString();
    
    ClientResponse clientResponse = new ClientResponseImpl();
    switch (apiCommand) {
    case "getnewblock":
    	logger.info("Processing getnewblock");
        int blockNumber = Integer.parseInt((String) jobj.get("blockNumber"));
        int numberOfBlocks = Integer.parseInt((String) jobj.get("numberOfBlocks"));
        logger.info("Getting blocks from ai block chain starting from number " + blockNumber
        + " to " + numberOfBlocks);
		HanaItems hanaItems = AbstractAPIAdapter.getInstance().getBlocksStartingWith(
								blockNumber, numberOfBlocks);
		if (hanaItems == null) {
			clientResponse.setStatus("failure-getnewblock request failed");
		} else {
			clientResponse.setStatus("success");
			clientResponse.setResultType("HanaItems");
			clientResponse.setResult(gson.toJson(hanaItems));
		}        
    	break;
    case "getnewtestblock":
  	  logger.info("Processing getNewTestBlock");
  	  HanaBlockItem blockItem = HanaItems.makeTestHanaBlockItem(); 
  	  logger.info("Test Block Item = " + gson.toJson(blockItem));
  	  clientResponse.setStatus("success");
  	  clientResponse.setResultType("HanaBlockInfo");
  	  clientResponse.setResult(blockItem); 
    	break;
    case "newFault":
    	logger.info("Processing newFault");
    	String signatureList = jobj.get("signatureList").toString();
    	logger.info ("signatures received : " + signatureList);
    	//TODO - BlockRPCAccess
    	List<String> faultTransactions = null; //AbstractAPIAdapter.getInstance().newFaultNotification(signatureList);
    	if (faultTransactions == null) {
    		clientResponse.setStatus("failure-newFault request failed");
    	} else {
    		clientResponse.setStatus("success");
    		clientResponse.setResultType("FaultTransactions");
    		clientResponse.setResult(gson.toJson(faultTransactions));
    	}    	 	
    	break;
    default:
    	logger.info("Processing default. Not expected behavior. Please check for command sent to server.");
    	clientResponse.setStatus(apiCommand + " failed. Command not specified or not implemented.");
    	break;
    }
    
    String response = gson.toJson(clientResponse);
    return response;
    
    /*BlockResponse blockResponse = new BlockResponse();
    BlockRequest blockRequest = gson.fromJson(frameText, BlockRequest.class);
    final String command = blockRequest.getCommand();
    logger.info("command " + command);
    System.out.println(StringUtils.log(logger) + "command " + command);

    if (command != null) {
      if ("getnewblock".equals(command)) {
        logger.info("Getting blocks from ai block chain starting from number " + blockRequest.getBlockNumber()
                + " to " + blockRequest.getNumberOfBlocks());
        HanaItems hanaItems = AbstractAPIAdapter.getInstance().getBlocksStartingWith(
                blockRequest.getBlockNumber(),
                blockRequest.getNumberOfBlocks());
        if (hanaItems == null) {
          blockResponse.setResult("failure-getnewblock request failed");
        } else {
          blockResponse.setResult(gson.toJson(hanaItems));
        }
      } else if ("getnewtestblock".equals(command)) { 
    	  logger.info("getNewTestBlock");
    	  HanaBlockItem blockItem = HanaItems.makeTestHanaBlockItem(); 
    	  logger.info("Test Block Item = " + gson.toJson(blockItem));
    	  blockResponse.setResult(gson.toJson(blockItem));
      } else {
        blockResponse.setResult("Failed. Command: " + command + " not recognized.");
      }
    } else {
      blockResponse.setResult("Failed. Command not specified.");
    }
    String response = gson.toJson(blockResponse);
    return response;*/
  }
}
