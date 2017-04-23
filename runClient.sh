#!/bin/bash

#java -jar target/aiblockchain-websocket-tests.jar com.aiblockchain.client.HanaClient $@

java -cp target/aiblockchain-websocket-tests.jar:aiblockchain-websocket.jar com.aiblockchain.client.HanaClient.class
