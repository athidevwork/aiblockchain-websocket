package com.aiblockchain.model.hana;

import com.aiblockchain.server.StringUtils;

/**
 * HanaTransactionInputInfo.java
 *
 * Description: Contains AI Coin transaction input information for the SAP HANA 2 demonstration.
 *
 * Copyright (C) Apr 20, 2017, Stephen L. Reed.
 */
public class HanaTransactionInputInfo {

  // the parent's transaction id
  private String parentTransactionId;
  // the transaction input index
  private int transactionInputIndex;
  // this transaction input's index into its parent transction's outputs
  private int indexIntoParentTransaction;

  /**
   * Creates a new instance of HanaTransactionInputInfo.
   * @param parentTransactionId the parent's transaction id
   * @param transactionInputIndex the transaction input index
   * @param indexIntoParentTransaction this transaction input's index into its parent transction's outputs
   */
  public HanaTransactionInputInfo(
          final String parentTransactionId,
          final int transactionInputIndex,
          final int indexIntoParentTransaction) {
    //Preconditions
    assert StringUtils.isNonEmptyString(parentTransactionId) : "parentTransactionId must be a non-empty string";
    assert transactionInputIndex >= 0 : "transactionInputIndex must not be negative";
    assert indexIntoParentTransaction >= 0 : "indexIntoParentTransaction must not be negative";

    this.parentTransactionId = parentTransactionId;
    this.transactionInputIndex = transactionInputIndex;
    this.indexIntoParentTransaction = indexIntoParentTransaction;
  }

  /** Gets the parent's transaction id.
   *
   * @return the parent's transaction id
   */
  public String getParentTransactionId() {
    return parentTransactionId;
  }

  /** Gets the transaction input index.
   *
   * @return the transaction input index
   */
  public int getTransactionInputIndex() {
    return transactionInputIndex;
  }

  /** Gets this transaction input's index into its parent transction's outputs.
   *
   * @return this transaction input's index into its parent transction's outputs
   */
  public int getIndexIntoParentTransaction() {
    return indexIntoParentTransaction;
  }

  /** Sets the parent's transaction id.
 * @param parentTransactionId 
   *
   * @return None
   */
  public void setParentTransactionId(String parentTransactionId) {
    this.parentTransactionId = parentTransactionId;
  }

  /** Sets the transaction input index.
 * @param transactionInputIndex 
   *
   * @return None
   */
  public void setTransactionInputIndex(int transactionInputIndex) {
    this.transactionInputIndex = transactionInputIndex;
  }

  /** Sets this transaction input's index into its parent transction's outputs.
 * @param indexIntoParentTransaction 
   *
   * @return None
   */
  public void setIndexIntoParentTransaction(int indexIntoParentTransaction) {
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
            .append("  parentTransactionId: ")
            .append(parentTransactionId)
            .append("\n  transactionInputIndex: ")
            .append(transactionInputIndex)
            .append("\n  indexIntoParentTransaction: ")
            .append(indexIntoParentTransaction)
            .append("]");
    return stringBuilder.toString();
  }
}
