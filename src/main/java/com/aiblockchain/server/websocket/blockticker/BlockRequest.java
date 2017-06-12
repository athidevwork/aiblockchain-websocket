package com.aiblockchain.server.websocket.blockticker;

/**
 * User : Athi.
 */
public class BlockRequest {
   private int blockNumber;
   private int numberOfBlocks;
   private String command;

   public String getCommand() {
      return command;
   }

   public void setCommand(String command) {
	      this.command = command;
	   }
   
   public int getBlockNumber() {
      return blockNumber;
   }

   public void setBlockNumber(int blockNumber) {
      this.blockNumber = blockNumber;
   }
   
   public int getNumberOfBlocks() {
	return numberOfBlocks;
}

public void setNumberOfBlocks(int numberOfBlocks) {
	this.numberOfBlocks = numberOfBlocks;
}


@Override
   public boolean equals(Object o) {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }

      BlockRequest that = (BlockRequest) o;

      if (command != null ? !command.equals(that.command) : that.command != null) { return false; }
      if (numberOfBlocks != 0 ? !(numberOfBlocks==that.numberOfBlocks) : that.numberOfBlocks != 0) { return false; }
      if (blockNumber != 0 ? !(blockNumber==that.blockNumber) : that.blockNumber != 0) { return false; }
      
      return true;
   }

   @Override
   public int hashCode() {
      int result = command != null ? command.hashCode() : 0;
      result = 31 * result + (numberOfBlocks != 0 ? numberOfBlocks : 0);
      result = 31 * result + (blockNumber != 0 ? blockNumber : 0);
      return result;
   }

   @Override
   public String toString() {
      return "TickerRequest{"  +
              "command='"      + command + '\'' +
             ", blockNumber='" + blockNumber + '\'' +              
            ", numberOfBlocks='" + numberOfBlocks + '\'' +
            '}';
   }

}
