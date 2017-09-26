/**
 * 
 */
package com.aiblockchain.server.websocket.fault;

/**
 * @author Athi
 *
 */
public class ResponseData
{
    private String status;
    private Object result;

    public Object getResult ()
    {
        return result;
    }

    public void setResult (Object result)
    {
        this.result = result;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "[result = "+result+", status = "+status+"]";
    }
}
