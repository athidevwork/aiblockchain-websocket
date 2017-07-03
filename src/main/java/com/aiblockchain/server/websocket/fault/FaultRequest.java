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
public class FaultRequest implements Serializable {

  // the command, "add"
  private final String command;

  // the list of fault signatures, in which each signature is a hex encoded byte array of at most 80 bytes
  // typically the fault signature is a SHA-256 hash with 32 byte length
  private final List<String> faultSignatures;

  /**
   * Constructs a new FaultRequest instance.
   *
   * @param command the command, "add"
   * @param faultSignatures the list of fault signatures
   */
  public FaultRequest(
          final String command,
          final List<String> faultSignatures) {
    //Preconditions
    assert StringUtils.isNonEmptyString(command) : "command must be a non empty string";
    assert faultSignatures != null : "faultSignatures must not be null";

    this.command = command;
    this.faultSignatures = faultSignatures;
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
   * Gets the list of fault signatures.
   *
   * @return the list of fault signatures
   */
  public List<String> getFaultSignatures() {
    return faultSignatures;
  }
  
  /** Returns a string representation of this object.
   * 
   * @return a string representation of this object 
   */
  @Override
  public String toString() {
    return new StringBuilder()
            .append("[FaultRequest ")
            .append(command)
            .append(", ")
            .append(faultSignatures)
            .append("]")
            .toString();
  }
}
