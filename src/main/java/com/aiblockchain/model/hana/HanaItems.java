/*
 * HanaItems.java
 *
 * Created on Apr 23, 2017, 10:42:07 AM
 *
 * Description: Contains HanaBlockInfos and constituents such that passed to the API module where they may be easily transformed 
* into JSON objects for clients.
 *
 * Copyright (C) 10:42:07 AM Stephen L. Reed, all rights reserved.
 *
 */
package com.aiblockchain.model.hana;

import com.aiblockchain.server.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A HanaItems object contains information about one or more aicoin blocks. The contained information objects are in a POJO form (Plain Old
 * Java Object) from which they be easily transformed into JSON for API clients.
 *
 *
 * @author reed
 */
public class HanaItems {

  // the logger
  private static final Logger LOGGER = Logger.getLogger(HanaItems.class);
  
  // the HANA 2 demonstration block items, default to empty until set by builder
  private List<HanaBlockItem> hanaBlockItems = new ArrayList<>();

  /**
   * Constructs a new HanaItems instance.
   */
  public HanaItems() {
  }

  /**
   * Gets the HANA 2 demonstration block items.
   *
   * @return the HANA 2 demonstration block items
   */
  public List<HanaBlockItem> getHanaBlockItems() {
    return hanaBlockItems;
  }

  /**
   * Sets the HANA 2 demonstration block items.
   *
   * @param hanaBlockItems the HANA 2 demonstration block items
   */
  public void setHanaBlockItems(List<HanaBlockItem> hanaBlockItems) {
    //Preconditions
    assert hanaBlockItems != null : "hanaBlockItems must not be null";

    this.hanaBlockItems = hanaBlockItems;
  }

  /**
   * Returns a string representation of this object.
   *
   * @return a string representation of this object
   */
  public String toString() {
    return (new StringBuilder())
            .append("[HanaItems, ")
            .append(hanaBlockItems.size())
            .append(" hanaBlockItems]")
            .toString();
  }

  /**
   * Contains a HANA 2 demonstration block information POJO, and a list of its transaction information items.
   */
  public static class HanaBlockItem {

    // the HANA 2 demonstration block information
    private HanaBlockInfo hanaBlockInfo;
    // the HANA 2 demonstration transaction informations
    private List<HanaTransactionItem> hanaTransactionItems;

    /**
     * Constructs an empty HanaBlockItem.
     */
    public HanaBlockItem() {
    }

    /**
     * Constructs an empty HanaBlockItem.
     *
     * @param hanaBlockInfo
     * @param hanaTransactionItems
     */
    public HanaBlockItem(
            HanaBlockInfo hanaBlockInfo,
            List<HanaTransactionItem> hanaTransactionItems) {
      //Preconditions
      assert hanaBlockInfo != null : "hanaBlockInfo must not be null";
      assert hanaTransactionItems != null : "hanaTransactionItems must not be null";
      assert !hanaTransactionItems.isEmpty() : "hanaTransactionItems must not be empty";

      this.hanaBlockInfo = hanaBlockInfo;
      this.hanaTransactionItems = hanaTransactionItems;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
      return "[HanaBlockItem " + hanaBlockInfo.toString() + "]";
    }

    /**
     * Gets the HANA 2 demonstration block information.
     *
     * @return the HANA 2 demonstration block information
     */
    public HanaBlockInfo getHanaBlockInfo() {
      return hanaBlockInfo;
    }

    /**
     * Sets the HANA 2 demonstration block information.
     *
     * @param hanaBlockInfo the HANA 2 demonstration block information
     */
    public void setHanaBlockInfo(final HanaBlockInfo hanaBlockInfo) {
      //Preconditions
      assert hanaBlockInfo != null : "hanaBlockInfo must not be null";

      this.hanaBlockInfo = hanaBlockInfo;
    }

    /**
     * Gets the HANA 2 demonstration transaction informations.
     *
     * @return the HANA 2 demonstration transaction informations
     */
    public List<HanaTransactionItem> getHanaTransactionItems() {
      return hanaTransactionItems;
    }

    /**
     * Sets the HANA 2 demonstration transaction informations.
     *
     * @param hanaTransactionItems the HANA 2 demonstration transaction informations
     */
    public void setHanaTransactionItems(final List<HanaTransactionItem> hanaTransactionItems) {
      //Preconditions
      assert hanaTransactionItems != null : "hanaTransactionItems must not be null";
      assert !hanaTransactionItems.isEmpty() : "hanaTransactionItems must not be empty";

      this.hanaTransactionItems = hanaTransactionItems;
    }
  }

