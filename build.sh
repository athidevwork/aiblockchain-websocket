#!/bin/bash
set -x
docker rm aiblockchain-websocket
docker rmi aiblockchain-websocket

mvn clean package docker:build

docker run -d -h localhost -p 8083:8083 --name aiblockchain-websocket aiblockchain-websocket
docker logs -f --tail=all aiblockchain-websocket
set +x
