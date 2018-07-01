package com.distributed_systems.group_2.impl;

import com.distributed_systems.group_2.constants.Constants;
import com.distributed_systems.group_2.enums.ResponseStatus;
import com.distributed_systems.group_2.interfaces.MessageHandler;
import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.ServerConnector;
import com.distributed_systems.group_2.models.request.JoinRequestEntity;
import com.distributed_systems.group_2.models.request.LeaveRequestEntity;
import com.distributed_systems.group_2.models.request.UserRequestEntity;
import com.distributed_systems.group_2.models.response.JoinResponseEntity;
import com.distributed_systems.group_2.models.response.UserResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerConnectorImpl implements ServerConnector {
    //TODO implement
    private String currentUserName;
    private MessageHandler handler;

    private final String basicUrl;
    private final String joinUrl;
    private final String leaveUrl;
    private final String userUrl;

    private Client client;

    public ServerConnectorImpl(String serverUrl, String currentUserName, MessageHandler handler) {
        this.currentUserName = currentUserName;
        this.handler = handler;
        this.basicUrl = serverUrl + "/SUPERSERVER-JAXRS-JSON/rest";
        joinUrl = basicUrl + "/join";
        leaveUrl = basicUrl + "/leave";
        userUrl = basicUrl + "/user";
        client = Client.create();
    }

    @Override
    public void initServerConnection(int udpPort) throws IOException {
        WebResource webResource = client.resource(joinUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        JoinRequestEntity request = new JoinRequestEntity();
        request.setUsername(currentUserName);
        try {
            request.setHost(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        request.setPort(udpPort);
        request.setSecret(Constants.CLIENT_AUTHENTICATION_SECRET);

        String jsonRequest = objectMapper.writeValueAsString(request);
        ClientResponse clientResponse = webResource.type("application/json").post(ClientResponse.class, jsonRequest);


        String jsonResponse = clientResponse.getEntity(String.class);
        JoinResponseEntity response = objectMapper.readValue(jsonResponse, JoinResponseEntity.class);

        switch (response.getStatus()) {
            case SUCCESS:
                handler.onRegisteredAtServer(MessageHandler.STATUS_SUCCESS);
                break;
            case USERNAME_ALREADY_EXISTS:
                handler.onRegisteredAtServer(MessageHandler.STATUS_NAME_IN_USE);
                break;
            case AUTHENTICATION_ERROR:
                handler.onRegisteredAtServer(MessageHandler.STATUS_COULD_NOT_REACH_SERVER);
                break;
        }
    }

    @Override
    public OtherClient getOtherClientFromServer(String clientName) throws IOException {
        WebResource webResource = client.resource(userUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequestEntity request = new UserRequestEntity();
        request.setUsername(clientName);
        request.setSecret(Constants.CLIENT_AUTHENTICATION_SECRET);

        String jsonRequest = objectMapper.writeValueAsString(request);
        ClientResponse clientResponse = webResource.type("application/json").post(ClientResponse.class, jsonRequest);

        String jsonResponse = clientResponse.getEntity(String.class);
        UserResponseEntity response = objectMapper.readValue(jsonResponse, UserResponseEntity.class);

        switch (response.getStatus()) {
            case SUCCESS:
                return new OtherClientImpl(clientName, response.getPort(), InetAddress.getByName(response.getHost()));
            case USERNAME_DOES_NOT_EXISTS:
            case AUTHENTICATION_ERROR:
            default:
                return null;

        }
    }

    @Override
    public void sendLeaveMessage() throws IOException {
        WebResource webResource = client.resource(leaveUrl);
        ObjectMapper objectMapper = new ObjectMapper();

        LeaveRequestEntity request = new LeaveRequestEntity();
        request.setUsername(currentUserName);
        request.setSecret(Constants.CLIENT_AUTHENTICATION_SECRET);

        String jsonRequest = objectMapper.writeValueAsString(request);
        ClientResponse clientResponse = webResource.type("application/json").post(ClientResponse.class, jsonRequest);

        String jsonResponse = clientResponse.getEntity(String.class);
        JoinResponseEntity response = objectMapper.readValue(jsonResponse, JoinResponseEntity.class);
    }

    @Override
    public String getCurrentUserName() {
        return currentUserName;
    }



    @Override
    public void sendStillAliveMessage() {

    }
}
