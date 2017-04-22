package com.aiblockchain.model.hana;

import java.util.List;

/**
 * HanaBlockInfo.java
 *
 * Description: Contains AI Coin block information for the SAP HANA 2 demonstration.
 *
 * Copyright (C) Apr 20, 2017, Stephen L. Reed.
 */
public class HanaBlockInfo 
{
  // the block number
  private int blockNumber;
  // the block version
  private short blockVersion;
  // the transactions merkle root as string
  private String blockMerkleRoot;
  // the blockTime as string
  private String blockTime;

  /**
   * Creates a new instance of HanaBlockInfo.
   *
   * @param blockNumber the block number
   * @param blockVersion the block version
   * @param blockMerkleRoot the transactions merkle root as string
   * @param blockTime the blockT time as string
   */
  public HanaBlockInfo(
          int blockNumber,
          short blockVersion,
          String blockMerkleRoot,
          String blockTime) {
    // Preconditions
    assert blockVersion > -1 : "version must not be negative";
    //assert StringUtils.isNonEmptyString(blockMerkleRoot) : "blockMerkleRoot must be a non-empty string";
    //assert StringUtils.isNonEmptyString(blockTime) : "blockTime must be a non-empty string";

    this.blockNumber = blockNumber;
    this.blockVersion = blockVersion;
    this.blockMerkleRoot = blockMerkleRoot;
    this.blockTime = blockTime;
  }

  public HanaBlockInfo() {}

  /** gets the block number.
   *
   * @return the block number
   */
  public int geBlockNumber() {
    return blockNumber;
  }

  /** gets the block version.
   *
   * @return the block version
   */
  public short getBlockVersion() {
    return blockVersion;
  }

  /** Gets the transactions merkle root as string.
   *
   * @return the transactions merkle root as string
   */
  public String getBlockMerkleRoot() {
    return blockMerkleRoot;
  }

  /** Gets the block time as string.
   *
   * @return the block time as string
   */
  public String getBlockTime() {
    return blockTime;
  }

   /** Sets the block number.
   *
   * @return None
   */
  public void setBlockNumber(int blockNumber) {
    this.blockNumber = blockNumber;
  }

  /** Sets the block version.
   *
   * @return None
   */
  public void setBlockVersion(short blockVersion) {
    this.blockVersion = blockVersion;
  }

  /** Sets the transactions merkle root as string.
   *
   * @return None
   */
  public void setBlockMerkleRoot(String blockMerkleRoot) {
    this.blockMerkleRoot = blockMerkleRoot;
  }

  /** Sets the block time as string.
   *
   * @return None
   */
  public void setBlockTime(String blockTime) {
    this.blockTime = blockTime;
  }

  /** Returns a string representation of this object.
   *
   * @return a string representation of this object
   */
  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
      stringBuilder
              .append("[HanaBlockInfo\n")
              .append("  blockNumber: ")
              .append(blockNumber)
              .append("\n  blockVersion: ")
              .append(blockVersion)
              .append("\n  blockMerkleRoot: ")
              .append(blockMerkleRoot)
              .append("\n  blockTime: ")
              .append(blockTime)
              .append("]");
    return stringBuilder.toString();
  }
}
