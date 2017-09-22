/**
 * Provides a commercial real estate fault reporting request API object. The fault documents are digested by the client and the hashes of
 * each form the signatures. This request says to store the given list of hex encoded signatures as OP_RETURN data on the blockchain.
 *
 * Presently the "add" command is supported.
 */
package com.aiblockchain.server.websocket.fault;

import com.aiblockchain.server.StringUtils;
import java.io.Serializable;
import java.util.List;

/**
 * @author Athi
 *
 */
public class DiamondRequest implements Serializable {

  // the command, "add"
  private final String command;

  // the list of diamond signatures, in which each signature is a hex encoded byte array of at most 80 bytes
  // typically the fault signature is a SHA-256 hash with 32 byte length
  private final List<String> diamondSignatures;

  /**
   * Constructs a new DiamondRequest instance.
   *
   * @param command the command, "saveDiamond"
   * @param faultSignatures the list of fault signatures
   */
  public DiamondRequest(
          final String command,
          final List<String> diamondSignatures) {
    //Preconditions
    assert StringUtils.isNonEmptyString(command) : "command must be a non empty string";
    assert diamondSignatures != null : "diamondSignatures must not be null";

    this.command = command;
    this.diamondSignatures = diamondSignatures;
  }

  /**
   * Gets the command.
   *
   * @return the command
   */
  public String getCommand() {
    return command;
  }

  /**
   * Gets the list of diamond signatures.
   *
   * @return the list of diamond signatures
   */
  public List<String> getDiamondSignatures() {
    return diamondSignatures;
  }
  
  /** Returns a string representation of this object.
   * 
   * @return a string representation of this object 
   */
  @Override
  public String toString() {
    return new StringBuilder()
            .append("[DiamondRequest ")
            .append(command)
            .append(", ")
            .append(diamondSignatures)
            .append("]")
            .toString();
  }
}
