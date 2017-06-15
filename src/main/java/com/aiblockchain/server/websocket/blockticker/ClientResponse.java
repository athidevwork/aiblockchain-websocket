/**
 * 
 */
package com.aiblockchain.server.websocket.blockticker;

/**
 * @author Athi
 *
 */
public interface ClientResponse {
	void setStatus(String status);
	void setResultType(String resultType);	
	void setResult(Object response);
}
