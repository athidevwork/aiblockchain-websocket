package com.aiblockchain.client;

import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import org.apache.log4j.Logger;

/**
 * WebsocketClientEndpoint
 *
 * @author Athi
 */
@ClientEndpoint
public class WebsocketClientEndpoint {

    // the logger
    private static final Logger LOGGER = Logger.getLogger(WebsocketClientEndpoint.class);
  
    Session userSession = null;
    private MessageHandler messageHandler;

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            LOGGER.error("check websocket endpoint - probably invalid URI");
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        LOGGER.info("opening websocket session " + userSession.getId());
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        LOGGER.info("closing websocket session " + userSession.getId());
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client receives a message from the websocket server.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        LOGGER.info("onMessage " + message);
         if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        LOGGER.info("addMessageHandler " + msgHandler);
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        LOGGER.info("sendMessage " + message);
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     *
     * @author Jiji_Sasidharan
     */
    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}