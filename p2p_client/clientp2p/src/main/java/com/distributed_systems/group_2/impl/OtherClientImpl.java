package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.interfaces.OtherClient;

import java.net.InetAddress;

public class OtherClientImpl implements OtherClient {

    private String userName;
    private int port;
    private InetAddress socketAddress;
    private int localCommunicationPartnerIndex;

    public OtherClientImpl(String userName, int port, InetAddress socketAddress) {
        this.userName = userName;
        this.port = port;
        this.socketAddress = socketAddress;
    }

    @Override
    public InetAddress getRemoteAddress() {
        return socketAddress;
    }

    @Override
    public int getSocketPort() {
        return port;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public int localCommunicationPartnerIndex() {
        return localCommunicationPartnerIndex;
    }

    @Override
    public void setLocalCommunicationPartnerIndex(int index) {
        this.localCommunicationPartnerIndex = index;
    }
}
