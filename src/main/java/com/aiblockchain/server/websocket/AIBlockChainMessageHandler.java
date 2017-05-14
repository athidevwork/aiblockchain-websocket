/**
 *
 */
package com.aiblockchain.server.websocket;

import com.aiblockchain.listener.AIBlockChainListener;
import com.aiblockchain.client.AbstractAPIAdapter;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;
import com.aiblockchain.server.StringUtils;
import com.aiblockchain.server.websocket.blockticker.BlockRequest;
import com.aiblockchain.server.websocket.blockticker.BlockResponse;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

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

    BlockResponse blockResponse = new BlockResponse();
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
    return response;
  }
}
