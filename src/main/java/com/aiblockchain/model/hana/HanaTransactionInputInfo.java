package com.aiblockchain.model.hana;

/**
 * HanaTransactionInputInfo.java
 *
 * Description: Contains AI Coin transaction input information for the SAP HANA 2 demonstration.
 *
 * Copyright (C) Apr 20, 2017, Stephen L. Reed.
 */
public class HanaTransactionInputInfo {

  // the block number
  private int blockNumber;
  // the transaction index
  private int transactionIndex;
  // the transaction id
  private String transactionId;
  // the parent's transaction id
  private String parentTransactionId;
  // the transaction input index
  private int transactionInputIndex;
  // this transaction input's index into its parent transction's outputs
  private int indexIntoParentTransaction;

  /**
   * Creates a new empty instance of HanaTransactionInputInfo.
   */
  public HanaTransactionInputInfo() {
  }

  /**
   * Creates a new instance of HanaTransactionInputInfo.
   * @param blockNumber the block number
   * @param transactionIndex the transaction index
   * @param transactionId the transaction id
   * @param parentTransactionId the parent's transaction id
   * @param transactionInputIndex the transaction input index
   * @param indexIntoParentTransaction this transaction input's index into its parent transaction's outputs
   */
  public HanaTransactionInputInfo(
          final int blockNumber,
          final int transactionIndex,
          final String transactionId,
          final String parentTransactionId,
          final int transactionInputIndex,
          final int indexIntoParentTransaction) {
    //Preconditions
    assert blockNumber >= 0 : "block number must not be negative";
    assert transactionId != null : "transactionId must not be null";
    assert !transactionId.isEmpty() : "transactionId must not be an empty string";
    assert parentTransactionId != null : "parentTransactionId must not be null";
    assert !parentTransactionId.isEmpty() : "parentTransactionId must not be an empty string";
    assert transactionInputIndex >= 0 : "transactionInputIndex must not be negative";
    assert indexIntoParentTransaction >= 0 : "indexIntoParentTransaction must not be negative";

    this.blockNumber = blockNumber;
    this.transactionIndex = transactionIndex;
    this.transactionId = transactionId;
    this.parentTransactionId = parentTransactionId;
    this.transactionInputIndex = transactionInputIndex;
    this.indexIntoParentTransaction = indexIntoParentTransaction;
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

  /** Gets the parent's transaction id.
   *
   * @return the parent's transaction id
   */
  public String getParentTransactionId() {
    return parentTransactionId;
  }

  /** Sets the parent's transaction id.
   *
   * @param parentTransactionId
   */
  public void setParentTransactionId(final String parentTransactionId) {
    //Preconditions
    assert parentTransactionId != null : "parentTransactionId must not be null";
    assert !parentTransactionId.isEmpty() : "parentTransactionId must not be an empty string";
    
    this.parentTransactionId = parentTransactionId;
  }

  /** Gets the transaction input index.
   *
   * @return the transaction input index
   */
  public int getTransactionInputIndex() {
    return transactionInputIndex;
  }

  /** Sets the transaction input index.
   *
   * @param transactionInputIndex the transaction input index
   */
  public void setTransactionInputIndex(final int transactionInputIndex) {
    //Preconditions
    assert transactionInputIndex >= 0 : "transactionInputIndex must not be negative";
    
    this.transactionInputIndex = transactionInputIndex;
  }
  /** Gets this transaction input's index into its parent transction's outputs.
   *
   * @return this transaction input's index into its parent transction's outputs
   */
  public int getIndexIntoParentTransaction() {
    return indexIntoParentTransaction;
  }

  /** Sets this transaction input's index into its parent transction's outputs.
   *
   */
  public void setIndexIntoParentTransaction(final int indexIntoParentTransaction) {
    //Preconditions
    assert indexIntoParentTransaction >= 0 : "indexIntoParentTransaction must not be negative";
    
    this.indexIntoParentTransaction = indexIntoParentTransaction;
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
            .append("[HanaTransactionInputInfo\n")
            .append("  blockNumber: ")
            .append(blockNumber)
            .append("\n  transactionIndex: ")
            .append(transactionIndex)
            .append("\n  transactionId: ")
            .append(transactionId)
            .append("\n  parentTransactionId: ")
            .append(parentTransactionId)
            .append("\n  indexIntoParentTransaction: ")
            .append(indexIntoParentTransaction)            
            .append("\n  transactionInputIndex: ")
            .append(transactionInputIndex)
            .append("]");
    return stringBuilder.toString();
  }
}