  /**
   * Contains a HANA 2 demonstration transaction information POJO, and a list of its transaction input POJOs and transaction output POJOs.
   */
  public static class HanaTransactionItem {

    // the HANA 2 demonstration transaction information
    private HanaTransactionInfo hanaTransactionInfo;
    // the HANA 2 demonstration transaction input informations
    private List<HanaTransactionInputInfo> hanaTransactionInputInfos;
    // the HANA 2 demonstration transaction output informations
    private List<HanaTransactionOutputInfo> hanaTransactionOutputInfos;

    /**
     * Constructs an empty HanaTransactionItem.
     */
    public HanaTransactionItem() {
    }

    /**
     * Constructs a HanaTransactionItem given its constituents.
     *
     * @param hanaTransactionInfo the HANA 2 demonstration transaction information
     * @param hanaTransactionInputInfos the HANA 2 demonstration transaction input informations
     * @param hanaTransactionOutputInfos the HANA 2 demonstration transaction output informations
     */
    public HanaTransactionItem(
            final HanaTransactionInfo hanaTransactionInfo,
            final List<HanaTransactionInputInfo> hanaTransactionInputInfos,
            final List<HanaTransactionOutputInfo> hanaTransactionOutputInfos) {
      //Preconditions
      assert hanaTransactionInfo != null : "hanaTransactionInfo must not be null";
      assert hanaTransactionInputInfos != null : "hanaTransactionInputInfos must not be null";
      assert !hanaTransactionInputInfos.isEmpty() : "hanaTransactionInputInfos must not be empty";
      assert hanaTransactionOutputInfos != null : "hanaTransactionOutputInfos must not be null";
      assert !hanaTransactionOutputInfos.isEmpty() : "hanaTransactionOutputInfos must not be empty";

      this.hanaTransactionInfo = hanaTransactionInfo;
      this.hanaTransactionInputInfos = hanaTransactionInputInfos;
      this.hanaTransactionOutputInfos = hanaTransactionOutputInfos;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
      return "[HanaTransactionItem " + hanaTransactionInfo.toString() + "]";
    }

    /**
     * Gets the HANA 2 demonstration transaction information.
     *
     * @return the HANA 2 demonstration transaction information
     */
    public HanaTransactionInfo getHanaTransactionInfo() {
      return hanaTransactionInfo;
    }

    /**
     * Sets the HANA 2 demonstration transaction information.
     *
     * @param hanaTransactionInfo the HANA 2 demonstration transaction information
     */
    public void setHanaTransactionInfo(final HanaTransactionInfo hanaTransactionInfo) {
      //Preconditions
      assert hanaTransactionInfo != null : "hanaTransactionInfo must not be null";

      this.hanaTransactionInfo = hanaTransactionInfo;
    }

    /**
     * Gets the HANA 2 demonstration transaction input informations.
     *
     * @return the HANA 2 demonstration transaction input informations
     */
    public List<HanaTransactionInputInfo> getHanaTransactionInputInfos() {
      return hanaTransactionInputInfos;
    }

    /**
     * Sets the HANA 2 demonstration transaction input informations.
     *
     * @param hanaTransactionInputInfos the HANA 2 demonstration transaction input informations
     */
    public void setHanaTransactionInputInfos(final List<HanaTransactionInputInfo> hanaTransactionInputInfos) {
      //Preconditions
      assert hanaTransactionInputInfos != null : "hanaTransactionInputInfos must not be null";
      assert !hanaTransactionInputInfos.isEmpty() : "hanaTransactionInputInfos must not be empty";

      this.hanaTransactionInputInfos = hanaTransactionInputInfos;
    }

    /**
     * Gets the HANA 2 demonstration transaction output informations.
     *
     * @return the HANA 2 demonstration transaction output informations
     */
    public List<HanaTransactionOutputInfo> getHanaTransactionOutputInfos() {
      return hanaTransactionOutputInfos;
    }

    /**
     * Sets the HANA 2 demonstration transaction output informations.
     *
     * @param hanaTransactionOutputInfos the HANA 2 demonstration transaction output informations
     */
    public void setHanaTransactionOutputInfos(final List<HanaTransactionOutputInfo> hanaTransactionOutputInfos) {
      //Preconditions
      assert hanaTransactionOutputInfos != null : "hanaTransactionOutputInfos must not be null";
      assert !hanaTransactionOutputInfos.isEmpty() : "hanaTransactionOutputInfos must not be empty";

      this.hanaTransactionOutputInfos = hanaTransactionOutputInfos;
    }
  }

