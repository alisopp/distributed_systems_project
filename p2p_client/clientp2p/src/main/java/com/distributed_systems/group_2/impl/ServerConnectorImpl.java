package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.ServerConnector;

public class ServerConnectorImpl implements ServerConnector {
    //TODO implement
    private String serverUrl;
    private String currentUserName;

    public ServerConnectorImpl(String serverUrl, String currentUserName) {
        this.serverUrl = serverUrl;
        this.currentUserName = currentUserName;
    }

    @Override
    public void initServerConnection(int udpPort) {

    }

    @Override
    public OtherClient getOtherClientFromServer(String clientName) {
        return null;
    }

    @Override
    public void sendLeaveMessage() {

    }

    @Override
    public String getCurrentUserName() {
        return currentUserName;
    }

    @Override
    public void sendStillAliveMessage() {

    }
}
