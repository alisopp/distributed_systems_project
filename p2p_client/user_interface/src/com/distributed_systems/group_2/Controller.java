package com.distributed_systems.group_2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Controller {
    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private TextArea chatText;
    @FXML private TableView<User> clientTable;
    @FXML private ListView chatHistory;
    @FXML
    public void updateStage() throws Exception{
        String masterIp=ip.getText();
        String masterPort=port.getText();
        //TODO connect to Master Server
        Parent root = FXMLLoader.load(getClass().getResource("connectToClient.fxml"));
        Main.primaryStage.getScene().setRoot(root);
    }
    @FXML
    public void connectTo() throws Exception{
        if (clientTable.getSelectionModel().getSelectedItem()==null){
            return;
        }
        User user=clientTable.getSelectionModel().getSelectedItem();
        //TODO connect to user
        Parent root = FXMLLoader.load(getClass().getResource("chat.fxml"));
        Main.primaryStage.getScene().setRoot(root);
    }
    @FXML
    public void keyDownChat(KeyEvent event) throws Exception{
        if (event.getCode().equals(KeyCode.ENTER)){
            chatHistory.getItems().add(new ChatPost(chatText.getText()));
            chatText.setText("");
        }
    }
    @FXML
    public void refreshClients() throws Exception{
        if (clientTable==null) {
            return;
        }
        clientTable.getItems().clear();
        //TODO get Users from master Server and add them

        addUser("user","0.1.2.3");
    }

    public void addUser(String name, String ip){
        if (clientTable!=null)
        clientTable.getItems().add(new User(name,ip));
    }
}
