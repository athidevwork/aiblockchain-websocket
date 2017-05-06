/**
 * Provides a HANA listener and an API adapter for communication with the software agents that manage the blockchain.
 */
package com.aiblockchain.listener;

import com.aiblockchain.client.AbstractAPIAdapter;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.server.StringUtils;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.log4j.Logger;

/**
 * @author Athi
 * @author reed
 *
 */
public class AIBlockChainListener implements HanaListener {

  // the logger
  private static final Logger LOGGER = Logger.getLogger(AIBlockChainListener.class);
  // the singleton instance
  private static AIBlockChainListener aiBlockChainListener;
  // the API adapter
  private AbstractAPIAdapter apiAdapter;
  // the current channel recorded from the received message handler so we can initiate outbound messages here to the API client
  private AtomicReference<Channel> atomicChannel = new AtomicReference<Channel>();

  /**
   * Constructs a new AIBlockChainListener instance.
   */
  protected AIBlockChainListener() {
    aiBlockChainListener = this;
  }

  /**
   * Returns the singleton instance of this class.
   *
   * @return the singleton instance of this class
   */
  public static synchronized AIBlockChainListener getInstance() {
    if (aiBlockChainListener == null) {
      aiBlockChainListener = new AIBlockChainListener();
    }
    return aiBlockChainListener;
  }

  /**
   * Adds this HANA 2 demonstration listener.
   */
  public void addHanaListener() {
    apiAdapter.addHanaListener(this);
  }

  /**
   * Sets the API adapter.
   *
   * @param apiAdapter the API adapter
   */
  public void setApiAdapter(final AbstractAPIAdapter apiAdapter) {
    //Preconditions
    assert apiAdapter != null : "apiAdapter must not be null";

    this.apiAdapter = apiAdapter;
  }

  /**
   * Receives notification of a new block which has been appended on to the blockchain.
   *
   * @param hanaBlockItem the HANA 2 demonstration block item, which is a container for block, transactions, inputs and outputs, as POJOs
   * easy to serialize to JSON
   */
  @Override
  public void newBlockNotification(final HanaItems.HanaBlockItem hanaBlockItem) {
    //Preconditions
    assert hanaBlockItem != null : "hanaBlockItem must not be null";

    final Channel channel = atomicChannel.get();
    if (channel == null) {
      LOGGER.info("cannot sent new block to API client without a channel: " + hanaBlockItem);
      System.out.println(StringUtils.log(LOGGER) + "cannot sent new block to API client without a channel: " + hanaBlockItem);
    } else {
      LOGGER.info("newBlockNotification: " + hanaBlockItem);
      System.out.println(StringUtils.log(LOGGER) + "newBlockNotification: " + hanaBlockItem);
      LOGGER.info("channel is writable: " + channel.isWritable());
      System.out.println(StringUtils.log(LOGGER) + "channel is writable: " + channel.isWritable());

      final Gson gson = new Gson();
      channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(hanaBlockItem)));
    }
  }

  /**
   * Returns a string representation of this object for debugging.
   *
   * @return a string representation of this object
   */
  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[AIBlockChainListener, singleton instance present: ");
    if (aiBlockChainListener == null) {
      stringBuilder.append("false");
    } else {
      stringBuilder.append("true");
    }
    stringBuilder.append(", API adapter present: ");
    if (apiAdapter == null) {
      stringBuilder.append("false");
    } else {
      stringBuilder.append("true");
    }
    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  /**
   * Gets the current channel recorded from the received message handler so we can initiate outbound messages here to the API client.
   *
   * @return the current channel
   */
  public Channel getChannel() {
    return atomicChannel.get();
  }

  /**
   * Sets the current channel recorded from the received message handler so we can initiate outbound messages here to the API client.
   *
   * @param channel the current channel
   */
  public void setChannel(final Channel channel) {
    atomicChannel.set(channel);
  }
}
