package com.distributed_systems.group_2.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * jax rs client interface
 */
public interface ServerConnector {
    /**
     * register current username at server
     * @param udpPort to which port other users should send a communication request
     */
    public void initServerConnection(int udpPort) throws IOException;

    /**
     *
     * @param clientName with which person i want to communicate
     * @return represents the client i want to communicate with
     */
    public OtherClient getOtherClientFromServer(String clientName) throws IOException;

    /**
     * deregister your userName at the server
     */
    public void sendLeaveMessage() throws IOException;

    /**
     *
     * @return the username with that you registered yourself at the server
     */
    public String getCurrentUserName();
    public void sendStillAliveMessage();
}
