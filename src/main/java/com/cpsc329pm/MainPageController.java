package com.cpsc329pm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainPageController {

    @FXML
    private void handleAddService(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load AddServiceScene.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddServiceScene.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Add New Service");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the Main Page
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteService(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load DeleteServiceScene.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteServiceScene.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Add New Service");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the Main Page
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
