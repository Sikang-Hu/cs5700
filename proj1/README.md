# TCP Server and Client Pair

This is the first part of the project 1, to implement a TCP Server and Client Pair. It contains 3
parts.

## Components
* TCP Server: the server which will be deployed on remote, listening to and responding to the
questions sent by the client.

* TCP Client: the client through which our user can sent message to the server and get the response.

* Service: the service provider exploited by the Server, in which the main businesses logic are
implemented.

## Instruction
To run the program, we need first run our server and then the client.

1. Put the **TCPServer.java**, **StringService.java** and **ToggleService.java** at the
remote server, then compile them and run the server.
```
javac TCPServer.java
java TCPServer <port number>
```

2. Put the **TCPClient.java** at the user's machine. Then, compile the .java file and run the client
with the hostname of the server and the number of the port where the server is running.
```
javac TCPClient.java
java TCPClient <hostname> <port number>
```

3. On the client side, input the string you want to toggle and reverse, then press **Return**.

4. Receive the result send by server, then both server and client will shut down.