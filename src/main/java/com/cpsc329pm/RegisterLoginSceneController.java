package com.cpsc329pm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterLoginSceneController {
    @FXML
    private TextField newUsernameField;

    @FXML
    private TextField confirmUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Get current stage (Add New Service)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load MainPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
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

    @FXML
    private void newAccount(ActionEvent event) {
        String username = newUsernameField.getText();
        System.out.println(username);
        String againUsername = confirmUsernameField.getText();
        System.out.println(againUsername);

        String password = newPasswordField.getText();
        System.out.println(password);
        String againPassword = confirmPasswordField.getText();
        System.out.println(againPassword);

        if ((username.equals(againUsername)) &&(password.equals(againPassword))){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid credentials.");
            // Optional: display error in UI
        }
    }
}