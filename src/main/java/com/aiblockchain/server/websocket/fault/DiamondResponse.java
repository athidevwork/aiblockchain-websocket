/**
 * 
 */
package com.aiblockchain.server.websocket.fault;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Athi
 *
 */
public class DiamondResponse implements Serializable {
	   private String result;
	   private Map<String, Serializable> diamondData;

	   public String getResult() {
	      return result;
	   }

	   public Map<String, Serializable> getDiamondData() {
	      return diamondData;
	   }

	   public void setResult(String command) {
	      this.result = command;
	   }

	   public void setDiamondData(Map<String, Serializable> diamondData) {
	      this.diamondData = diamondData;
	   }

	   @Override
	   public boolean equals(Object o) {
	      if (this == o) { return true; }
	      if (o == null || getClass() != o.getClass()) { return false; }

	      DiamondResponse that = (DiamondResponse) o;

	      if (result != null ? !result.equals(that.result) : that.result != null) { return false; }
	      if (diamondData != null ? !diamondData.equals(that.diamondData) : that.diamondData != null) { return false; }

	      return true;
	   }

	   @Override
	   public int hashCode() {
	      int result = this.result != null ? this.result.hashCode() : 0;
	      result = 31 * result + (diamondData != null ? diamondData.hashCode() : 0);
	      return result;
	   }

	   @Override
	   public String toString() {
	      return "DiamondResponse{"  +
	              "result='"      + result + '\'' +
	            ", diamondData='" + diamondData + '\'' +
	            '}';
	   }
}
