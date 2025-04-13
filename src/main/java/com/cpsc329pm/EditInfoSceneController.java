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
 * Controller class for handling the Edit Info scene in the JavaFX application.
 * Allows editing and deleting of user service credentials (username, password).
 */
public class EditInfoSceneController {

    @FXML
    private Button backButton; // Button to return to the main page

    @FXML
    private Button editButton; // Button to confirm editing of info

    @FXML
    private TextField serviceNameField; // Field to enter service/platform name

    @FXML
    private TextField usernameField; // Field to enter current username

    @FXML
    private TextField editUsernameField; // Field to enter new username

    @FXML
    private PasswordField editPasswordField; // Field to enter new password

    /**
     * Handles the Edit button action. Validates input and updates credentials
     * if the entry exists in the data storage.
     *
     * @param event ActionEvent from the edit button click
     */
    @FXML
    private void handleEdit(ActionEvent event) {
        String platform = serviceNameField.getText().trim(); // Read platform name
        String username = usernameField.getText().trim(); // Read old username
        String newUsername = editUsernameField.getText().trim(); // Read new username
        String newPassword = editPasswordField.getText().trim(); // Read new password


        StringBuilder checkString = new StringBuilder(); // Collects input error messages


        // Username validation
        if (platform.isEmpty()) {
            checkString.append("Please fill out the Service Name for Credential Changes box.\n");
        }

        if (username.isEmpty()) {
            checkString.append("Please fill out the Username for Credential Changes box.\n");
        }else if(username.length() < 8){
            checkString.append("Username for Credential Changes must be at least 8 characters long.\n");
        }

        if (newUsername.isEmpty()) {
            checkString.append("Please fill out the New Username box.\n");
        }else if(newUsername.length() < 8){
            checkString.append("New Username must be at least 8 characters long.\n");
        }

        // Password validation
        MasterLogin masterLogin = new MasterLogin();
        masterLogin.registerWithConfirmation(username, newUsername, newPassword, newPassword);
        boolean firstValidity = masterLogin.isValidPassword(newPassword);

        if (newPassword.isEmpty()) {
            checkString.append("Please fill out the New Password box.\n");
        }else if (!firstValidity){
            checkString.append("Your New Password does not fulfill the requirements.\n");
        }




        // Show error alert if validation fails
        if (!checkString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deleting Service Failed");
            alert.setHeaderText(null);
            alert.setContentText(checkString.toString());
            alert.showAndWait();
        } else
            try {
                DataStorage ds = new DataStorage("master_login.json"); // Load data storage

                // Check if the entry exists
                Data existingData = ds.getData(platform, username);
                if (existingData == null) {
                    // If not found, show error and exit
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Edit Info");
                    alert.setHeaderText("Error");
                    alert.setContentText("No service found with the provided Service Name for Credential Changes and Username for Credential Changes.");
                    alert.showAndWait();
                    return;
                }

                // Update and save the new credentials
                ds.updateData(platform, username, newUsername, newPassword);
                ds.saveToJSON();

                // Show success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit Info");
                alert.setHeaderText("Success");
                alert.setContentText("Data updated successfully.");
                alert.showAndWait();

                // Return to main page
                Parent root = FXMLLoader.load(getClass().getResource("ReformattedMainPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace(); // Log any unexpected errors
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Edit Info");
                alert.setHeaderText("Error");
                alert.setContentText("Failed to update data.");
                alert.showAndWait();
            }
    }

    /**
     * Handles the Delete action by removing the corresponding platform/username entry
     * from the data storage and saving the update.
     *
     * @param event ActionEvent from the delete button
     */
    @FXML
    private void handleDelete(ActionEvent event) {
        String platform = serviceNameField.getText(); // Read platform to delete
        String username = editUsernameField.getText(); // Read username to delete

        try {
            DataStorage ds = new DataStorage("master_login.json"); // Load data file
            ds.deleteData(platform, username); // Delete entry
            ds.saveToJSON(); // Save the updated data

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Info");
            alert.setHeaderText("Success");
            alert.setContentText("Data deleted successfully.");
            alert.showAndWait();

            // Redirect to main page
            Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); // Log error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Info");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to delete data.");
            alert.showAndWait();
        }
    }

    /**
     * Handles navigation back to the main page while maintaining the current user's session.
     *
     * @param event ActionEvent from the back button click
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Get current window and load the main page FXML
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReformattedMainPage.fxml"));
            Parent root = loader.load();

            // Pass username to the controller
            MainPageController controller = loader.getController();
            String currentUser = UserSession.getUsername();
            controller.setCurrUsername(currentUser);

            // Show main page and close current
            Stage stage = new Stage();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root));
            stage.show();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace(); // Log any loading errors
        }
    }

    @FXML
    private void provideHelp(ActionEvent event) {
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
