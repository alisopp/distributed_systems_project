Projekt: chat based system, unstructured peer2peer-network, simple version of Skype  
	!GUI  
	USE Java/Node/JS, NOT C/C++/C#....  
  
Expected technologies:
* Java jax rs (Restful webservice for server) a client will communicate with it to find the ip address and port of another client
* Java UDP for initializing a tcp connection between two clients
* Java tcp for chatting between two clients

### Use Case  
Alice wants to communicate with Bob.
Alice gets Bob's address from the server. Then Alice opens a server socket in a new thread and sends a message over an UDP interface to Bob asking him to connect to Alice's server socket.
