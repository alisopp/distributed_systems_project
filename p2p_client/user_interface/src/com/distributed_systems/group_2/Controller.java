package com.distributed_systems.group_2;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Controller {
    @FXML
    public void updateStage() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("chat.fxml"));
        Main.primaryStage.getScene().setRoot(root);
    }
}
