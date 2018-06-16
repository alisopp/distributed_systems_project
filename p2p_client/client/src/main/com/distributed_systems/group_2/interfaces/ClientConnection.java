package com.distributed_systems.group_2.interfaces;

public interface ClientConnection{
    void sendMessage(String message);
    void closeConnection();
    OtherClient getOtherClient();
    P2PClient getLocalClient();
}
