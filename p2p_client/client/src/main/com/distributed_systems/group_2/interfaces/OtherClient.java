package com.distributed_systems.group_2.interfaces;

import java.net.InetAddress;

public interface OtherClient {

    public InetAddress udpSocketAddress();
    public int udpSocketPort();
    public int localChatPartnerIndex();
}
