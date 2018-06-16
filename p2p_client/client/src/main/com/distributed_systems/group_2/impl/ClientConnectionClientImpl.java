package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.P2PClient;
import com.distributed_systems.group_2.interfaces.ClientConnectionClient;

public class ClientConnectionClientImpl implements ClientConnectionClient {

    P2PClient p2PClient;



    @Override
    public P2PClient getLocalClient() {

        return null;
    }


    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void closeConnection() {

    }

    @Override
    public OtherClient getOtherClient() {
        return null;
    }
}
