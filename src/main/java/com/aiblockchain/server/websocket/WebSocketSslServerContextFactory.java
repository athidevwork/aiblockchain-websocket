/**
 * 
 */
package com.aiblockchain.server.websocket;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author Athi
 *
 */
public class WebSocketSslServerContextFactory {
    private static final String PROTOCOL = "TLS";
    private static final SSLContext CONTEXT;
    private static final String PASSWORD = "changeit";
    private static final char[] KEY_PASSWORD = PASSWORD.toCharArray();
    private static final char[] JKS_PASSWORD = PASSWORD.toCharArray();

    static {
        SSLContext serverContext;
        try {
            /* Get the JKS contents */
    		final KeyStore keyStore = KeyStore.getInstance("JKS");
    		try (final InputStream is = new FileInputStream(fullPathOfKeyStore())) {
    			keyStore.load(is, JKS_PASSWORD);
    		}
    		final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
    				.getDefaultAlgorithm());
    		kmf.init(keyStore, KEY_PASSWORD);
    		final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory
    				.getDefaultAlgorithm());
    		tmf.init(keyStore);
    		
            serverContext = SSLContext.getInstance(PROTOCOL);
            serverContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new java.security.SecureRandom());
        } catch (Exception e) {
            throw new Error("Failed to initialize the server-side SSLContext", e);
        }
        CONTEXT = serverContext;
    }

    public static SSLContext getContext() {
        return CONTEXT;
    }

    private static File fullPathOfKeyStore() {
    	return new File("src/main/resources/ssl/aibc_server_keystore.jks");
	}

	private WebSocketSslServerContextFactory() {
        // Unused
    }
}
