/**
 * 
 */
package com.aiblockchain.server.websocket.blockticker;

/**
 * @author Athi
 *
 */
public class ClientResponseImpl implements ClientResponse {
	private String resultMsg;
	private Object result;

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object response) {
		this.result = response;
	}
	
}
