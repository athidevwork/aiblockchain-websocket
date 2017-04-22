package com.aiblockchain.server.websocket.service;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.aiblockchain.server.websocket.entity.Client;


public class RequestService {

	/**
	 * clientRegister
	 * @param request {id:1;rid:21;token:'43606811c7305ccc6abb2be116579bfd'}
	 * @return
	 */
    public static Client clientRegister(String request) {
    	System.out.println("Received request : " + request);
        String res = new String(Base64.decodeBase64(request));
        System.out.println("Remote : " + res);
        JSONObject json = new JSONObject(res);

        Client client = new Client();

        if (!json.has("rid")) {
            return client;
        }

        try {
            client.setRoomId(json.getInt("rid"));
        } catch (JSONException e) {
            e.printStackTrace();
            return client;
        }

        if (!json.has("id") || !json.has("token")) {
            return client;
        }

        Long id;
        String token;

        try {
            id = json.getLong("id");
            token = json.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
            return client;
        }

        if (!checkToken(id, token)) {
            return client;
        }

        client.setId(id);

        return client;
    }

    /**
     * checkToken
     * @param id
     * @param token
     * @return
     */
    private static boolean checkToken(Long id, String token) {
        return true;
    }
}
