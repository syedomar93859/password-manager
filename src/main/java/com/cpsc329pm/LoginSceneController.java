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
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        boolean success = loginService.authenticate(username, password);

        StringBuilder checkString = new StringBuilder();

        if (username.isEmpty()){
            checkString.append("You have not entered a username.\n");
        }
        if (password.isEmpty()){
            checkString.append("You have not entered a password.\n");
        }

        if (!checkString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText(checkString.toString());
            alert.showAndWait();
        }else if (success) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cpsc329pm/ReformattedMainPage.fxml"));
                Parent root = loader.load(); // Load FXML and get root node

                //  Now get the controller from the loader
                MainPageController controller = loader.getController();
                UserSession.setUsername(username);
                String currentUser = UserSession.getUsername();
                controller.setCurrUsername(currentUser);  // Pass username to controller

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Main Page");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Create and show an alert when the login fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("The username or password you entered is incorrect. Please try again.");

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
