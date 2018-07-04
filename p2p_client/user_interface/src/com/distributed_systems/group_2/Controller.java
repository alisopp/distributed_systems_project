package com.distributed_systems.group_2;

import com.distributed_systems.group_2.impl.P2PClientImpl;
import com.distributed_systems.group_2.interfaces.MessageHandler;
import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.P2PClient;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;


public class Controller implements MessageHandler {



    private P2PClient client;

    // main page
    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private TextField udp_port;
    @FXML private TextField tcp_port;
    @FXML private TextField username;
    @FXML private GridPane main;

    // chat
    @FXML private Label usernameLabel;
    @FXML private TextArea chatHistoryTable;
    @FXML private TextField chatInput;
    @FXML private ListView communicationPartners;
    @FXML private GridPane connectToClient;
    @FXML private TextField connect;
    @FXML private StackPane holder;
    @FXML private GridPane chat;





    private Pane currentPane;
    private int currentChatPartner ;
    private String userName;
    private ArrayList<ChatHistory> chatHistoryPerUser;

    public Controller() {
        chatHistoryPerUser = new ArrayList<>();
        currentChatPartner = -1;
        Main.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    if(client != null)
                    {
                        client.shutdown();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void updateStage() throws Exception{

        String masterIp=ip.getText();
        String masterPort=port.getText();
        if(client == null)
        {
            client = new P2PClientImpl(Integer.valueOf(udp_port.getText()),Integer.valueOf(tcp_port.getText()), username.getText());
            client.setMessageHandler(this);
        }else {
            client.setUserName(username.getText());
        }

        client.register(masterIp+":"+masterPort);
        userName = username.getText();
    }

    @FXML
    public void refreshClients() throws Exception{
        client.startCommunication(connect.getText());
    }

    @FXML
    public void backToServerSelect() throws Exception{
        client.shutdown();
        setPane(main);
    }


    @FXML
    public void sendMessage()
    {
        client.sendMessage(currentChatPartner, chatInput.getText());
        ChatHistory history = chatHistoryPerUser.get(currentChatPartner);
        String text = userName + ": " +chatInput.getText();
        history.addInput(text);
        chatHistoryTable.appendText(text + "\n");
        chatInput.clear();
    }

    private void switchChatPartner(int chatPartnerIndex)
    {
        currentChatPartner = chatPartnerIndex;
        chatHistoryTable.clear();
        chatHistoryTable.setText(chatHistoryPerUser.get(chatPartnerIndex).getCompleteHistory());
    }


    @Override
    public void onReceivedMessage(OtherClient client, String content) {
        ChatHistory history = chatHistoryPerUser.get(client.localCommunicationPartnerIndex());
        String text = client.getUserName() + ": " + content;
        history.addInput(text);
        if(currentChatPartner == client.localCommunicationPartnerIndex())
        {
            chatHistoryTable.appendText(text+"\n");
        }
    }

    @Override
    public void onLostCommunication(OtherClient client) {

    }

    @Override
    public void onCommunicationEstablished(OtherClient client) {
        communicationPartners.getItems().add(new ClientView(client.getUserName(), client.localCommunicationPartnerIndex()));
        chatHistoryPerUser.add(new ChatHistory());
        switchChatPartner(client.localCommunicationPartnerIndex());
    }

    @Override
    public void onFailedToEstablishedACommunication(OtherClient client) {

    }

    @Override
    public void onRegisteredAtServer(int status) {
        if (status == MessageHandler.STATUS_SUCCESS)
        {
            setPane(connectToClient);
            usernameLabel.setText(username.getText());
            communicationPartners.setOnMouseClicked(onCommunicationPartnerClickListener);
        }else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed to register at server");
            alert.setHeaderText(null);
            if(status == MessageHandler.STATUS_NAME_IN_USE)
            {
                alert.setContentText("Username already in use");
            } else
            {
                alert.setContentText("Could not reach server");
            }
            alert.showAndWait();
        }

    }


    public void setPane(Pane pane)
    {
        if(currentPane != null)
        {
            currentPane.setDisable(true);
            currentPane.setVisible(false);
        }else {
            main.setDisable(true);
            main.setVisible(false);
        }
        currentPane = pane;
        currentPane.setDisable(false);
        currentPane.setVisible(true);
    }

    private EventHandler<MouseEvent> onCommunicationPartnerClickListener = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            ClientView view = (ClientView) communicationPartners.getSelectionModel().getSelectedItem();
            switchChatPartner(view.getIndex());
        }
    };

}
