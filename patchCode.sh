#!/bin/bash

echo "Patching aiblockchain-websocket..."
ls -lt target/ai*.jar

echo "Patching turing..."
cp target/aiblockchain-websocket.jar ~/docker/turing/Main-1.0/lib/aiblockchain-websocket-0.0.1-SNAPSHOT.jar
echo "Patching alice..."
cp target/aiblockchain-websocket.jar ~/docker/alice/Main-1.0/lib/aiblockchain-websocket-0.0.1-SNAPSHOT.jar
echo "Patching bob..."
cp target/aiblockchain-websocket.jar ~/docker/bob/Main-1.0/lib/aiblockchain-websocket-0.0.1-SNAPSHOT.jar

echo "Restarting server..."
~/docker/build-demo-docker-images.sh
echo done
