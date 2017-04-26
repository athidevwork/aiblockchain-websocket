package com.aiblockchain.listener;

import com.aiblockchain.model.hana.HanaItems.HanaBlockItem;

/**
 * Created on Apr 20, 2017, 9:17:33 PM.
 *
 * Description: Defines a HANA 2 demonstration listener for new blocks.
 *
 * Copyright (C) Apr 20, 2017, Stephen L. Reed, Texai.org.
 *
 * @author reed
 */
public interface HanaListener {

  /** Receives notification of a new block which has been appended on to the blockchain.
   *
   * @param hanaBlockItem the HANA 3 demonstration block item, which is a container for block, transactions, inputs and outputs, as
   * POJOs easy to serialize to JSON
   */
  public void newBlockNotification(final HanaBlockItem hanaBlockItem);
}
