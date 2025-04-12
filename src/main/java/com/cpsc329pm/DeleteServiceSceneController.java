package com.cpsc329pm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for handling the Delete Service scene in a JavaFX application.
 * Allows the user to delete a service entry (platform + username) after confirming input.
 */
public class DeleteServiceSceneController {

    @FXML
    private Button backButton; // Button to return to main page

    @FXML
    private Button deleteButton; // Button to initiate deletion process

    @FXML
    private TextField serviceField; // Input field for service/platform name

    @FXML
    private TextField usernameField; // Input field for username

    @FXML
    private TextField confirmUsernameField; // Input field to confirm username

    /**
     * Handles the event triggered when the back button is clicked.
     * Navigates back to the main page while maintaining the current user session.
     *
     * @param event The action event from the back button.
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Get the current stage and load the main page FXML
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReformattedMainPage.fxml"));
            Parent root = loader.load();

            // Pass the current username to the main page controller
            MainPageController controller = loader.getController();
            String currentUser = UserSession.getUsername();
            controller.setCurrUsername(currentUser);

            // Switch to the main page scene
            Stage stage = new Stage();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the delete service window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Log any errors during loading
        }
    }

    /**
     * Handles the delete button action.
     * Validates user input and deletes the corresponding service if found.
     *
     * @param event The action event from the delete button.
     */
    @FXML
    private void handleDelete(ActionEvent event) {
        String platform = serviceField.getText().trim(); // Read and trim service name
        String username = usernameField.getText().trim(); // Read and trim username
        String confirmUser = confirmUsernameField.getText().trim(); // Read and trim confirmation field

        StringBuilder checkString = new StringBuilder(); // StringBuilder for error message accumulation

        // Input validation
        if (platform.isEmpty()) {
            checkString.append("Please fill out the Name of Service box.\n");
        }
        if (username.isEmpty()) {
            checkString.append("Please fill out the Username box.\n");
        }
        if (confirmUser.isEmpty()) {
            checkString.append("Please fill out the Confirm Username box.\n");
        }
        if (!username.equals(confirmUser)) {
            checkString.append("Username and Confirm Username do not match.\n");
        }

        // If there are input errors, show alert and exit
        if (!checkString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deleting Service Failed");
            alert.setHeaderText(null);
            alert.setContentText(checkString.toString());
            alert.showAndWait();
        } else if (username.equals(confirmUser)) {
            try {
                DataStorage ds = new DataStorage("master_login.json"); // Load data from file

                // Check if data entry exists
                Data existingData = ds.getData(platform, username);
                if (existingData == null) {
                    // Show error if entry doesn't exist
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Delete Service");
                    alert.setHeaderText("Error");
                    alert.setContentText("No service found with the provided platform and username.");
                    alert.showAndWait();
                    return; // Exit early
                }

                // If service exists, delete and save
                ds.deleteData(platform, username);
                ds.saveToJSON();

                // Show confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Service");
                alert.setHeaderText("Success");
                alert.setContentText("Service deleted successfully.");
                alert.showAndWait();

                // Reload main page after deletion
                Parent root = FXMLLoader.load(getClass().getResource("ReformattedMainPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace(); // Log exceptions
            }
        }

//        } else {
//            // If username does not match the confirm username field
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Delete Service");
//            alert.setHeaderText("Error");
//            alert.setContentText("Username does not match Confirm Username.");
//            alert.showAndWait();
//        }
    }
}
