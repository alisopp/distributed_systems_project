package com.distributed_systems.group_2;

import com.distributed_systems.group_2.impl.P2PClientImpl;
import com.distributed_systems.group_2.interfaces.MessageHandler;
import com.distributed_systems.group_2.interfaces.OtherClient;
import com.distributed_systems.group_2.interfaces.P2PClient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class Controller implements MessageHandler {

    private P2PClient client;

    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private TextArea chatText;
    @FXML private TableView<User> clientTable;
    @FXML private ListView chatHistory;
    @FXML private TextField connect;
    @FXML private StackPane holder;
    @FXML private GridPane chat;
    @FXML private GridPane connectToClient;
    @FXML private GridPane main;

    private Pane currentPane;



    @FXML
    public void updateStage() throws Exception{

        String masterIp=ip.getText();
        String masterPort=port.getText();
        client = new P2PClientImpl(7777,40000, "Hellmuth");
        client.setMessageHandler(this);
        client.register(masterIp+":"+masterPort);

    }
    @FXML
    public void keyDownChat(KeyEvent event) throws Exception{
        if (event.getCode().equals(KeyCode.ENTER)){
            chatHistory.getItems().add(new ChatPost(chatText.getText().trim()));
            client.sendMessage(0,chatText.getText());
            chatText.setText("");
        }
    }
    @FXML
    public void refreshClients() throws Exception{
        client.startCommunication(connect.getText());
        setPane(chat);
    }


    @Override
    public void onReceivedMessage(OtherClient client, String content) {
        if (chatHistory!=null){
            chatHistory.getItems().add(new ChatPost(content));
        }

    }

    @Override
    public void onLostCommunication(OtherClient client) {

    }

    @Override
    public void onCommunicationEstablished(OtherClient client) {

    }

    @Override
    public void onFailedToEstablishedACommunication(OtherClient client) {

    }

    @Override
    public void onRegisteredAtServer(boolean connectionSuccessful) {
        setPane(connectToClient);
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
}
