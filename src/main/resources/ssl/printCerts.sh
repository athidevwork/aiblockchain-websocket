#!/bin/bash

echo
echo Server
keytool -list -v -keystore aibc_server_keystore.jks -storepass changeit
echo
echo Client
echo
keytool -list -v -keystore aibc_client_keystore.jks -storepass changeit
echo
