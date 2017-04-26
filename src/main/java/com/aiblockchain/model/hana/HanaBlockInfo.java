package com.aiblockchain.model.hana;

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
  // the number of transactions is a block
  private int blockNoOftransactions;
  
  /**
   * Creates a new empty instance of HanaBlockInfo.
   */
  public HanaBlockInfo() {
  }

  /**
   * Creates a new instance of HanaBlockInfo.
   *
   * @param blockNumber the block number
   * @param blockVersion the block version
   * @param blockMerkleRoot the transactions merkle root as string
   * @param blockTime the blockT time as string
   * @param blockNoOftransactions the number of transactions in a block
   */
  public HanaBlockInfo(
          final int blockNumber,
          final short blockVersion,
          final String blockMerkleRoot,
          final String blockTime,
	  final int blockNoOftransactions) {
    // Preconditions
    assert blockNumber > 0 : "Block number has to be greater than 0";
    assert blockVersion > -1 : "version must not be negative";
    assert blockMerkleRoot != null : "blockMerkleRoot must not be null";
    assert !blockMerkleRoot.isEmpty() : "blockMerkleRoot must not be empty";
    assert blockTime != null : "blockTime must not be null";
    assert !blockTime.isEmpty() : "blockTime must not be empty";
    assert blockNoOftransactions > 0 : "number of transactions in a block have to more than 1";

    this.blockNumber = blockNumber;
    this.blockVersion = blockVersion;
    this.blockMerkleRoot = blockMerkleRoot;
    this.blockTime = blockTime;
    this.blockNoOftransactions = blockNoOftransactions;
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

  /** gets the block version.
   *
   * @return the block version
   */
  public short getBlockVersion() {
    return blockVersion;
  }

  /** Sets the block version.
   *
   * @param blockVersion the block version
   */
  public void setBlockVersion(final short blockVersion) {
    // Preconditions
    assert blockVersion > -1 : "version must not be negative";
    
    this.blockVersion = blockVersion;
  }
  /** Gets the transactions merkle root as string.
   *
   * @return the transactions merkle root as string
   */
  public String getBlockMerkleRoot() {
    return blockMerkleRoot;
  }

  /** Sets the transactions merkle root as string.
   *
   */
  public void setBlockMerkleRoot(final String blockMerkleRoot) {
    // Preconditions
    assert blockMerkleRoot != null : "blockMerkleRoot must not be null";
    assert !blockMerkleRoot.isEmpty() : "blockMerkleRoot must not be empty";
    
    this.blockMerkleRoot = blockMerkleRoot;
  }
  
  /** Gets the block time as string.
   *
   * @return the block time as string
   */
  public String getBlockTime() {
    return blockTime;
  }

  /** Sets the block time as string.
   *
   * @param blockTime the block time as string
   */
  public void setBlockTime(final String blockTime) {
    // Preconditions
    assert blockTime != null : "blockTime must not be null";
    assert !blockTime.isEmpty() : "blockTime must not be empty";
    
    this.blockTime = blockTime;
  }
  
  /** Gets the number of transactions in a block.
   *
   * @return the number of transactions in a block
   */
  public int getBlockNoOftransactions() {
    return blockNoOftransactions;
  }  

  /** Sets the number of transactions in a block.
   *
   */
  public void setBlockNoOftransactions(final int blockNoOftransactions) {
    // Preconditions
    assert blockNoOftransactions > 0 : "number of transactions in a block must be positive";

    this.blockNoOftransactions = blockNoOftransactions;
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
              .append("\n  blockNoOftransactions: ")
              .append(blockNoOftransactions)
              .append("]");
    return stringBuilder.toString();
  }
}
