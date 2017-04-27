#!/bin/bash

#java -Dlog4j.debug -Dlog4j.configuration=log4j.properties -jar target/original-aiblockchain-websocket.jar 8083 $@
java -Dlog4j.debug -Dlog4j.configuration=./src/main/resources/log4j.properties -jar target/original-aiblockchain-websocket.jar 20000 $@
