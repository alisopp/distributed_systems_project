package com.distributed_systems.group_2;

import com.distributed_systems.group_2.impl.OtherClientImpl;
import com.distributed_systems.group_2.impl.P2PClientImpl;
import com.distributed_systems.group_2.interfaces.MessageHandler;
import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.P2PClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

public class ConnectionTest {

    P2PClient p2PClient;
    P2PClient p2PClient2;
    private static int index = 0;

    @Before
    public void init() throws IOException, InterruptedException {
        index+=10;
        p2PClient = new P2PClientImpl(20000+index,17000+index, "Alex");
        p2PClient2 = new P2PClientImpl(10000+index, 27000+index, "Patrick");

        p2PClient.startUDP();
        p2PClient2.startUDP();

        MessageHandler messageHandler = new MessageHandler() {
            @Override
            public void onReceivedMessage(OtherClient client, String content) {

                System.out.println("Received message " + content + " - from " + client.getUserName());

            }

            @Override
            public void onLostCommunication(OtherClient client) {
                System.out.println("Communication to " + client.getUserName() + " lost!");
            }

            @Override
            public void onCommunicationEstablished(OtherClient client) {
                System.out.println(client.getRemoteAddress().toString());
            }

            @Override
            public void onFailedToEstablishedACommunication(OtherClient client) {

            }

            @Override
            public void onRegisteredAtServer(boolean connectionSuccessful) {

            }
        };
        p2PClient.setMessageHandler(messageHandler);
        p2PClient2.setMessageHandler(messageHandler);
    }

    @Test
    public void testConnection() throws IOException, InterruptedException {
        OtherClient otherClient = new OtherClientImpl("Patrick", 10000+index, InetAddress.getByName("localhost"));
        p2PClient.connectTo(otherClient);
        Thread.sleep(1000);
        p2PClient2.sendMessage(0,"Hello");
        Thread.sleep(1000);
        p2PClient.sendMessage(0, "back");
    }

    @Test
    public void testShutdown() throws IOException, InterruptedException {
        OtherClient otherClient = new OtherClientImpl("Patrick", 10000+index, InetAddress.getByName("localhost"));
        p2PClient.connectTo(otherClient);
        Thread.sleep(1000);
        p2PClient2.sendMessage(0,"Hello");
        Thread.sleep(1000);
        p2PClient.sendMessage(0, "back");
        Thread.sleep(1000);
        p2PClient2.shutdown();
        p2PClient.shutdown();
    }

    @Test
    public void testConnectionLost() throws IOException, InterruptedException {
        OtherClient otherClient = new OtherClientImpl("Patrick", 10000+index, InetAddress.getByName("localhost"));
        p2PClient.connectTo(otherClient);
        Thread.sleep(1000);
        p2PClient2.sendMessage(0,"Hello");
        Thread.sleep(1000);
        p2PClient.sendMessage(0, "back");
        Thread.sleep(1000);
        p2PClient2.shutdown();
        Thread.sleep(3000);
        p2PClient.connectTo(otherClient);
    }

    @Test
    public void testCommunicationEstablishFailed() throws IOException, InterruptedException {
        OtherClient otherClient = new OtherClientImpl("Patrick", 10000+index, InetAddress.getByName("localhost"));
        p2PClient2.shutdown();
        Thread.sleep(1000);
        p2PClient.connectTo(otherClient);
        Thread.sleep(7000);
        p2PClient.sendMessage(0,"Hello");
        p2PClient.shutdown();
        Thread.sleep(3000);
    }

    @Test
    public void testCommunicationEstablishFailedReconnectSuccessful() throws IOException, InterruptedException {
        OtherClient otherClient = new OtherClientImpl("Patrick", 10000+index, InetAddress.getByName("localhost"));
        p2PClient2.shutdown();  // shutdown other client...
        Thread.sleep(1000);
        p2PClient.connectTo(otherClient);   // ... so connect fails and triggers retries
        Thread.sleep(7000);
        p2PClient2.startUDP();  // after some time UDP is up and running again, retry should succeed now!
        Thread.sleep(3000);
        p2PClient.sendMessage(0,"Hello");
        Thread.sleep(1000);
        p2PClient2.sendMessage(0, "back");
        p2PClient2.shutdown();
        p2PClient.shutdown();
        Thread.sleep(3000);
    }
}