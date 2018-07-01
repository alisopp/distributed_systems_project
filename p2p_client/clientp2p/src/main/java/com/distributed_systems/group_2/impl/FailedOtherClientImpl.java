package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.interfaces.OtherClient;

import java.net.InetAddress;

public class FailedOtherClientImpl implements OtherClient {

    private String userName;

    public FailedOtherClientImpl(String userName) {
        this.userName = userName;
    }

    @Override
    public InetAddress getRemoteAddress() {
        return null;
    }

    @Override
    public int getSocketPort() {
        return 0;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public int localCommunicationPartnerIndex() {
        return 0;
    }

    @Override
    public void setLocalCommunicationPartnerIndex(int index) {

    }

    @Override
    public boolean hasServerSocket() {
        return false;
    }

    @Override
    public void setHasServerSocket() {

    }
}
