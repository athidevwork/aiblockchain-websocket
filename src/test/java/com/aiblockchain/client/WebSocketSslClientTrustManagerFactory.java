/**
 * 
 */
package com.aiblockchain.client;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import io.netty.util.internal.SystemPropertyUtil;

/**
 * @author Athi
 *
 */
public class WebSocketSslClientTrustManagerFactory {
	private static Logger logger = Logger.getLogger(WebSocketSslClientTrustManagerFactory.class);

	private static final String PROTOCOL = "TLS";
	private static final SSLContext SERVER_CONTEXT;
	private static final SSLContext CLIENT_CONTEXT;

	/*static {

	    SSLContext serverContext = null;
	    SSLContext clientContext = null;

	        // get keystore and trustore locations and passwords
	    String keyStoreLocation = System.getProperty("javax.net.ssl.keyStore");
	    String keyStorePassword = System.getProperty("javax.net.ssl.keyStorePassword");
	    String trustStoreLocation = System.getProperty("javax.net.ssl.trustStore");
	    String trustStorePassword = System.getProperty("javax.net.ssl.trustStorePassword");
	    try {

	        KeyStore ks = KeyStore.getInstance("JKS");
	        ks.load(KeyStoreStreamManager.asInputStream(keyStoreLocation),
	                keyStorePassword.toCharArray());

	        // Set up key manager factory to use our key store
	        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	        kmf.init(ks, keyStorePassword.toCharArray());

	          // truststore
	        KeyStore ts = KeyStore.getInstance("JKS");
	        ts.load(KeyStoreStreamManager.asInputStream(trustStoreLocation),
	                trustStorePassword.toCharArray());

	        // set up trust manager factory to use our trust store
	        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	        tmf.init(ts);

	        // Initialize the SSLContext to work with our key managers.
	        serverContext = SSLContext.getInstance(PROTOCOL);
	        serverContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

	    } catch (Exception e) {
	        throw new Error(
	                "Failed to initialize the server-side SSLContext", e);
	    }*/

	    static {
	        String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
	        if (algorithm == null) {
	            algorithm = "SunX509";
	        }

	        SSLContext serverContext;
	        SSLContext clientContext;
	        try {
	            KeyStore ks = KeyStore.getInstance("JKS");
	            /*ks.load(SecureChatKeyStore.asInputStream(),
	                    SecureChatKeyStore.getKeyStorePassword());*/

	            // Set up key manager factory to use our key store
	            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
	            //kmf.init(ks, SecureChatKeyStore.getCertificatePassword());

	            // Initialize the SSLContext to work with our key managers.
	            serverContext = SSLContext.getInstance(PROTOCOL);
	            serverContext.init(kmf.getKeyManagers(), null, null);
	        } catch (Exception e) {
	            throw new Error(
	                    "Failed to initialize the server-side SSLContext", e);
	        }

	        try {
	            clientContext = SSLContext.getInstance(PROTOCOL);
	            //clientContext.init(null, SecureChatTrustManagerFactory.getTrustManagers(), null);
	        } catch (Exception e) {
	            throw new Error(
	                    "Failed to initialize the client-side SSLContext", e);
	        }

	        SERVER_CONTEXT = serverContext;
	        CLIENT_CONTEXT = clientContext;
	    }
	    
	    /*try {
	        clientContext = SSLContext.getInstance(PROTOCOL);
	        clientContext.init(null, WebSocketSslClientTrustManagerFactory.getTrustManagers(), null);
	    } catch (Exception e) {
	        throw new Error(
	                "Failed to initialize the client-side SSLContext", e);
	    }

	    SERVER_CONTEXT = serverContext;
	    CLIENT_CONTEXT = clientContext;
	}*/

	public static SSLContext getServerContext() {
	    return SERVER_CONTEXT;
	}

	public static SSLContext getClientContext() {
	    return CLIENT_CONTEXT;
	}

	private WebSocketSslClientTrustManagerFactory() {
	    // Unused
	}
}
