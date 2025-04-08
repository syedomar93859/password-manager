package com.cpsc329pm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AddServiceSceneController {

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Get current stage (Add New Service)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load MainPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current Add Service window
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}