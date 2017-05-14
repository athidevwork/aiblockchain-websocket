/**
 * 
 */
package com.aiblockchain.client;

import javax.net.ssl.SSLContext;

/**
 * @author Athi
 *
 */
public class WebSocketSslClientContextFactory {
    private static final String PROTOCOL = "TLS";
    private static final SSLContext CONTEXT;

    static {
        SSLContext clientContext;
        try {
            clientContext = SSLContext.getInstance(PROTOCOL);
            //clientContext.init(null, WebSocketSslClientTrustManagerFactory.getTrustManagers(), null);
        } catch (Exception e) {
            throw new Error(
                    "Failed to initialize the client-side SSLContext", e);
        }

        CONTEXT = clientContext;
    }

    public static SSLContext getContext() {
        return CONTEXT;
    }

    private WebSocketSslClientContextFactory() {
        // Unused
    }
}
