package com.aiblockchain.model.hana;

/**
 * HanaTransactionOutputInfo.java
 *
 * Description: Contains AI Coin transaction output information for the SAP HANA 2 demonstration.
 *
 * Copyright (C) Apr 20, 2017, Stephen L. Reed.
 */
public class HanaTransactionOutputInfo {

  // the parent's transaction id
  private String parentTransactionId;
  // the transaction output index
  private int transactionOutputIndex;
  // the transaction output address, or "to multisig", "to unknown type"
  private String address;
  // the amount
  private double amount;

  /**
   * Creates a new instance of HanaTransactionOutputInfo.
   *
   * @param parentTransactionId the parent's transaction id
   * @param transactionOutputIndex the transaction output index
   * @param address the transaction output address, or "to multisig", "to unknown type"
   * @param amount the amount
   */
  public HanaTransactionOutputInfo(
          final String parentTransactionId,
          final int transactionOutputIndex,
          final String address,
          final double amount) {
    //Preconditions
    //assert StringUtils.isNonEmptyString(parentTransactionId) : "parentTransactionId must be a non-empty string";	
    assert transactionOutputIndex >= 0 : "transactionOutputIndex must not be negative";

	this.parentTransactionId = parentTransactionId;
    this.transactionOutputIndex = transactionOutputIndex;
    this.address = address;
    this.amount = amount;
  }

  /** Gets the parent's transaction id.
   *
   * @return the parent's transaction id
   */
  public String getParentTransactionId() {
    return parentTransactionId;
  }
  
  /**
   * Gets the transaction output index;
   *
   * @return the transaction output index
   */
  public int getTransactionOutputIndex() {
    return transactionOutputIndex;
  }

  /**
   * Gets the transaction output address, or "to multisig", "to unknown type".
   *
   * @return the transaction output address, or "to multisig", "to unknown type"
   */
  public String getAddress() {
    return address;
  }
  
  /**
   * Gets the amount.
   *
   * @return the amount
   */
  public double getAmount() {
    return amount;
  }

  /** Sets the parent's transaction id.
   *
   * @return None
   */
  public void setParentTransactionId() {
    this.parentTransactionId = parentTransactionId;
  }

  /**
   * Sets the transaction output index;
   *
   * @return None
   */
  public void setTransactionOutputIndex() {
    this.transactionOutputIndex = transactionOutputIndex;
  }

  /**
   * Sets the transaction output address, or "to multisig", "to unknown type".
   *
   * @return None
   */
  public void setAddress() {
    this.address = address;
  }
  
  /**
   * Sets the amount.
   *
   * @return None
   */
  public void setAmount() {
    this.amount = amount;
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
            .append("[HanaTransactionOutputInfo\n")
            .append("  parentTransactionId: ")
            .append(parentTransactionId)
            .append("\n  transactionOutputIndex: ")
            .append(transactionOutputIndex)
            .append("\n  address: ")
            .append(address)
            .append("\n  amount: ")
            .append(amount)
            .append("]");
    return stringBuilder.toString();
  }

}
