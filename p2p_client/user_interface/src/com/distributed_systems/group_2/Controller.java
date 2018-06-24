package com.distributed_systems.group_2;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    @FXML
    public void updateStage() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("connectToClient.fxml"));
        Main.primaryStage.getScene().setRoot(root);
        System.out.println(clientTable==null);
    }
    @FXML
    public void connectTo() throws Exception{
        if (clientTable.getSelectionModel().getSelectedItem()==null){
            clientTable.getItems().add(new User("hallo","wurlt"));
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("chat.fxml"));
        Main.primaryStage.getScene().setRoot(root);
        System.out.println(clientTable.getSelectionModel().getSelectedItem().getName());

    }
    @FXML
    public void keyDownChat(KeyEvent event) throws Exception{
        if (event.getCode().equals(KeyCode.ENTER)){
            chatText.setText("");
        }
    }
}
