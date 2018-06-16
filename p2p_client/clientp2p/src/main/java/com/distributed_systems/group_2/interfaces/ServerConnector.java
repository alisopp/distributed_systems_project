package com.distributed_systems.group_2.interfaces;

/**
 * jax rs client interface
 */
public interface ServerConnector {
    /**
     * register current username at server
     * @param udpPort to which port other users should send a communication request
     */
    public void initServerConnection(int udpPort);

    /**
     *
     * @param clientName with which person i want to communicate
     * @return represents the client i want to communicate with
     */
    public OtherClient getOtherClientFromServer(String clientName);

    /**
     * deregister your userName at the server
     */
    public void sendLeaveMessage();

    /**
     *
     * @return the username with that i registered myself at the server
     */
    public String getCurrentUserName();
    public void sendStillAliveMessage();
}
