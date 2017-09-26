/**
 * 
 */
package com.aiblockchain.server.websocket.fault;

import java.io.Serializable;

/**
 * @author Athi
 *
 */
public class DiamondResponse implements Serializable {
	private static final long serialVersionUID = 8570583239914223244L;
	private String resultType;
    private ResponseData responseData;

    public ResponseData getResponseData ()
    {
        return responseData;
    }

    public void setResponseData (ResponseData responseData)
    {
        this.responseData = responseData;
    }

    public String getResultType ()
    {
        return resultType;
    }

    public void setResultType (String resultType)
    {
        this.resultType = resultType;
    }

    @Override
    public String toString()
    {
        return "DiamondResponse [responseData = "+responseData+", resultType = "+resultType+"]";
    }
}
