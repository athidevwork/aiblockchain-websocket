/**
 *
 */
package com.aiblockchain.server.websocket;

import com.aiblockchain.client.AIBlockChainListenerClient;
import com.aiblockchain.client.AbstractAPIAdapter;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.server.StringUtils;
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

  public AIBlockChainMessageHandler() {
    //Preconditions
    assert AIBlockChainListenerClient.getInstance() != null : "the AIBlockChainListenerClient must be constructed before the WebSocketServer";

    AIBlockChainListenerClient.getInstance().addHanaListener();
  }

  @Override
  public String handleMessage(ChannelHandlerContext ctx, String frameText) {
    this.channel.set(ctx.channel());
    BlockResponse blockResponse = new BlockResponse();
    BlockRequest blockRequest = gson.fromJson(frameText, BlockRequest.class);

    logger.info("Block Server handleMessage " + frameText);
    System.out.println(StringUtils.log(logger) + "Block Server handleMessage " + frameText);

    final String command = blockRequest.getCommand();
    logger.info("command " + command);
    System.out.println(StringUtils.log(logger) + "command " + command);

    if (command != null) {
      if ("getnewblock".equals(command)) {
        logger.info("Getting blocks from ai block chain starting from number " + blockRequest.getBlockNumber()
                + " to " + blockRequest.getNumberOfBlocks());
        System.out.println(StringUtils.log(logger) + "Getting blocks from ai block chain starting from number " + blockRequest.getBlockNumber()
                + " to " + blockRequest.getNumberOfBlocks());
        HanaItems hanaItems = AbstractAPIAdapter.getInstance().getBlocksStartingWith(
                blockRequest.getBlockNumber(),
                blockRequest.getNumberOfBlocks());
        if (hanaItems == null) {
          blockResponse.setResult("failure-getnewblock request failed");
        } else {
          blockResponse.setResult(gson.toJson(hanaItems));
        }
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
