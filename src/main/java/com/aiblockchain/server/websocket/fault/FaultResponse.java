/**
 * 
 */
package com.aiblockchain.server.websocket.fault;

import java.io.Serializable;
import java.util.List;

/**
 * @author Athi
 *
 */
public class FaultResponse implements Serializable
{
	private static final long serialVersionUID = 2878120152313658729L;
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
        return "FaultResponse [responseData = "+responseData+", resultType = "+resultType+"]";
    }
}
