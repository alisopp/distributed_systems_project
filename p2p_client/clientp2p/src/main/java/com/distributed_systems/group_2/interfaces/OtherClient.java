package com.distributed_systems.group_2.interfaces;

import java.net.InetAddress;

public interface OtherClient {

    public InetAddress getRemoteAddress();
    public int getSocketPort();
    public String getUserName();
    public int localCommunicationPartnerIndex();
    public void setLocalCommunicationPartnerIndex(int index);
    public boolean hasServerSocket();
    public void setHasServerSocket();
}
