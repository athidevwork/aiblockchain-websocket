/**
 * 
 */
package com.aiblockchain.server.websocket.blockticker;

import java.util.Map;

/**
 * @author Athi
 *
 */
public class FaultResponse {
	   private String result;
	   private Map<String,String> faultData;

	   public String getResult() {
	      return result;
	   }

	   public Map<String,String> getTickerData() {
	      return faultData;
	   }

	   public void setResult(String command) {
	      this.result = command;
	   }

	   public void setTickerData(Map<String,String> tickerData) {
	      this.faultData = tickerData;
	   }

	   @Override
	   public boolean equals(Object o) {
	      if (this == o) { return true; }
	      if (o == null || getClass() != o.getClass()) { return false; }

	      FaultResponse that = (FaultResponse) o;

	      if (result != null ? !result.equals(that.result) : that.result != null) { return false; }
	      if (faultData != null ? !faultData.equals(that.faultData) : that.faultData != null) { return false; }

	      return true;
	   }

	   @Override
	   public int hashCode() {
	      int result = this.result != null ? this.result.hashCode() : 0;
	      result = 31 * result + (faultData != null ? faultData.hashCode() : 0);
	      return result;
	   }

	   @Override
	   public String toString() {
	      return "TickerResponse{"  +
	              "result='"      + result + '\'' +
	            ", tickerData='" + faultData + '\'' +
	            '}';
	   }
}
