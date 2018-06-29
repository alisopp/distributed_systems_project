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
import java.net.SocketTimeoutException;

public class ClientConnectionServerImpl extends Thread implements ClientConnectionServer {

    private final int SOCKET_TIMEOUT = 3000;
    private OtherClient otherClient;
    private P2PClient localClient;

    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private boolean isRunning;
    private PrintWriter out;
    private BufferedReader in;

    public ClientConnectionServerImpl(OtherClient otherClient, P2PClient localClient, int port) {
        this.otherClient = otherClient;
        this.localClient = localClient;
        this.port = port;
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void closeConnection() throws IOException {
        if (serverSocket != null)
            serverSocket.close();
        if (clientSocket != null)
            clientSocket.close();
        isRunning = false;
    }

    @Override
    public void startConnection() throws IOException {
        System.out.println("Opening serverSocket on port " + port + "...");
        serverSocket = new ServerSocket(port,1);
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
            serverSocket.setSoTimeout(SOCKET_TIMEOUT);
            System.out.println("Waiting for clients to connect...");
            clientSocket = serverSocket.accept();

            System.out.println("Client connected successfully to serverSocket!");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            localClient.connectionEstablished(this);
            String input;
            isRunning = true;

            while (isRunning && clientSocket.isConnected() && !clientSocket.isClosed())
            {
                while ((input = in.readLine()) != null )
                {
                    localClient.onReceivedMessage(otherClient,input);
                }
            }
        } catch (SocketTimeoutException e) {
            try {
                System.out.println("Timeout waiting for client socket!");
                localClient.onCommunicationEstablishedFailed(otherClient);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Timeout waiting for client socket!");
            if (isRunning) {    // if still running and exception happened (no controlled shutdown)
                try {
                    localClient.onCommunicationLost(otherClient);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
