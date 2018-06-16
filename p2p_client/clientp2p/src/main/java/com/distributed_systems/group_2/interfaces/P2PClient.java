package com.distributed_systems.group_2.interfaces;

import java.io.IOException;
import java.util.Map;

/**
 * Central class for creating a p2p chat client
 */
public interface P2PClient {
    void startUDP() throws IOException;
    void register(String hostURL)  throws IOException;
    void onReceivedMessage(OtherClient client, String message);
    void sendMessage(int localCommunicationPartnerIndex, String message);
    void connectionEstablished(ClientConnection clientConnection);
    void connectTo(OtherClient otherClient) throws IOException;
    void setMessageHandler(MessageHandler messageHandler);
    void shutdown();
    Map<Integer,ClientConnection> getCommunicationPartners();
}
