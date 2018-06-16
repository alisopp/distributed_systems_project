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

    @Before
    public void init() throws IOException {
        p2PClient = new P2PClientImpl(20000,17000, "Alex");
        p2PClient2 = new P2PClientImpl(10000, 27000, "Patrick");
        p2PClient.startUDP();
        p2PClient2.startUDP();
        MessageHandler messageHandler = new MessageHandler() {
            @Override
            public void onReceivedMessage(OtherClient client, String content) {
                System.out.println(content);
            }

            @Override
            public void onLostCommunication(OtherClient client) {

            }

            @Override
            public void onCommunicationEstablished(OtherClient client) {
                System.out.println(client.getRemoteAddress().toString());
            }

            @Override
            public void onFailedToEstablishedACommunication(OtherClient client) {

            }
        };
        p2PClient.setMessageHandler(messageHandler);
        p2PClient2.setMessageHandler(messageHandler);
    }

    @Test
    public void testConnection() throws IOException, InterruptedException {
        OtherClient otherClient = new OtherClientImpl("Patrick", 10000, InetAddress.getByName("localhost"));
        p2PClient.connectTo(otherClient);
        Thread.sleep(1000);
        p2PClient2.sendMessage(0,"Hello");
        Thread.sleep(1000);
    }
}
