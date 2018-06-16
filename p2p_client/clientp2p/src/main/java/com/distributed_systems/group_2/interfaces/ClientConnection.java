package com.distributed_systems.group_2.interfaces;

import java.io.IOException;

public interface ClientConnection{
    void sendMessage(String message);
    void closeConnection() throws IOException;
    void startConnection() throws IOException;
    OtherClient getOtherClient();
    P2PClient getLocalClient();
}
