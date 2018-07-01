package com.distributed_systems.group_2.interfaces;

public interface MessageHandler {
    int STATUS_SUCCESS = 0;
    int STATUS_COULD_NOT_REACH_SERVER = 1;
    int STATUS_NAME_IN_USE = 2;

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
     * @param statusCode {@link #STATUS_SUCCESS}, {@link #STATUS_COULD_NOT_REACH_SERVER}, {@link #STATUS_NAME_IN_USE}
     */
    void onRegisteredAtServer(int statusCode);
}
