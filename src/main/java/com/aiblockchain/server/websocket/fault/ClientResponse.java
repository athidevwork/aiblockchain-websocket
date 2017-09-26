/**
 * 
 */
package com.aiblockchain.server.websocket.fault;

/**
 * @author Athi
 *
 */
public class ClientResponse {
	private String resultType;
	private ResponseData responseData;

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	@Override
	public String toString() {
		return "ClientResponse [resultType=" + resultType + ", responseData=" + responseData + "]";
	}
}
