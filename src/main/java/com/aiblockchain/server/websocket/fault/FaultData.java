/**
 * 
 */
package com.aiblockchain.server.websocket.fault;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Athi
 *
 */
public class FaultData implements Serializable {
	   //private String result;
	private List<String> faultIds;
	private List<String> txnIds;

/*	   public String getResult() {
	      return result;
	   }

	   public Map<String, Serializable> getFaultData() {
	      return faultData;
	   }

	   public void setResult(String command) {
	      this.result = command;
	   }

	   public void setFaultData(Map<String, Serializable> faultData) {
	      this.faultData = faultData;
	   }
	   */
	

   @Override
   public boolean equals(Object o) {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }

      FaultData that = (FaultData) o;

      //if (result != null ? !result.equals(that.result) : that.result != null) { return false; }
      if (faultIds != null ? !faultIds.equals(that.faultIds) : that.faultIds != null) { return false; }
      if (txnIds != null ? !txnIds.equals(that.txnIds) : that.txnIds != null) { return false; }

      return true;
   }

	public List<String> getFaultIds() {
		return faultIds;
	}

	public void setFaultIds(List<String> faultIds) {
		this.faultIds = faultIds;
	}

	public List<String> getTxnIds() {
		return txnIds;
	}

	public void setTxnIds(List<String> txnIds) {
		this.txnIds = txnIds;
	}

	@Override
	   public int hashCode() {
	      int result = this.faultIds != null ? this.faultIds.hashCode() : 0;
	      result = 31 * result + (txnIds != null ? txnIds.hashCode() : 0);
	      return result;
	   }

	@Override
	public String toString() {
		return "FaultResponse [faultIds=" + faultIds + ", txnIds=" + txnIds + "]";
	}

}
