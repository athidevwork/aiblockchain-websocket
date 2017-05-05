#!/bin/bash

#java -verbose -jar target/aiblockchain-websocket-tests.jar:aiblockchain-websocket.jar com.aiblockchain.client.HanaClient $@
#java -verbose -jar target/*.jar com.aiblockchain.client.HanaClient 8083 $@

#java -cp target/aiblockchain-websocket-tests.jar:target/aiblockchain-websocket.jar com.aiblockchain.client.HanaClient
#java -verbose -cp target/*.jar com.aiblockchain.client.HanaClient

#java -verbose -cp target/*.jar com.aiblockchain.load.client.LoadTransactionsClient $@

#cd target/test-classes
#java -cp . load.client.LoadTransactionsClient $@

cd target/test-classes
java -cp ../aiblockchain-websocket.jar:. com.aiblockchain.client.HanaClient aicoin.dyndns.org $@

