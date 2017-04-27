/**
 * 
 */
package com.aiblockchain.server.websocket;



import com.aiblockchain.client.AIBlockChainListenerClient;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.server.websocket.blockticker.BlockRequest;
import com.aiblockchain.server.websocket.blockticker.BlockResponse;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.log4j.Logger;

/**
 * @author Athi
 *
 */
public class AIBlockChainMessageHandler implements WebSocketMessageHandler {
   Logger logger = Logger.getLogger(AIBlockChainMessageHandler.class);
	
   // stateless JSON serializer/deserializer
   private Gson gson = new Gson();
	   
   // Keep track of the current channel so we can talk directly to the client
   private AtomicReference<Channel> channel = new AtomicReference<Channel>();
	   
   private AIBlockChainApiAdapter aiBlockChainApiAdapter;
   private AIBlockChainListenerClient aiBlockChainListenerClient;
      
   AIBlockChainApiAdapter getAIBlockChainApiAdapter() {
	   if (aiBlockChainApiAdapter == null)
		   aiBlockChainApiAdapter = new AIBlockChainApiAdapter();
	   return aiBlockChainApiAdapter;
   }
   /** Gets the the AI blockchain listener client singleton instance.
    * 
    * @return the the AI blockchain listener client
    */
   public AIBlockChainListenerClient getAIBlockChainListenerClient() {
     return aiBlockChainListenerClient;
   }  
   
   /** Initialize the AI blockchain listener client from the software agent system.
    * 
    * @param apiAdapter the API adapter supplied by the software agent system
    */
   public void intializeAIBlockChainListenerClient(final AIBlockChainApiAdapter apiAdapter) {
     //Preconditions
     assert apiAdapter != null : "apiAdapter must not be null";
     
     // construct the singleton instance of the listener client
     aiBlockChainListenerClient = AIBlockChainListenerClient.getInstance();
     // insert the API adapter dependency
     aiBlockChainListenerClient.setApiAdapter(apiAdapter);
     logger.info("the AI blockchain listener client is initialized");
   }   

   public AIBlockChainMessageHandler() {	   
	   intializeAIBlockChainListenerClient(getAIBlockChainApiAdapter());
	   
	   //Preconditions
	   assert aiBlockChainListenerClient != null : "the AIBlockChainListenerClient must be constructed before the WebSocketServer";

	   aiBlockChainListenerClient.addHanaListener();
   }
   
	@Override
	public String handleMessage(ChannelHandlerContext ctx, String frameText) {
		this.channel.set(ctx.channel());
		BlockResponse blockResponse = new BlockResponse();
		BlockRequest blockRequest = gson.fromJson(frameText, BlockRequest.class);
      
	    logger.info("Block Server handleMessage " + frameText);
	    
	    if (blockRequest.getCommand() != null) {
	    	if ("getnewblock".equals(blockRequest.getCommand())) {
	        	 logger.info("Getting blocks from ai block chain starting from number "+blockRequest.getBlockNumber()
	        			 													+" to " + blockRequest.getNumberOfBlocks());
				HanaItems hanaItems = getAIBlockChainApiAdapter().getBlocksStartingWith(blockRequest.getBlockNumber(), 
																						blockRequest.getNumberOfBlocks());
				if (hanaItems == null)
					blockResponse.setResult("failure-getnewblock request failed");
				else
					blockResponse.setResult("success-" + gson.toJson(hanaItems));
	         } else {
	        	 blockResponse.setResult("Failed. Command not recognized.");
	         }
	      } else {
	    	  blockResponse.setResult("Failed. Command not recognized.");
	      }
	      String response = gson.toJson(blockResponse);
	      return response;		
	}

}
