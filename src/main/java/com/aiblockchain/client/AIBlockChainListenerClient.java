/**
 * Provides a HANA listener and an API adapter for communication with the software agents that manage the blockchain.
 */
package com.aiblockchain.client;

import com.aiblockchain.model.hana.HanaItems;
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
  private AIBlockChainListenerClient() {
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
   * Returns a list of blocks starting with the given block number.
   *
   * @param startingBlockNumber the given block number of the first block returned
   * @param nbrOfBlocks the number of blocks to return, avoiding very large data structures possible with unlimited request
   *
   * @return the block items for blocks starting with the given block number limited by the number of blocks to return, or because the
   * blockchain has been exhausted by this request
   */
  public HanaItems getBlocksStartingWith(
          final long startingBlockNumber,
          final int nbrOfBlocks) {
    //Preconditions
    assert startingBlockNumber >= 0 : "startingBlockNumber must not be negative";
    assert nbrOfBlocks > 0 : "nbrOfBlocks must be positive";

    return apiAdapter.getBlocksStartingWith(startingBlockNumber, nbrOfBlocks);
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
   * @param hanaBlockItem the HANA 3 demonstration block item, which is a container for block, transactions, inputs and outputs, as POJOs
   * easy to serialize to JSON
   */
  @Override
  public void newBlockNotification(HanaItems.HanaBlockItem hanaBlockItem) {
    //Preconditions
    assert hanaBlockItem != null : "hanaBlockItem must not be null";

    LOGGER.info("newBlockNotification: " + hanaBlockItem);
    System.out.println("redundant to check logger - newBlockNotification: " + hanaBlockItem);

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
