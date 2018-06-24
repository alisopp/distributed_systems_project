package com.distributed_systems.group_2;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class Controller {
    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private TableView<User> clientTable;
    @FXML
    public void updateStage() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("connectToClient.fxml"));
        Main.primaryStage.getScene().setRoot(root);
        //System.out.println(ip.getText()+"\n"+port.getText());

    }
    @FXML
    public void connectTo() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("chat.fxml"));
        Main.primaryStage.getScene().setRoot(root);
        System.out.println(clientTable.getSelectionModel().getSelectedItem().getName());
    }
}
