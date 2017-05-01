/**
 * Provides a HANA listener and an API adapter for communication with the software agents that manage the blockchain.
 */
package com.aiblockchain.client;

import com.aiblockchain.listener.HanaListener;
import com.aiblockchain.model.hana.HanaItems;
import com.aiblockchain.server.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Athi
 * @author reed
 *
 */
public class AIBlockChainListenerClient implements HanaListener {

  // the logger
  private static final Logger LOGGER = Logger.getLogger(AIBlockChainListenerClient.class);
  // the singleton instance
  private static AIBlockChainListenerClient aiBlockChainListenerClient;
  // the API adapter
  private AbstractAPIAdapter apiAdapter;

  /**
   * Constructs a new AIBlockChainListenerClient instance.
   */
  protected AIBlockChainListenerClient() {
    aiBlockChainListenerClient = this;
  }

  /**
   * Returns the singleton instance of this class.
   *
   * @return the singleton instance of this class
   */
  public static synchronized AIBlockChainListenerClient getInstance() {
    if (aiBlockChainListenerClient == null) {
      aiBlockChainListenerClient = new AIBlockChainListenerClient();
    }
    return aiBlockChainListenerClient;
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
  public void newBlockNotification(HanaItems.HanaBlockItem hanaBlockItem) {
    //Preconditions
    assert hanaBlockItem != null : "hanaBlockItem must not be null";

    LOGGER.info("newBlockNotification: " + hanaBlockItem);
    System.out.println(StringUtils.log(LOGGER) + "newBlockNotification: " + hanaBlockItem);

    //TODO pass the new block item to the HANA client via the web socket connection 
  }

  /**
   * Returns a string representation of this object for debugging.
   *
   * @return a string representation of this object
   */
  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[AIBlockChainListenerClient, singleton instance present: ");
    if (aiBlockChainListenerClient == null) {
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
}
