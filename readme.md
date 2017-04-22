This server can be run as a **stand alone java server using runServer.sh** or as a **docker container which can be built using build.sh** 

#BUILD and PACKAGE
To build the server, you would need java jdk and maven installed in the machine. 

Command to build the package

>mvn clean package

build.sh script assumes docker is installed in the environment. If not docker commands will fail.

To build the package and create the docker build, Go to the project folder and run 

>mvn clean package docker:build. 

This would create two versions of the jar in the target folder, 

**original-aiblockchain-websocket.jar** which does not have any dependant libraries used by runServer.sh 

and 

**aiblockchain-websocket.jar** which was built specifically to be run a docker container with all dependant libraries included in it.


To run Websocket server on port 8083:

>java -Dlog4j.debug -Dlog4j.configuration=log4j.properties -jar target/original-aiblockchain-websocket.jar 8083 $@

===================================================================================
#TEST CLIENTS

Client would call with this uri  to connect to the websocket on the host

```
ws://localhost:8083/wsticker
```

This is another sample for the client (need to figure out how to use both the handlers in the code)

ws://localhost:8083/websocket/?request=e2lkOjE7cmlkOjI2O3Rva2VuOiI0MzYwNjgxMWM3MzA1Y2NjNmFiYjJiZTExNjU3OWJmZCJ9
ws://localhost:8083/websocket?request=e2lkOjE7cmlkOjI2O3Rva2VuOiI0MzYwNjgxMWM3MzA1Y2NjNmFiYjJiZTExNjU3OWJmZCJ9

===================================================================================
