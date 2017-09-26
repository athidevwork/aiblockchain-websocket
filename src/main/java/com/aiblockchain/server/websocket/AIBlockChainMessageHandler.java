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
import com.aiblockchain.server.websocket.fault.ClientResponse;
import com.aiblockchain.server.websocket.fault.DiamondRequest;
import com.aiblockchain.server.websocket.fault.DiamondResponse;
import com.aiblockchain.server.websocket.fault.FaultRequest;
import com.aiblockchain.server.websocket.fault.FaultData;
import com.aiblockchain.server.websocket.fault.ResponseData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
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
    
    ClientResponse clientResponse = new ClientResponse();
	ResponseData respData = new ResponseData(); 
	String serverResult = null;
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
			respData.setStatus("failure-getnewblock request failed");
		} else {
			respData.setStatus("success");
	       	respData.setResult(hanaItems);
			clientResponse.setResultType("HanaItems");	       	
	    	clientResponse.setResponseData(respData);
	    		
			System.out.println("Block response from server = " + gson.toJson(hanaItems));
			//clientResponse.setResult(gson.toJson(hanaItems));
		}        
    	break;
    case "getnewtestblock":
  	  logger.info("Processing getNewTestBlock");
  	  HanaBlockItem blockItem = HanaItems.makeTestHanaBlockItem(); 
		respData.setStatus("success");
       	respData.setResult(blockItem);
		clientResponse.setResultType("HanaBlockInfo");	       	
    	clientResponse.setResponseData(respData); 
    	
    	System.out.println ("Test Block Item = " + gson.toJson(blockItem));    	
    	break;
    case "addFault":
    	System.out.println("Processing add Fault");
    	JSONObject faultData = jobj.getJSONObject("faultData");
    	JSONArray txnArr = faultData.getJSONArray("faultIds");    	
    	List<String> faultIds = new ArrayList<String>();
    	for(int i = 0; i < txnArr.length(); i++){
    		faultIds.add(txnArr.getString(i));
    	}
    	
    	System.out.println ("fault ids received : " + faultIds);
    	/*Type listType = 
    		     new TypeToken<List<String>>(){}.getType();
    	List<String> faultTransactions = new Gson().fromJson(faultTxns, listType);*/

    	if (faultIds.size() == 0) {
    		//respData.setStatus("failure-"+apiCommand+" request failed. No transactions to be sent to server");
        	respData.setStatus("failure");
        	respData.setResult(null);
    		clientResponse.setResultType(apiCommand + " request failed. No transactions to be sent to server.");
    		clientResponse.setResponseData(null);
    	} else {
    		FaultRequest faultRequest = new FaultRequest("addFault", faultIds);
        	serverResult = AbstractAPIAdapter.getInstance().updateFault(faultRequest);
        	//serverResult = "[[\"value1\",\"value2\",\"value3\"],[\"06012c65c2ce2237d4ab00570b337b453af45bbf490ed908fc64cded99331ef3\",\"cc903acb3dcb96b067b1a482cb60df65c3767eace43f15b6348fae909fa990bd\",\"ad7738df1b07ff06918a8d7fb02229a9ec98949a2288fa49763eba161658d017\"]]";
        	respData.setStatus("success");
        	respData.setResult(serverResult);
    		clientResponse.setResultType("FaultResponse");
    		clientResponse.setResponseData(respData);
    		System.out.println("Fault response from server = " + gson.toJson(clientResponse));
    	}    	 	
    	break;
    case "saveDiamond":
    	System.out.println("Processing save Diamond");
    	JSONObject diamondData = jobj.getJSONObject("diamondData");
    	JSONArray diamondArr = diamondData.getJSONArray("diamondIds");    	
    	List<String> diamondIds = new ArrayList<String>();
    	for(int i = 0; i < diamondArr.length(); i++){
    		diamondIds.add(diamondArr.getString(i));
    	}
    	
    	System.out.println ("diamond ids received : " + diamondIds);
    	if (diamondIds.size() == 0) {
        	respData.setStatus("failure");
        	respData.setResult(null);
    		clientResponse.setResultType(apiCommand + " request failed. No transactions to be sent to server.");
    		clientResponse.setResponseData(null);
    	} else {
    		DiamondRequest diamondRequest = new DiamondRequest("saveDiamond", diamondIds);
        	serverResult = AbstractAPIAdapter.getInstance().saveDiamond(diamondRequest);
        	//serverResult = "[[\"value1\",\"value2\"],[\"06012c65c2ce2237d4ab00570b337b453af45bbf490ed908fc64cded99331ef3\",\"cc903acb3dcb96b067b1a482cb60df65c3767eace43f15b6348fae909fa990bd\"]]";
        	respData.setStatus("success");
        	respData.setResult(serverResult);
    		clientResponse.setResultType("DiamondResponse");
    		clientResponse.setResponseData(respData);
    		System.out.println("Diamond response from server = " + gson.toJson(clientResponse));
    	}    	 	
    	break;   	
    default:
    	System.out.println("Processing default. Not expected behavior. Please check for command sent to server.");
    	//clientResponse.setStatus(apiCommand + " failed. Command not specified or not implemented.");
    	respData.setStatus("failure");
    	respData.setResult(null);
		clientResponse.setResultType(apiCommand + " failed. Command not specified or not implemented.");
		clientResponse.setResponseData(null);
    	break;
    }
    
    String response = gson.toJson(clientResponse);
    logger.info("handle message reponse at server = " + response);
    return response;
  }
}
