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

/**
 * Controller class for handling user login and navigation to the registration page.
 */
public class LoginSceneController {

    @FXML
    private TextField usernameField; // Text field for user to enter username

    @FXML
    private PasswordField passwordField; // Password field for user to enter password

    @FXML
    private Button loginButton; // Button to initiate login process

    @FXML
    private Button registerButton; // Button to navigate to registration scene

    private final MasterLogin loginService = new MasterLogin(); // Service class to authenticate credentials

    /**
     * Handles login button click event.
     *
     * @param event Action event triggered by login button
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        // Get user input from fields
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Authenticate user
        boolean success = loginService.authenticate(username, password);

        // StringBuilder to accumulate error messages
        StringBuilder checkString = new StringBuilder();

        // Check if fields are empty
        if (username.isEmpty()){
            checkString.append("You have not entered a username.\n");
        }
        if (password.isEmpty()){
            checkString.append("You have not entered a password.\n");
        }

        // Show error alert if any field is empty
        if (!checkString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText(checkString.toString());
            alert.showAndWait();
        } else if (success) {
            try {
                // Load the main page FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cpsc329pm/ReformattedMainPage.fxml"));
                Parent root = loader.load(); // Load FXML and get root node

                // Get the controller of the loaded FXML
                MainPageController controller = loader.getController();

                // Store username in session
                UserSession.setUsername(username);
                String currentUser = UserSession.getUsername();

                // Pass username to main page controller
                controller.setCurrUsername(currentUser);

                // Switch to the main page scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Main Page");
                stage.show();
            } catch (Exception e) {
                // Handle FXML loading errors
                e.printStackTrace();
            }
        } else {
            // Show login failed alert if credentials are invalid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("The username or password you entered is incorrect. Please try again.");
            alert.showAndWait();
        }
    }

    /**
     * Handles register button click event.
     * Navigates the user to the registration scene.
     *
     * @param event Action event triggered by register button
     */
    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            // Get current window (login scene)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the registration scene FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterLoginScene.fxml"));
            Parent root = loader.load();

            // Create a new window for the registration scene
            Stage stage = new Stage();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the login window
            currentStage.close();

        } catch (IOException e) {
            // Handle FXML loading errors
            e.printStackTrace();
        }
    }
}
