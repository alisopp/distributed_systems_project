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
    void onCommunicationLost(OtherClient otherClient) throws IOException;
    void onCommunicationEstablishedFailed(OtherClient otherClient) throws IOException;

    /**
     * starts a communication with another client by getting his address from the server
     * @param clientName
     * @throws IOException
     */
    void startCommunication(String clientName) throws IOException;
    void sendMessage(int localCommunicationPartnerIndex, String message);
    void connectionEstablished(ClientConnection clientConnection);
    void connectTo(OtherClient otherClient) throws IOException;
    void setMessageHandler(MessageHandler messageHandler);
    void shutdown() throws IOException, InterruptedException;
    Map<Integer,ClientConnection> getCommunicationPartners();
}
