package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.interfaces.ClientConnectionServer;
import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.P2PClient;
import com.distributed_systems.group_2.interfaces.ServerConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnectionServerImpl extends Thread implements ClientConnectionServer {

    private OtherClient otherClient;
    private P2PClient localClient;


    private ServerSocket serverSocket;
    private Socket clientSocket;
    private boolean isRunning;
    private PrintWriter out;
    private BufferedReader in;

    public ClientConnectionServerImpl(OtherClient otherClient, P2PClient localClient, ServerSocket serverSocket) {
        this.otherClient = otherClient;
        this.localClient = localClient;
        this.serverSocket = serverSocket;
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void closeConnection() throws IOException {
        if (clientSocket != null)
        {
            clientSocket.close();
        }
        isRunning = false;
    }

    @Override
    public void startConnection() throws IOException {
        start();
    }

    @Override
    public OtherClient getOtherClient() {
        return otherClient;
    }

    @Override
    public P2PClient getLocalClient() {
        return localClient;
    }

    @Override
    public void run() {
        try {
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            localClient.connectionEstablished(this);
            String input;
            isRunning = true;
            while (isRunning)
            {
                while ((input = in.readLine()) != null )
                {
                    System.out.println("ClientConnectionSERVERImpl - " + input);
                    localClient.onReceivedMessage(otherClient,input);
                }
            }
        } catch (IOException e) {
            if (isRunning) {    // if still running and exception happened (no controlled shutdown)
                try {
                    System.out.println("ClientConnectionServerImpl Exception!");
                    localClient.onCommunicationLost(otherClient);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