  /**
   * Validates the internal consistency of the given HANA 2 demonstration block item.
   *
   * @param hanaBlockItem the given HANA 2 demonstration block item
   * @return an object array - the first item is the indicator whether the block item is valid, and the second item is the invalid reason
   * number.
   */
  public static Object[] isHanaBlockItemValid(final HanaBlockItem hanaBlockItem) {
    final Object[] invalid = {Boolean.FALSE, 1};
    if (hanaBlockItem == null) {
      invalid[1] = 1;
      return invalid;
    }
    if (hanaBlockItem.getHanaBlockInfo() == null) {
      invalid[1] = 2;
      return invalid;
    }
    if (hanaBlockItem.getHanaBlockInfo().geBlockNumber() < 1) {
      invalid[1] = 3;
      return invalid;
    }
    if (hanaBlockItem.getHanaBlockInfo().getBlockVersion() != 4) {
      invalid[1] = 4;
      return invalid;
    }
    if (hanaBlockItem.getHanaBlockInfo().getBlockMerkleRoot() == null) {
      invalid[1] = 5;
      return invalid;
    }
    if (hanaBlockItem.getHanaBlockInfo().getBlockMerkleRoot().isEmpty()) {
      invalid[1] = 6;
      return invalid;
    }
    if (hanaBlockItem.getHanaBlockInfo().getBlockTime() == null) {
      invalid[1] = 7;
      return invalid;
    }
    if (hanaBlockItem.getHanaBlockInfo().getBlockTime().isEmpty()) {
      invalid[1] = 8;
      return invalid;
    }
    if (hanaBlockItem.getHanaBlockInfo().getBlockNoOftransactions() < 1) {
      invalid[1] = 9;
      return invalid;
    }
    if (hanaBlockItem.getHanaBlockInfo().getBlockNoOftransactions() != hanaBlockItem.getHanaTransactionItems().size()) {
      invalid[1] = 10;
      return invalid;
    }
    if (hanaBlockItem.getHanaTransactionItems() == null) {
      invalid[1] = 11;
      return invalid;
    }
    if (hanaBlockItem.getHanaTransactionItems().isEmpty()) {
      invalid[1] = 12;
      return invalid;
    }

    int transactionIndex = 0;
    for (final HanaTransactionItem hanaTransactionItem : hanaBlockItem.getHanaTransactionItems()) {
      if (hanaTransactionItem.getHanaTransactionInfo() == null) {
        invalid[1] = 13;
        return invalid;
      }
      if (hanaTransactionItem.getHanaTransactionInfo().getBlockNumber() != hanaBlockItem.getHanaBlockInfo().geBlockNumber()) {
        invalid[1] = 14;
        return invalid;
      }
      if (hanaTransactionItem.getHanaTransactionInfo().getTransactionIndex() != transactionIndex) {
        invalid[1] = 15;
        return invalid;
      }
      if (hanaTransactionItem.getHanaTransactionInfo().getTransactionId() == null) {
        invalid[1] = 16;
        return invalid;
      }
      if (hanaTransactionItem.getHanaTransactionInfo().getTransactionId().isEmpty()) {
        invalid[1] = 17;
        return invalid;
      }
      if (hanaTransactionItem.getHanaTransactionInfo().getTransactionNoOfInputs() < 1) {
        invalid[1] = 18;
        return invalid;
      }
      if (hanaTransactionItem.getHanaTransactionInfo().getTransactionNoOfInputs() != hanaTransactionItem.getHanaTransactionInputInfos().size()) {
        invalid[1] = 19;
        return invalid;
      }
      if (hanaTransactionItem.getHanaTransactionInfo().getTransactionNoOfOutputs() < 1) {
        invalid[1] = 20;
        return invalid;
      }
      if (hanaTransactionItem.getHanaTransactionInfo().getTransactionNoOfOutputs() != hanaTransactionItem.getHanaTransactionOutputInfos().size()) {
        invalid[1] = 21;
        return invalid;
      }

      int transactionInputIndex = 0;
      for (final HanaTransactionInputInfo hanaTransactionInputInfo : hanaTransactionItem.getHanaTransactionInputInfos()) {
        if (hanaTransactionInputInfo.getBlockNumber() != hanaTransactionItem.getHanaTransactionInfo().getBlockNumber()) {
          invalid[1] = 22;
          return invalid;
        }
        if (hanaTransactionInputInfo.getTransactionIndex() != hanaTransactionItem.getHanaTransactionInfo().getTransactionIndex()) {
          invalid[1] = 23;
          return invalid;
        }
        if (!hanaTransactionInputInfo.getTransactionId().equals(hanaTransactionItem.getHanaTransactionInfo().getTransactionId())) {
          invalid[1] = 24;
          return invalid;
        }
        if (hanaTransactionInputInfo.getParentTransactionId() == null) {
          invalid[1] = 25;
          return invalid;
        }
        if (hanaTransactionInputInfo.getParentTransactionId().isEmpty()) {
          invalid[1] = 26;
          return invalid;
        }
        if (hanaTransactionInputInfo.getTransactionInputIndex() != transactionInputIndex) {
          invalid[1] = 27;
          return invalid;
        }
        if (hanaTransactionInputInfo.getIndexIntoParentTransaction() < 0) {
          invalid[1] = 28;
          return invalid;
        }
        transactionInputIndex++;
      }

      int transactionOutputIndex = 0;
      for (final HanaTransactionOutputInfo hanaTransactionOutputInfo : hanaTransactionItem.getHanaTransactionOutputInfos()) {

        if (hanaTransactionOutputInfo.geBlockNumber() != hanaTransactionItem.getHanaTransactionInfo().getBlockNumber()) {
          invalid[1] = 29;
          return invalid;
        }
        if (hanaTransactionOutputInfo.getTransactionIndex() != hanaTransactionItem.getHanaTransactionInfo().getTransactionIndex()) {
          invalid[1] = 30;
          return invalid;
        }
        if (!hanaTransactionOutputInfo.getTransactionId().equals(hanaTransactionItem.getHanaTransactionInfo().getTransactionId())) {
          invalid[1] = 31;
          return invalid;
        }
        if (hanaTransactionOutputInfo.getTransactionOutputIndex() != transactionOutputIndex) {
          invalid[1] = 32;
          return invalid;
        }

        if (hanaTransactionOutputInfo.getAddress() == null) {
          invalid[1] = 33;
          return invalid;
        }
        if (hanaTransactionOutputInfo.getAddress().isEmpty()) {
          invalid[1] = 34;
          return invalid;
        }
        transactionOutputIndex++;
      }
      transactionIndex++;
    }
    final Object[] valid = {Boolean.TRUE, 0};
    return valid;
  }

