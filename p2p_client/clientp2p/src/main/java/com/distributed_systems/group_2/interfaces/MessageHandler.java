package com.distributed_systems.group_2.interfaces;

public interface MessageHandler {
    /**
     *
     * @param client person who sends the message
     * @param content content of the message
     */
    void onReceivedMessage(OtherClient client, String content);

    /**
     *
     * @param client to which i lost the connection
     */
    void onLostCommunication(OtherClient client);

    /**
     * a new communication was established
     * @param client the client to which a communication was established
     */
    void onCommunicationEstablished(OtherClient client);

    /**
     *
     * @param client the client to which a communication was not established
     */
    void onFailedToEstablishedACommunication(OtherClient client);


    /**
     *
     * @param connectionSuccessful
     */
    void onRegisteredAtServer(boolean connectionSuccessful);
}
