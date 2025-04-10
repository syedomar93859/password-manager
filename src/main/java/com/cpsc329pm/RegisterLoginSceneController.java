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
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root));
            stage.show();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void newAccount(ActionEvent event) {
        String username = newUsernameField.getText();
        String againUsername = confirmUsernameField.getText();
        String password = newPasswordField.getText();
        String againPassword = confirmPasswordField.getText();

        if (username.equals(againUsername) && password.equals(againPassword)) {
            try {
                DataStorage masterStorage = new DataStorage("master_login.json");

                // Ensure no duplicates before adding
                if (masterStorage.getData("master", username) != null) {
                    // Create an alert for existing username
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Account Creation Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Username already exists!");
                    alert.showAndWait();
                    return;  // Prevent overwriting existing user
                }

                masterStorage.addData("master", username, password);
                masterStorage.saveToJSON();

                Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Create an alert for invalid credentials
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Credentials");
            alert.setHeaderText(null);
            alert.setContentText("Invalid credentials. Please fill out all the boxes and make sure both username and password match.");
            alert.showAndWait();
        }
    }



    @FXML
    private void reqRegister(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Help");
        alert.setHeaderText("What Usernames and Passwords are Valid?");
        alert.setContentText(
                "Password must:\n" +
                        "- Be at least 8 characters long\n" +
                        "- Contain at least one uppercase letter\n" +
                        "- Contain at least one lowercase letter\n" +
                        "- Contain at least one number\n" +
                        "- Contain at least one special character (!@#$%^&*(),.?\":{}|<>)\n" +
                        "- Cannot contain spaces\n" +
                        "- Cannot contain common words or patterns"
        );

        alert.getButtonTypes().add(ButtonType.OK);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("color.css").toExternalForm());
        alert.showAndWait();
    }
}
