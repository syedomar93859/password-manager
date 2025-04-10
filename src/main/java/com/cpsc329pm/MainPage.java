
package com.cpsc329pm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage {

    private Stage stage;
    private Scene scene;

    @FXML
    public void switchToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Page");
        stage.show();
    }

    @FXML
    public void switchToAddService(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddServiceScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add New Service");
        stage.show();
    }

    @FXML
    public void switchToDeleteService(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DeleteServiceScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Delete Service");
        stage.show();
    }

    @FXML
    public void deleteAllServices(ActionEvent event) {
        System.out.println("Delete All Services clicked");
    }

    @FXML
    public void viewAllServices(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StoredServices.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("All Services");
        stage.show();
    }
}