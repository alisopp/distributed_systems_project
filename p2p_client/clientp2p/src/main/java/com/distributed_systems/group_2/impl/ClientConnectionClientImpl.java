package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.P2PClient;
import com.distributed_systems.group_2.interfaces.ClientConnectionClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnectionClientImpl extends Thread implements ClientConnectionClient {

    private P2PClient p2pClient;
    private OtherClient otherClient;

    private Socket clientSocket;

    private boolean isRunning;
    private PrintWriter out;
    private BufferedReader in;

    public ClientConnectionClientImpl(P2PClient p2pClient, OtherClient otherClient) {
        this.p2pClient = p2pClient;
        this.otherClient = otherClient;
    }

    @Override
    public P2PClient getLocalClient() {

        return p2pClient;
    }


    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void closeConnection() throws IOException {
        isRunning = false;
        clientSocket.close();
    }

    @Override
    public void startConnection() throws IOException {
        clientSocket = new Socket(otherClient.getRemoteAddress().getHostAddress(), otherClient.getSocketPort());
        isRunning = true;

        out = new PrintWriter(clientSocket.getOutputStream(), true);

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        start();
        p2pClient.connectionEstablished(this);
    }

    @Override
    public OtherClient getOtherClient() {
        return otherClient;
    }

    @Override
    public void run() {
        try
        {
            while (isRunning)
            {
                String input;
                while ((input = in.readLine()) != null )
                {
                    System.out.println("ClientConnectionClientImpl - " + input);
                    p2pClient.onReceivedMessage(otherClient,input);
                }
            }
        }catch (IOException e) {
            if (isRunning) { // still running and exception happened!
                try {
                    System.out.println("ClientConnectionClientImpl Exception!");
                    p2pClient.onCommunicationLost(otherClient);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }
}
