package com.cpsc329pm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the Add Service scene.
 * Handles user input for adding a new platform/service and navigation back to the main page.
 */
public class AddServiceSceneController {

    /** TextField for the name of the service/platform */
    @FXML
    private TextField serviceField;

    /** TextField for the username associated with the service */
    @FXML
    private TextField usernameField;

    /** TextField for the password associated with the service */
    @FXML
    private TextField passwordField;

    /** TextField for confirming the entered password */
    @FXML
    private TextField confirmPasswordField;

    /**
     * Handles the "Back" button action.
     * Navigates the user from the Add Service screen back to the main page.
     *
     * @param event ActionEvent triggered by the user.
     */
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

    /**
     * Handles the "Add" button action.
     * Validates user input, checks for duplicates, and saves new service credentials if valid.
     *
     * @param event ActionEvent triggered by the user.
     */
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
        }else if(username.length() < 8){
            checkString.append("Username must be at least 8 characters long.\n");
        }

        // Password validation
        MasterLogin masterLogin = new MasterLogin();
        masterLogin.registerWithConfirmation(username, username, password, confirmUser);
        boolean firstValidity = masterLogin.isValidPassword(password);
        boolean secondValidity = masterLogin.isValidPassword(confirmUser);

        if (password.isEmpty()) {
            checkString.append("Please fill out the Password box.\n");
        }else if (!firstValidity){
            checkString.append("Your Password does not fulfill the requirements.\n");
        }

        if (confirmUser.isEmpty()) {
            checkString.append("Please fill out the Confirm Password box.\n");
        }else if (!secondValidity) {
            checkString.append("Your Confirm Password does not fulfill the requirements.\n");
        }

        if (!password.equals(confirmUser)) {
            checkString.append("Password and Confirm Password do not match.\n");
        }

//        // Username validation
//        if (platform.isEmpty()) {
//            checkString.append("Please fill out the Name of Service box.\n");
//        }
//        if (username.isEmpty()) {
//            checkString.append("Please fill out the Username box.\n");
//        }
//        if (password.isEmpty()) {
//            checkString.append("Please fill out the Password box.\n");
//        }
//        if (confirmUser.isEmpty()) {
//            checkString.append("Please fill out the Confirm Password box.\n");
//        }
//        if (!password.equals(confirmUser)) {
//            checkString.append("Password and Confirm Password do not match.\n");
//        }


        // Show alert if any issues were found
        if (!checkString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Adding Service Failed");
            alert.setHeaderText(null);
            alert.setContentText(checkString.toString());
            alert.showAndWait();
        } else if (password.equals(confirmUser)) {
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
    @FXML
    private void giveHelp(ActionEvent event) {
        // Create a help dialog describing valid password rules
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
