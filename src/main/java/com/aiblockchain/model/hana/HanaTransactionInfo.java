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

  // the transaction index
  private final int transactionIndex;
  // the transaction id
  private final String transactionId;
  // the transaction input infos
  private final List<HanaTransactionInputInfo> hanaTransactionInputInfos;
  // the transaction output infos
  private final List<HanaTransactionOutputInfo> hanaTransactionOutputInfos;

  /**
   * Creates a new instance of HanaTransactionInfo.
   *
   * @param transactionIndex the transaction index
   * @param transactionId the transaction id
   * @param hanaTransactionInputInfos the transaction input infos
   * @param hanaTransactionOutputInfos the transaction output infos
   */
  public HanaTransactionInfo(
          final int transactionIndex,
          final String transactionId,
          final List<HanaTransactionInputInfo> hanaTransactionInputInfos,
          final List<HanaTransactionOutputInfo> hanaTransactionOutputInfos) {
    //Preconditions
    assert transactionIndex >= 0 : "transactionIndex must not be negative";
    //assert StringUtils.isNonEmptyString(transactionId) : "transactionId must be a non-empty string";
    assert hanaTransactionInputInfos != null : "hanaTransactionInputInfos must not be null";
    assert hanaTransactionOutputInfos != null : "hanaTransactionOutputInfos must not be null";
    assert !hanaTransactionOutputInfos.isEmpty() : "hanaTransactionOutputInfos must not be empty";

    this.transactionIndex = transactionIndex;
    this.transactionId = transactionId;
    this.hanaTransactionInputInfos = hanaTransactionInputInfos;
    this.hanaTransactionOutputInfos = hanaTransactionOutputInfos;
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
   * Gets the transaction input infos.
   *
   * @return the transaction input infos
   */
  public List<HanaTransactionInputInfo> getHanaTransactionInputInfos() {
    return hanaTransactionInputInfos;
  }

  /**
   * the transaction output infos
   *
   * @return the transaction output infos
   */
  public List<HanaTransactionOutputInfo> getHanaTransactionOutputInfos() {
    return hanaTransactionOutputInfos;
  }

  /**
   * Gets the number of transaction inputs.
   *
   * @return the number of transaction inputs
   */
  public short getTransactionNoOfInputs() {
    return (short) hanaTransactionInputInfos.size();
  }

  /**
   * Gets the number of transaction outputs.
   *
   * @return the number of transaction outputs
   */
  public short getTransactionNoOfOutputs() {
    return (short) hanaTransactionOutputInfos.size();
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
            .append("  transactionIndex: ")
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