  /**
   * Makes a HANA 2 demonstration block item for unit tests.
   *
   * @return a HANA 2 demonstration block item for unit tests
   */
  public static HanaBlockItem makeTestHanaBlockItem() {
    final HanaBlockItem hanaBlockItem = new HanaBlockItem();

    // populate the HanaBlockInfo
    final int blockNumber = 2;
    // the block version, 1 = AI Coin genesis, 4 = most current BitcoinCore.
    final short blockVersion = 4;
    // the transactions merkle root as string
    final String blockMerkleRoot = "b8669e62014f8e351f4691313f9c5934356d28a45c61f451b2a868b626c7247e";
    // the blockTime as string
    final String blockTime = "Sun Apr 23 21:16:56 GMT-06:00 2017";
    final HanaBlockInfo hanaBlockInfo = new HanaBlockInfo(
            blockNumber,
            blockVersion,
            blockMerkleRoot,
            blockTime,
            3); // blockNoOftransactions
    hanaBlockItem.setHanaBlockInfo(hanaBlockInfo);

    // the transactions
    List<HanaTransactionItem> hanaTransactionItems = new ArrayList<>();
    // the transaction index
    int transactionIndex = 0;
    // ------------------------------------------------------------------------
    // start transaction

    HanaTransactionInfo hanaTransactionInfo = new HanaTransactionInfo();
    hanaTransactionInfo.setBlockNumber(blockNumber);
    hanaTransactionInfo.setTransactionIndex(transactionIndex);
    String transactionId = "e0bb1a768f8f179b987cd95e9fefba92f5ef93c0e53e54f5f02d3d06d7084956";
    hanaTransactionInfo.setTransactionId(transactionId);
    hanaTransactionInfo.setTransactionNoOfInputs((short) 1);
    hanaTransactionInfo.setTransactionNoOfOutputs((short) 1);

    // the transaction input infos
    List<HanaTransactionInputInfo> hanaTransactionInputInfos = new ArrayList<>();
    int transactionInputIndex = 0;

    // start transaction input
    String parentTransactionId;
    int indexIntoParentTransaction;
    // is coinbase
    parentTransactionId = "0";
    indexIntoParentTransaction = 0;
    HanaTransactionInputInfo hanaTransactionInputInfo = new HanaTransactionInputInfo(
            blockNumber,
            transactionIndex,
            transactionId,
            parentTransactionId,
            transactionInputIndex,
            indexIntoParentTransaction);
    hanaTransactionInputInfos.add(hanaTransactionInputInfo);
    transactionInputIndex++;
    // end transaction input

    // the transaction output infos
    List<HanaTransactionOutputInfo> hanaTransactionOutputInfos = new ArrayList<>();
    int transactionOutputIndex = 0;

    // start transaction output
    String address = "AYmqNJ1zgKBRtXAPUDoAT8aU9aEsQ1kmdq";
    Double amount = 50.0000836;
    HanaTransactionOutputInfo hanaTransactionOutputInfo = new HanaTransactionOutputInfo(
            blockNumber,
            transactionIndex,
            transactionId,
            transactionOutputIndex,
            address,
            amount);
    hanaTransactionOutputInfos.add(hanaTransactionOutputInfo);
    transactionOutputIndex++;
    // end transaction output

    hanaTransactionItems.add(new HanaTransactionItem(
            hanaTransactionInfo,
            hanaTransactionInputInfos,
            hanaTransactionOutputInfos));
    transactionIndex++;
    // end transaction
    // ------------------------------------------------------------------------
    // start transaction

    hanaTransactionInfo = new HanaTransactionInfo();
    hanaTransactionInfo.setBlockNumber(blockNumber);
    hanaTransactionInfo.setTransactionIndex(transactionIndex);
    transactionId = "9bc9413a071dda2e7b5f5c732d352249e9711e4368c4731d03d68886c66dc62c";
    hanaTransactionInfo.setTransactionId(transactionId);
    hanaTransactionInfo.setTransactionNoOfInputs((short) 1);
    hanaTransactionInfo.setTransactionNoOfOutputs((short) 2);

    // the transaction input infos
    hanaTransactionInputInfos = new ArrayList<>();
    transactionInputIndex = 0;

    // start transaction input
    parentTransactionId = "01bc3ff4c81eb521913d1bc34692d2d5c30dd5e284ff675b6d3d7ec52c6a3188";
    indexIntoParentTransaction = 0;
    hanaTransactionInputInfo = new HanaTransactionInputInfo(
            blockNumber,
            transactionIndex,
            transactionId,
            parentTransactionId,
            transactionInputIndex,
            indexIntoParentTransaction);
    hanaTransactionInputInfos.add(hanaTransactionInputInfo);
    transactionInputIndex++;
    // end transaction input

    // the transaction output infos
    hanaTransactionOutputInfos = new ArrayList<>();
    transactionOutputIndex = 0;

    // start transaction output
    address = "AaaU1f3u2J9UPwNYmT9StiHyT5itPikQtV";
    amount = 39.7499616;
    hanaTransactionOutputInfo = new HanaTransactionOutputInfo(
            blockNumber,
            transactionIndex,
            transactionId,
            transactionOutputIndex,
            address,
            amount);
    hanaTransactionOutputInfos.add(hanaTransactionOutputInfo);
    transactionOutputIndex++;
    // end transaction output

    // start transaction output
    address = "AH4SAHSxWqCLXE878ApoDWfLabqcd7utxr";
    amount = 10.25;
    hanaTransactionOutputInfo = new HanaTransactionOutputInfo(
            blockNumber,
            transactionIndex,
            transactionId,
            transactionOutputIndex,
            address,
            amount);
    hanaTransactionOutputInfos.add(hanaTransactionOutputInfo);
    transactionOutputIndex++;
    // end transaction output

    hanaTransactionItems.add(new HanaTransactionItem(
            hanaTransactionInfo,
            hanaTransactionInputInfos,
            hanaTransactionOutputInfos));
    transactionIndex++;
    // end transaction
    // ------------------------------------------------------------------------
    // start transaction

    hanaTransactionInfo = new HanaTransactionInfo();
    hanaTransactionInfo.setBlockNumber(blockNumber);
    hanaTransactionInfo.setTransactionIndex(transactionIndex);
    transactionId = "a8670c29b6754ccc90a1b373778ebd79424b897601d514e6cf84bfc476697a1f";
    hanaTransactionInfo.setTransactionId(transactionId);
    hanaTransactionInfo.setTransactionNoOfInputs((short) 1);
    hanaTransactionInfo.setTransactionNoOfOutputs((short) 2);

    // the transaction input infos
    hanaTransactionInputInfos = new ArrayList<>();
    transactionInputIndex = 0;

    // start transaction input
    parentTransactionId = "9bc9413a071dda2e7b5f5c732d352249e9711e4368c4731d03d68886c66dc62c";
    indexIntoParentTransaction = 0;
    hanaTransactionInputInfo = new HanaTransactionInputInfo(
            blockNumber,
            transactionIndex,
            transactionId,
            parentTransactionId,
            transactionInputIndex,
            indexIntoParentTransaction);
    hanaTransactionInputInfos.add(hanaTransactionInputInfo);
    transactionInputIndex++;
    // end transaction input

    // the transaction output infos
    hanaTransactionOutputInfos = new ArrayList<>();
    transactionOutputIndex = 0;

    // start transaction output
    address = "AZy2WpaExSeyPjgC2JACU6toA2pmFXfrJb";
    amount = 28.7499164;
    hanaTransactionOutputInfo = new HanaTransactionOutputInfo(
            blockNumber,
            transactionIndex,
            transactionId,
            transactionOutputIndex,
            address,
            amount);
    hanaTransactionOutputInfos.add(hanaTransactionOutputInfo);
    transactionOutputIndex++;
    // end transaction output

    // start transaction output
    address = "AVfZDc4X2KYaGsh5PfMk9H13n5SfdSirT3";
    amount = 11.0;
    hanaTransactionOutputInfo = new HanaTransactionOutputInfo(
            blockNumber,
            transactionIndex,
            transactionId,
            transactionOutputIndex,
            address,
            amount);
    hanaTransactionOutputInfos.add(hanaTransactionOutputInfo);
    transactionOutputIndex++;
    // end transaction output

    hanaTransactionItems.add(new HanaTransactionItem(
            hanaTransactionInfo,
            hanaTransactionInputInfos,
            hanaTransactionOutputInfos));
    transactionIndex++;
    // end transaction

    hanaBlockItem.setHanaTransactionItems(hanaTransactionItems);

    //Postconditions
    final Object[] isHanaBlockItemValid = HanaItems.isHanaBlockItemValid(hanaBlockItem);
    assert (boolean) isHanaBlockItemValid[0] : "invalid hanaBlockItem, reason " + isHanaBlockItemValid[1];
    return hanaBlockItem;
  }

