package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.interfaces.MessageHandler;
import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.ServerConnector;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerConnectorImpl implements ServerConnector {
    //TODO implement
    private String serverUrl;
    private String currentUserName;
    private MessageHandler handler;

    public ServerConnectorImpl(String serverUrl, String currentUserName, MessageHandler handler) {
        this.serverUrl = serverUrl;
        this.currentUserName = currentUserName;
        this.handler = handler;
    }

    @Override
    public void initServerConnection(int udpPort) {
        handler.onRegisteredAtServer(true);
    }

    @Override
    public OtherClient getOtherClientFromServer(String clientName) {
        try {
            return new OtherClientImpl(clientName, 1, InetAddress.getByName("10.0.0.1"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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
