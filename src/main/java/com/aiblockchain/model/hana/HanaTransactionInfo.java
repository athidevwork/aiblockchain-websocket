package com.aiblockchain.model.hana;

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
   */
  public HanaTransactionInfo() {
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
   * Sets the Block Number.
   *
   * @param blockNumber the block number
   */
  public void setBlockNumber(final int blockNumber) {
    //Preconditions
    assert blockNumber >= 0 : "block number must not be negative";

    this.blockNumber = blockNumber;
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
   * Sets the transaction index.
   *
   * @param transactionIndex the transaction index
   */
  public void setTransactionIndex(final int transactionIndex) {
    //Preconditions
    assert transactionIndex >= 0 : "transactionIndex must not be negative";

    this.transactionIndex = transactionIndex;
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
   * Sets the transaction id.
   *
   * @param transactionId the transaction id
   */
  public void setTransactionId(final String transactionId) {
    //Preconditions
    assert transactionId != null : "transactionId must not be null";
    assert !transactionId.isEmpty() : "transactionId must not be an empty string";

    this.transactionId = transactionId;
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
   * Sets the number of transaction inputs.
   *
   * @param transactionNoOfInputs the number of transaction inputs
   */
  public void setTransactionNoOfInputs(final short transactionNoOfInputs) {
    //Preconditions
    assert transactionNoOfInputs > 0 : "transactionNoOfInputs must be positive";

    this.transactionNoOfInputs = transactionNoOfInputs;
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
   * Sets the number of transaction outputs.
   *
   * @param transactionNoOfOutputs the number of transaction outputs
   */
  public void setTransactionNoOfOutputs(final short transactionNoOfOutputs) {
    //Preconditions
    assert transactionNoOfOutputs > 0 : "transactionNoOfOutputs must be positive";

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
            .append(transactionNoOfInputs)
            .append("\n  transactionNoOfOutputs: ")
            .append(transactionNoOfOutputs)
            .append("]");
    return stringBuilder.toString();
  }

}
