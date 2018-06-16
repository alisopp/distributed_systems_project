package com.distributed_systems.group_2.interfaces;

import java.io.IOException;
import java.util.Map;

/**
 * Central class for creating a p2p chat client
 */
public interface P2PClient {
    void startUDP(int port) throws IOException;
    void register(String hostURL, String userName)  throws IOException;
    void sendMessage(int localCommunicationPartnerIndex);
    void connectTo(OtherClient otherClient) throws IOException;
    void setMessageHandler(MessageHandler messageHandler);
    Map<Integer,OtherClient> getCommunicationPartners();
}
