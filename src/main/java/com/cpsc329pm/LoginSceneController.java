package com.cpsc329pm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class LoginSceneController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;
    private final MasterLogin loginService = new MasterLogin();

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean success = loginService.authenticate(username, password);

        if (success) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/cpsc329pm/MainPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Create and show an alert when the login fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("You have either not entered your username and password or the username or password you entered is incorrect. Please try again.");

            // Show the alert
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            // Get current stage (Add New Service)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load MainPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterLoginScene.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Register Page");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current Add Service window
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
