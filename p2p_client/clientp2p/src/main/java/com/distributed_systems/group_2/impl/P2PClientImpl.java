package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.interfaces.*;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class P2PClientImpl implements P2PClient {

    private static final String TCP_PORT = "tcp_port";
    private static final String USER_NAME = "user_name";

    private Map<Integer, OtherClient> otherClients;
    private int udpPort;
    private InetAddress address;
    private ServerConnector connectionToSuperServer;
    private MessageHandler messageHandler;
    private Map<Integer, ClientConnection> connectionPartners;
    private int lastPartnerID;
    private final Object connectionEstablishedToken;
    private DatagramSocket socket;
    private int portStart = 4444;
    private String userName;
    private CommunicationEstablisher establisher;
    private ArrayList<ClientConnectionServer> clientConnectionServerInstances;

    public P2PClientImpl(int udpPort, int tcpPortStart, String userName) {
        connectionPartners = new HashMap<>();
        this.udpPort = udpPort;
        this.portStart = tcpPortStart;
        this.userName = userName;
        lastPartnerID = 0;
        connectionEstablishedToken = new Object();
        clientConnectionServerInstances = new ArrayList<>();
    }

    @Override
    public void startUDP() throws IOException {

        socket = new DatagramSocket(udpPort);
        establisher = new CommunicationEstablisher(this);
        establisher.start();
    }

    @Override
    public void register(String hostURL) throws IOException {
        connectionToSuperServer = new ServerConnectorImpl(hostURL, userName, messageHandler);
        connectionToSuperServer.initServerConnection(444);
        startUDP();
    }

    @Override
    public void onReceivedMessage(OtherClient sender,String message) {
        messageHandler.onReceivedMessage(sender,message);
    }

    @Override
    public void onCommunicationLost (OtherClient otherClient) throws IOException {
        //TODO lost communication with chat partner
        messageHandler.onLostCommunication(otherClient);

        System.out.println("Connection to " + otherClient.getUserName() + " lost!");
        // try to reconnect using a new port

        if (!otherClient.hasServerSocket()) {   // if other client has the clientSocket
            System.out.println("Trying to reconnect to " + otherClient.getUserName());
            connectTo(otherClient);
        }
    }

    @Override
    public void onCommunicationEstablishedFailed(OtherClient otherClient) throws IOException {
        //TODO failed to connect to chatPartner
        messageHandler.onFailedToEstablishedACommunication(otherClient);

        System.out.println("Failed to establish connection to " + otherClient.getUserName());
        // try to reconnect using a new port

        if (!otherClient.hasServerSocket()) {   // if other client has the clientSocket
            System.out.println("Trying to reconnect to " + otherClient.getUserName());
            connectTo(otherClient);
        }
    }

    @Override
    public void startCommunication(String clientName) throws IOException {
        OtherClient otherClient = connectionToSuperServer.getOtherClientFromServer(clientName);
        connectTo(otherClient);
    }

    @Override
    public void sendMessage(int localCommunicationPartnerIndex, String message) {
        connectionPartners.get(localCommunicationPartnerIndex).sendMessage(message);
    }

    @Override
    public void connectionEstablished(ClientConnection clientConnection) {
        synchronized (connectionEstablishedToken){
            connectionPartners.put(lastPartnerID, clientConnection);
            clientConnection.getOtherClient().setLocalCommunicationPartnerIndex(lastPartnerID);
            lastPartnerID++;
        }
        messageHandler.onCommunicationEstablished(clientConnection.getOtherClient());
    }

    @Override
    public void connectTo(OtherClient otherClient) throws IOException {
        JSONObject message = new JSONObject();
        message.put(USER_NAME, userName);
        message.put(TCP_PORT, portStart);
        String msg = message.toString();
        byte[]buf = msg.getBytes();
        System.out.println("connectTo called: " + userName + " waiting as ServerSocket for " + otherClient.getUserName());
        DatagramPacket packet = new DatagramPacket(buf, buf.length, otherClient.getRemoteAddress(), otherClient.getSocketPort());
        ClientConnectionServer clientConnectionServer = new ClientConnectionServerImpl(otherClient, this,portStart);
        clientConnectionServerInstances.add(clientConnectionServer);
        clientConnectionServer.startConnection();
        socket.send(packet);
        portStart++;
    }

    @Override
    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void shutdown() throws IOException, InterruptedException {
        System.out.println(userName + " got shutdown request!");

        establisher.stopCommunicationEstablisher();
        ArrayList<ClientConnectionClient> clientConnectionClientInstances = establisher.getClientConnectionClientInstances();

        System.out.println("Closing " + clientConnectionClientInstances.size() + " client connections...");
        // close client sockets
        for (ClientConnectionClient clientConnectionClient : clientConnectionClientInstances) {
            clientConnectionClient.closeConnection();
        }
        System.out.println("Done.");

        Thread.sleep(1000);

        System.out.println("Closing " + clientConnectionServerInstances.size() + " server connections...");
        // close server sockets
        for (ClientConnectionServer clientConnectionServer : clientConnectionServerInstances) {
            clientConnectionServer.closeConnection();
        }
        System.out.println("Done.");

        Thread.sleep(1000);

        socket.close();
    }

    @Override
    public Map<Integer, ClientConnection> getCommunicationPartners() {
        return connectionPartners;
    }


    private class CommunicationEstablisher extends Thread
    {
        private boolean isRunning;
        private P2PClient p2pClient;
        private ArrayList<ClientConnectionClient> clientConnectionClientInstances;

        public CommunicationEstablisher(P2PClient client) {

            p2pClient = client;
            clientConnectionClientInstances = new ArrayList<>();
        }

        public void stopCommunicationEstablisher() {
            isRunning = false;
        }

        public ArrayList<ClientConnectionClient> getClientConnectionClientInstances() {
            return clientConnectionClientInstances;
        }

        @Override
        public synchronized void start() {
            isRunning= true;
            super.start();
        }

        @Override
        public void run() {
            OtherClient temp = null;
            try {

                while (isRunning)
                {
                    byte[] buf = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(),0, packet.getLength());
                    JSONObject object = new JSONObject(message);
                    if (!object.has(USER_NAME) || !object.has(TCP_PORT))
                    {
                        System.err.println("Received an unknown message");
                        continue;
                    }
                    int port = object.getInt(TCP_PORT);
                    String userName = object.getString(USER_NAME);
                    InetAddress address = packet.getAddress();
                    final OtherClient otherClient = new OtherClientImpl(userName, port, address);
                    temp = otherClient;
                    otherClient.setHasServerSocket();
                    Thread thread = new Thread(() -> {
                        ClientConnectionClient clientConnectionClient = new ClientConnectionClientImpl(p2pClient, otherClient);
                        clientConnectionClientInstances.add(clientConnectionClient);
                        try {
                            clientConnectionClient.startConnection();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    thread.start();
                }
            } catch (IOException e) {
                if (isRunning) {
                    try {
                        p2pClient.onCommunicationEstablishedFailed(temp);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
