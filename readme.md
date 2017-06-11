This server can be run as a **stand alone java server using runServer.sh** or as a **docker container which can be built using build.sh** 

## BUILD and PACKAGE

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


## Latest updates 

This project had two versions of jar that gets created for namely, 
#### inon secured websockets****(aiblockchain-websocket-non-secured-websocket.jar)*** 
and 
#### secured websockets****(aiblockchain-websocket-secured-websocket.jar)***

There is a runServer.sh script that would run secured or non secured version like given below,

>./runServer.sh (***Non secured websockets by default***)
>./runServer.sh tls (***secured websockets***)

=================================================================================
## TEST CLIENTS

### Non secured web socket client url:

```
ws://localhost:20000/wsticker
```

### Secured web socket client url:

```
wss://localhost:20443/websocket
```

There is a index page that is server when the server comes up which would connect 
to the secured web socket.


### Secured web socket page:

```
https://localhost:20443
```
=================================================================================