  /**
   * Executes a test of making a HANA 2 demonstration block item.
   *
   * @param args the command line args - not used
   */
  public static void main(final String[] args) {
    final HanaBlockItem hanaBlockItem = makeTestHanaBlockItem();
    System.out.println(StringUtils.log(LOGGER) + "the HANA 2 demonstration block item ...");
    System.out.println(StringUtils.log(LOGGER) + hanaBlockItem);
    System.out.println(StringUtils.log(LOGGER));
    System.out.println(StringUtils.log(LOGGER) + "the HANA 2 demonstration transaction items ...");
    for (final HanaTransactionItem hanaTransactionItem : hanaBlockItem.hanaTransactionItems) {
      System.out.println(StringUtils.log(LOGGER) + hanaTransactionItem);
      System.out.println(StringUtils.log(LOGGER));
      System.out.println(StringUtils.log(LOGGER) + "the HANA 2 demonstration transaction input infos ...");
      for (final HanaTransactionInputInfo hanaTransactionInputInfo : hanaTransactionItem.hanaTransactionInputInfos) {
        System.out.println(StringUtils.log(LOGGER) + hanaTransactionInputInfo);
        System.out.println(StringUtils.log(LOGGER));
      }
      System.out.println(StringUtils.log(LOGGER) + "the HANA 2 demonstration transaction output infos ...");
      for (final HanaTransactionOutputInfo hanaTransactionOutputInfo : hanaTransactionItem.hanaTransactionOutputInfos) {
        System.out.println(StringUtils.log(LOGGER) + hanaTransactionOutputInfo);
        System.out.println(StringUtils.log(LOGGER));
      }
    }
  }
}
