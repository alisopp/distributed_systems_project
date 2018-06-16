package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.P2PClient;
import com.distributed_systems.group_2.interfaces.MessageHandler;
import com.distributed_systems.group_2.interfaces.ServerConnector;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Map;

public class P2PClientImpl implements P2PClient {

    private Map<Integer, OtherClient> otherClients;
    private int udpPort;
    private InetAddress address;
    private ServerConnector connectionToSuperServer;




    @Override
    public void startUDP(int port) throws IOException {

    }

    @Override
    public void register(String hostURL, String userName) throws IOException {
        connectionToSuperServer = new ServerConnectorImpl(hostURL, userName);

    }

    @Override
    public void sendMessage(int localCommunicationPartnerIndex) {

    }

    @Override
    public void connectTo(OtherClient otherClient) throws IOException {

    }

    @Override
    public void setMessageHandler(MessageHandler messageHandler) {

    }

    @Override
    public Map<Integer, OtherClient> getCommunicationPartners() {
        return null;
    }


    private class CommunicationEstablisher extends Thread
    {

        private boolean isRunning;
        private int port;

        public CommunicationEstablisher(int port) {
            this.port = port;
        }

        @Override
        public synchronized void start() {
            isRunning= true;
            super.start();
        }

        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket(port);
                while (isRunning)
                {
                    byte[] buf = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
