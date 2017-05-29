#!/bin/bash

#cleanup old data
rm -f *.jks *.pem *.cer *.csr *.srl

echo "server setup start"

#create the key
keytool -genkey -alias aibc_websocket_server -keyalg RSA -keystore aibc_server_keystore.jks -keypass changeit -storepass changeit -dname "cn=Drew Hingorani, ou=Java Server, o=AI Blockchain, c=US" -validity 365 -keysize 3072

#server certificate
keytool -export -alias aibc_websocket_server -file  aibc_websocket_server.cer -keystore aibc_server_keystore.jks -storepass changeit

keytool -list -v -keystore aibc_server_keystore.jks -storepass changeit

echo "server setup complete"
echo "=================================================="
echo "client setup start"

#create a client keystore
keytool -genkey -alias aibc_websocket_client -keyalg RSA -keystore aibc_client_keystore.jks -keypass changeit -storepass changeit -dname "cn=Drew Hingorani, ou=Java Client, o=AI Blockchain, c=US" -keysize 3072

#create a certificate signing request
keytool -certreq -keyalg RSA -alias aibc_websocket_client -file aibc_websocket_client.csr -keystore aibc_client_keystore.jks -storepass changeit 

#Create a self signed certificate (notice the addition of -x509 option):
openssl req -config aiblockchain-certs.conf -new -x509 -sha256 -newkey rsa:2048 -nodes \
            -keyout aiblockchain.key.pem -days 365 -out aiblockchain.cert.pem

#Create a signing request (notice the lack of -x509 option):
#openssl req -config aiblockchain-certs.conf -new -sha256 -newkey rsa:2048 -nodes \
            #-keyout aiblockchain.key.pem -days 365 -out aiblockchain.req.pem

#generate the self signed certificate for a certificate request
openssl x509 -req -CA aiblockchain.cert.pem -CAkey aiblockchain.key.pem -in aibc_websocket_client.csr -out aibc_websocket_client.cer -days 365 -CAcreateserial

#Print a self signed certificate:
openssl x509 -in aiblockchain.cert.pem -text -noout

#install the certificate
keytool -import -alias theCARoot -keystore aibc_client_keystore.jks -file aiblockchain.cert.pem -trustcacerts
keytool -import -alias aibc_websocket_client -keystore aibc_client_keystore.jks –file aibc_websocket_client.cer -trustcacerts
#keytool -import -alias aibc_websocket_server -keystore aibc_client_keystore.jks -file aibc_websocket_server.cer 

keytool -list -v -keystore aibc_client_keystore.jks -storepass changeit

echo "client setup complete"
echo "=================================================="

#keytool -import -alias aibc_websocket_client -keystore aibc_server_keystore.jks -file aibc_websocket_client.cer 
