package com.aiblockchain.client;

import com.aiblockchain.listener.HanaListener;
import com.aiblockchain.model.hana.HanaItems;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * APIAdapter.java
 *
 * Description: Provides an abstract API adapter for AI Coin and AI Blockchain to communicate with the cloud.
 *
 * Copyright (C) Apr 19, 2017, AI Coin, Inc.
 */
public abstract class AbstractAPIAdapter {

  // the singleton instance
  private static AbstractAPIAdapter apiAdapter;
  // the HANA demonstration listeners
  private final Set<HanaListener> hanaListeners = new HashSet<>();

  /**
   * Constructs an AbstractAPIAdapter instance.
   *
   */
  public AbstractAPIAdapter() {
    apiAdapter = this;
  }

  /** Gets the singleton instance of this class.
   * 
   * @return the singleton instance of this class, or null if not yet initialized by the AICOperation agent
   */
  public static AbstractAPIAdapter getInstance() {
    return apiAdapter;
  }
  
  /** Adds a HANA 2 demonstration listener.
   * 
   * @param hanaListener the HANA 2 demonstration listener which will receive notification of new aicoin-qt blocks
   */
  public void addHanaListener(final HanaListener hanaListener) {
    //Preconditions
    assert hanaListener != null : "hanaListener must not be null";

    getLogger().info("adding HanaListener " + hanaListener);
    hanaListeners.add(hanaListener);
  }

  /** Returns a list of blocks starting with the given block number.
   * 
   * @param startingBlockNumber the given block number of the first block returned
   * @param nbrOfBlocks the number of blocks to return, avoiding very large data structures possible with unlimited request
   * 
   * @return the block items for blocks starting with the given block number limited by the number of blocks to return, or because 
   * the blockchain has been exhausted by this request
   */
  public abstract HanaItems getBlocksStartingWith(
          final long startingBlockNumber,
          final int nbrOfBlocks);
  
  /** Gets the HANA demonstration listeners.
   * 
   * @return the HANA demonstration listeners
   */
  protected Set<HanaListener> getHanaListeners() {
    return hanaListeners;
  }
  
  /** Gets the logger.
   * 
   * @return the logger
   */
  abstract protected Logger getLogger();
}
