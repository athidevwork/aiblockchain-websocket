package com.aiblockchain.model.hana;

import java.util.List;

/**
 * HanaTransactionInfo.java
 *
 * Description: Contains AI Coin transaction information for the SAP HANA 2 demonstration.
 *
 * Copyright (C) Apr 20, 2017, Stephen L. Reed.
 */
public class HanaTransactionInfo {

  // the block number
  private int blockNumber;
  // the transaction index
  private int transactionIndex;
  // the transaction id
  private String transactionId;
  // the transaction number of inputs
  private short transactionNoOfInputs;
  // the transaction number of outputs
  private short transactionNoOfOutputs;

  /**
   * Creates a new instance of HanaTransactionInfo.
   *
   * @param transactionIndex the transaction index
   * @param transactionId the transaction id
   * @param transactionNoOfInputs the number of transactions in a block
   * @param transactionNoOfOutputs the number of transactions in a block
   */
  public HanaTransactionInfo(
          final int blockNumber,
          final int transactionIndex,
          final String transactionId,
		  final short transactionNoOfInputs,
          final short transactionNoOfOutputs) {
    //Preconditions
    assert blockNumber > 0 : "Block number has to be greater than 0";	
    assert transactionIndex >= 0 : "transactionIndex must not be negative";
    //assert StringUtils.isNonEmptyString(transactionId) : "transactionId must be a non-empty string";
    assert transactionNoOfInputs > 0 : "transactionNoOfInputs must not be negative";
    assert transactionNoOfOutputs > 0 : "transactionNoOfOutputs must not be negative";

	this.blockNumber = blockNumber;
    this.transactionIndex = transactionIndex;
    this.transactionId = transactionId;
    this.transactionNoOfInputs = transactionNoOfInputs;
    this.transactionNoOfOutputs = transactionNoOfOutputs;	
  }

  /**
   * Gets the Block Number.
   *
   * @return the block number
   */
  public int getBlockNumber() {
    return blockNumber;
  }
  
  /**
   * Gets the transaction index.
   *
   * @return the transaction index
   */
  public int getTransactionIndex() {
    return transactionIndex;
  }

  /**
   * Gets the transaction id.
   *
   * @return the transaction id
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * Gets the number of transaction inputs.
   *
   * @return the number of transaction inputs
   */
  public short getTransactionNoOfInputs() {
    return transactionNoOfInputs;
  }

  /**
   * Gets the number of transaction outputs.
   *
   * @return the number of transaction outputs
   */
  public short getTransactionNoOfOutputs() {
    return transactionNoOfOutputs;
  }

  /**
   * Sets the Block Number.
   *
   * @return None
   */
  public void setBlockNumber() {
    this.blockNumber = blockNumber;	
  }  
  
  /**
   * Sets the transaction index.
   *
   * @return None
   */
  public void setTransactionIndex() {
    this.transactionIndex = transactionIndex;
  }

  /**
   * Sets the transaction id.
   *
   * @return None
   */
  public void setTransactionId() {
    this.transactionId = transactionId;
  }

  /**
   * Sets the number of transaction inputs.
   *
   * @return None
   */
  public void setTransactionNoOfInputs() {
    this.transactionNoOfInputs = transactionNoOfInputs;
  }

  /**
   * Sets the number of transaction outputs.
   *
   * @return None
   */
  public void setTransactionNoOfOutputs() {
    this.transactionNoOfOutputs = transactionNoOfOutputs;		
  }
  
  /**
   * Returns a string representation of this object.
   *
   * @return a string representation of this object
   */
  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder
            .append("[HanaTransactionInfo\n")
            .append("  blockNumber: ")
            .append(blockNumber)
            .append("\n  transactionIndex: ")
            .append(transactionIndex)
            .append("\n  transactionId: ")
            .append(transactionId)			
            .append("\n  transactionNoOfInputs: ")
            .append(getTransactionNoOfInputs())
            .append("\n  transactionNoOfOutputs: ")
            .append(getTransactionNoOfOutputs())
            .append("]");
    return stringBuilder.toString();
  }

}
