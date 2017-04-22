package com.aiblockchain.model.hana;

/**
 * HanaTransactionInputInfo.java
 *
 * Description: Contains AI Coin transaction input information for the SAP HANA 2 demonstration.
 *
 * Copyright (C) Apr 20, 2017, Stephen L. Reed.
 */
public class HanaTransactionInputInfo {

  // the transaction input index
  private final int transactionInputIndex;
  // the parent's transaction id
  private final String parentTransactionId;
  // this transaction input's index into its parent transction's outputs
  private final int indexIntoParentTransaction;

  /**
   * Creates a new instance of HanaTransactionInputInfo.
   * @param transactionInputIndex the transaction input index
   * @param parentTransactionId the parent's transaction id
   * @param indexIntoParentTransaction this transaction input's index into its parent transction's outputs
   */
  public HanaTransactionInputInfo(
          final int transactionInputIndex,
          final String parentTransactionId,
          final int indexIntoParentTransaction) {
    //Preconditions
    assert transactionInputIndex >= 0 : "transactionInputIndex must not be negative";
    //assert StringUtils.isNonEmptyString(parentTransactionId) : "parentTransactionId must be a non-empty string";
    assert indexIntoParentTransaction >= 0 : "indexIntoParentTransaction must not be negative";

    this.transactionInputIndex = transactionInputIndex;
    this.parentTransactionId = parentTransactionId;
    this.indexIntoParentTransaction = indexIntoParentTransaction;
  }

  /** Gets the transaction input index.
   *
   * @return the transaction input index
   */
  public int getTransactionInputIndex() {
    return transactionInputIndex;
  }

  /** Gets the parent's transaction id.
   *
   * @return the parent's transaction id
   */
  public String getParentTransactionId() {
    return parentTransactionId;
  }

  /** Gets this transaction input's index into its parent transction's outputs.
   *
   * @return this transaction input's index into its parent transction's outputs
   */
  public int getIndexIntoParentTransaction() {
    return indexIntoParentTransaction;
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
            .append("  transactionInputIndex: ")
            .append(transactionInputIndex)
            .append("\n  parentTransactionId: ")
            .append(parentTransactionId)
            .append("\n  indexIntoParentTransaction: ")
            .append(indexIntoParentTransaction)
            .append("]");
    return stringBuilder.toString();
  }
}
