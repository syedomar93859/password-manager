package com.cpsc329pm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


public class AddServiceSceneController {

    @FXML
    private TextField serviceField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmPasswordField;


    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Get current stage (Add New Service)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load MainPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReformattedMainPage.fxml"));
            Parent root = loader.load();

            //  Now get the controller from the loader
            MainPageController controller = loader.getController();
            String currentUser = UserSession.getUsername();
            controller.setCurrUsername(currentUser);  // Pass username to controller

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
    private void handleAdd(ActionEvent event) {
        String platform = serviceField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmUser = confirmPasswordField.getText().trim();

        StringBuilder checkString = new StringBuilder();

// Username validation
        if (platform.isEmpty()) {
            checkString.append("Please fill out the Name of Service box.\n");
        }
        if (username.isEmpty()) {
            checkString.append("Please fill out the Username box.\n");
        }
        if (password.isEmpty()) {
            checkString.append("Please fill out the Password box.\n");
        }
        if (confirmUser.isEmpty()) {
            checkString.append("Please fill out the Confirm Password box.\n");
        }
        if (!password.equals(confirmUser)) {
            checkString.append("Password and Confirm Password do not match.\n");
        }

// Show alert if any issues were found
        if (!checkString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Adding Service Failed");
            alert.setHeaderText(null);
            alert.setContentText(checkString.toString());
            alert.showAndWait();
        }else if (password.equals(confirmUser)) {
            try {
                DataStorage ds = new DataStorage("master_login.json");

                // Check if the service already exists
                if (ds.getData(platform, username) != null) {
                    // If service exists, show error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add Service");
                    alert.setHeaderText("Error");
                    alert.setContentText("Service already exists for this platform and username.");
                    alert.showAndWait();
                    return; // Exit method early if service already exists
                }

                // Add new service if it doesn't exist
                ds.addData(platform, username, password);
                ds.saveToJSON();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Service");
                alert.setHeaderText("Success");
                alert.setContentText("Service added successfully.");
                alert.showAndWait();

                // Load MainPage.fxml after success
                Parent root = FXMLLoader.load(getClass().getResource("ReformattedMainPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        } else {
//            // Show error if passwords don't match
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Add Service");
//            alert.setHeaderText("Error");
//            alert.setContentText("Password does not match Confirm Password.");
//            alert.showAndWait();
//        }
    }


}