package com.cpsc329pm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class MainPageController {

    @FXML private Button viewButton;

    @FXML
    private Label currUsername;

    public void setCurrUsername(String username) {
        if (currUsername != null) {
            currUsername.setText(username);
        } else {
            System.err.println("currUsername TextField is null!");
        }
    }


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
            stage.setTitle("Delete A Service");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the Main Page
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditService(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load DeleteServiceScene.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditInfoScene.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Edit Your Services");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the Main Page
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewAllServices(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load DeleteServiceScene.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StoredServices.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("View All Your Services");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the Main Page
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBack(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Warning");
        alert.setContentText("Are you sure you want to logout? You will go to the login page.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);

        // Show the alert and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Login Page");
                stage.setScene(new Scene(root));
                stage.show();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
