package com.aiblockchain.model.hana;

/**
 * HanaTransactionOutputInfo.java
 *
 * Description: Contains AI Coin transaction output information for the SAP HANA 2 demonstration.
 *
 * Copyright (C) Apr 20, 2017, Stephen L. Reed.
 */
public class HanaTransactionOutputInfo {

  // the transaction output index
  private final int transactionOutputIndex;
  // the transaction output address, or "to multisig", "to unknown type"
  private final String address;
  // the amount
  private double amount;

  /**
   * Creates a new instance of HanaTransactionOutputInfo.
   *
   * @param transactionOutputIndex the transaction output index
   * @param address the transaction output address, or "to multisig", "to unknown type"
   * @param amount the amount
   */
  public HanaTransactionOutputInfo(
          final int transactionOutputIndex,
          final String address,
          final double amount) {
    //Preconditions
    assert transactionOutputIndex >= 0 : "transactionOutputIndex must not be negative";

    this.transactionOutputIndex = transactionOutputIndex;
    this.address = address;
    this.amount = amount;
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
            .append("  transactionOutputIndex: ")
            .append(transactionOutputIndex)
            .append("\n  address: ")
            .append(address)
            .append("\n  amount: ")
            .append(amount)
            .append("]");
    return stringBuilder.toString();
  }

}
