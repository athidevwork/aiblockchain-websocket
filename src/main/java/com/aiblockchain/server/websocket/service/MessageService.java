package com.aiblockchain.server.websocket.service;

import com.aiblockchain.server.websocket.dto.Response;
import com.aiblockchain.server.websocket.entity.Client;

public class MessageService {

    public static Response sendMessage(Client client, String message) {
        Response res = new Response();
        res.getData().put("id", client.getId());
        res.getData().put("message", message);
        res.getData().put("ts", System.currentTimeMillis());
        return res;
    }
}
