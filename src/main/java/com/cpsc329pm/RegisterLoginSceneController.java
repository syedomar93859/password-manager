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

/**
 * Controller class for handling the registration scene functionality.
 * Manages the logic for creating a new master login account,
 * validating inputs, displaying alerts, and navigating between scenes.
 */
public class RegisterLoginSceneController {

    // FXML fields linked to input elements in the registration scene
    @FXML
    private TextField newUsernameField;

    @FXML
    private TextField confirmUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Handles the action of clicking the Back button.
     * Navigates the user back to the LoginScene.
     *
     * @param event the action event triggered by the button click
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Get the current stage from the event
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Load the LoginScene FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
            Parent root = loader.load();
            // Create and show the new stage
            Stage stage = new Stage();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root));
            stage.show();
            // Close the current stage
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles account creation when the Register button is clicked.
     * Validates the username and password, shows alerts for errors,
     * and stores the new credentials if validation passes.
     *
     * @param event the action event triggered by the button click
     */
    @FXML
    private void newAccount(ActionEvent event) {
        // Get user input from the form and trim whitespace
        String username = newUsernameField.getText().trim();
        String againUsername = confirmUsernameField.getText().trim();
        String password = newPasswordField.getText().trim();
        String againPassword = confirmPasswordField.getText().trim();

        // Used to accumulate validation error messages
        StringBuilder checkString = new StringBuilder();

        // Username validation
        if (username.isEmpty()) {
            checkString.append("Please fill out the Username box.\n");
        }
        if (againUsername.isEmpty()) {
            checkString.append("Please fill out the Confirm Username box.\n");
        }
        if (!username.equals(againUsername)) {
            checkString.append("Username and Confirm Username do not match.\n");
        }

        // Password validation
        if (password.isEmpty()) {
            checkString.append("Please fill out the Password box.\n");
        }
        if (againPassword.isEmpty()) {
            checkString.append("Please fill out the Confirm Password box.\n");
        }
        if (!password.equals(againPassword)) {
            checkString.append("Password and Confirm Password do not match.\n");
        }

        // Show alert if any issues were found
        if (!checkString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText(checkString.toString());
            alert.showAndWait();
        } else if (username.equals(againUsername) && password.equals(againPassword)) {
            try {
                // Create a storage handler for master login credentials
                DataStorage masterStorage = new DataStorage("master_login.json");

                // Check if username already exists
                if (masterStorage.getData("master", username) != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Registration Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Username already exists!");
                    alert.showAndWait();
                    return;  // Prevent overwriting an existing user
                }

                // Add new user data and save to file
                masterStorage.addData("master", username, password);
                masterStorage.saveToJSON();

                // Navigate back to login screen
                Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Generic fallback alert for unexpected validation failure
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all the boxes and make sure both username and password match.");
            alert.showAndWait();
        }
    }

    /**
     * Displays a dialog box explaining valid requirements for a username and password.
     * Triggered when the user clicks on a "?" or help icon.
     *
     * @param event the action event triggered by the button click
     */
    @FXML
    private void reqRegister(ActionEvent event) {
        // Create a help dialog describing valid password rules
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

        // Add OK button and apply stylesheet for styling
        alert.getButtonTypes().add(ButtonType.OK);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("color.css").toExternalForm());
        alert.showAndWait();
    }
}
