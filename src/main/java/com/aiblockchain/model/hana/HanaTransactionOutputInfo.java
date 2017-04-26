package com.aiblockchain.model.hana;

/**
 * HanaTransactionOutputInfo.java
 *
 * Description: Contains AI Coin transaction output information for the SAP HANA 2 demonstration.
 *
 * Copyright (C) Apr 20, 2017, Stephen L. Reed.
 */
public class HanaTransactionOutputInfo {

  // the block number
  private int blockNumber;
  // the transaction index
  private int transactionIndex;
  // the transaction id
  private String transactionId;
  // the transaction output index
  private int transactionOutputIndex;
  // the transaction output address, or "to multisig", "to unknown type"
  private String address;
  // the amount in aicoin
  private double amount;

  /**
   * Creates a new empty instance of HanaTransactionOutputInfo.
   */
  public HanaTransactionOutputInfo() {
  }
  
  /**
   * Creates a new instance of HanaTransactionOutputInfo.
   * @param blockNumber the block number
   * @param transactionIndex the transaction index
   * @param transactionId the transaction id
   * @param transactionOutputIndex
   * @param address the transaction output address, or "to multisig", "to unknown type"
   * @param amount the amount in aicoin
   */
  public HanaTransactionOutputInfo(
          final int blockNumber,
          final int transactionIndex,
          final String transactionId,
          final int transactionOutputIndex,
          final String address,
          final double amount) {
    //Preconditions
    assert blockNumber >= 0 : "block number must not be negative";
    assert transactionId != null : "transactionId must not be null";
    assert !transactionId.isEmpty() : "transactionId must not be an empty string";
    assert transactionOutputIndex >= 0 : "transactionOutputIndex must not be negative";
    assert address != null : "address must not be null";
    assert !address.isEmpty() : "address must not be empty";

    this.blockNumber = blockNumber;
    this.transactionIndex = transactionIndex;
    this.transactionId = transactionId;
    this.transactionOutputIndex = transactionOutputIndex;
    this.address = address;
    this.amount = amount;
  }
  
  /** gets the block number.
   *
   * @return the block number
   */
  public int geBlockNumber() {
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
   * Gets the transaction output index;
   *
   * @return the transaction output index
   */
  public int getTransactionOutputIndex() {
    return transactionOutputIndex;
  }

  /**
   * Sets the transaction output index.
   *
   * @param transactionOutputIndex the transaction output index
   */
  public void setTransactionOutputIndex(final int transactionOutputIndex) {
    //Preconditions
    assert transactionOutputIndex >= 0 : "transactionOutputIndex must not be negative";
    
    this.transactionOutputIndex = transactionOutputIndex;
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
   * Sets the transaction output address, or "to multisig", "to unknown type".
   *
   * @param address the transaction output address, or "to multisig", "to unknown type"
   */
  public void setAddress(final String address) {
    //Preconditions
    assert address != null : "address must not be null";
    assert !address.isEmpty() : "address must not be empty";
    
    this.address = address;
  }
  /**
   * Gets the amount.
   *
   * @return the amount
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Sets the amount.
   *
   * @param amount the amount
   */
  public void setAmount(final double amount) {
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
            .append("  blockNumber: ")
            .append(blockNumber)
            .append("\n  transactionIndex: ")
            .append(transactionIndex)
            .append("\n  transactionId: ")
            .append(transactionId)
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
