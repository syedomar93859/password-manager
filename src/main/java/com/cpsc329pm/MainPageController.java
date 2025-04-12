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
import java.util.Optional;

/**
 * Controller for the main page of the application.
 * Handles navigation to different scenes like adding, deleting, editing services,
 * and viewing all services.
 */
public class MainPageController {

    @FXML private Button viewButton;

    @FXML
    private Label currUsername;

    /**
     * Initializes the controller by setting the current username in the label
     * when the scene is loaded.
     */
    @FXML
    public void initialize() {
        // Get the username from the session and set it to the label
        String username = UserSession.getUsername();
        if (username != null && currUsername != null) {
            currUsername.setText(username); // Set the current username
        }
    }

    /**
     * Sets the current username in the label on the main page.
     *
     * @param username the username to display in the label
     */
    public void setCurrUsername(String username) {
        if (currUsername != null) {
            currUsername.setText(username); // Update the label with the username
        } else {
            System.err.println("currUsername TextField is null!"); // Log error if label is null
        }
    }

    /**
     * Handles the event when the "Add Service" button is clicked.
     * Loads the AddServiceScene and opens it in a new window.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    private void handleAddService(ActionEvent event) {
        try {
            // Get the current stage (main page)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the AddServiceScene.fxml file and create a new root node
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddServiceScene.fxml"));
            Parent root = loader.load();

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Add New Service"); // Set window title
            stage.setScene(new Scene(root)); // Set the loaded scene
            stage.show(); // Show the new window

            // Close the current main page window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print error if scene loading fails
        }
    }

    /**
     * Handles the event when the "Delete Service" button is clicked.
     * Loads the DeleteServiceScene and opens it in a new window.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    private void handleDeleteService(ActionEvent event) {
        try {
            // Get the current stage (main page)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the DeleteServiceScene.fxml file and create a new root node
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteServiceScene.fxml"));
            Parent root = loader.load();

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Delete A Service"); // Set window title
            stage.setScene(new Scene(root)); // Set the loaded scene
            stage.show(); // Show the new window

            // Close the current main page window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print error if scene loading fails
        }
    }

    /**
     * Handles the event when the "Edit Service" button is clicked.
     * Loads the EditInfoScene and opens it in a new window.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    private void handleEditService(ActionEvent event) {
        try {
            // Get the current stage (main page)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the EditInfoScene.fxml file and create a new root node
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditInfoScene.fxml"));
            Parent root = loader.load();

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Edit Your Services"); // Set window title
            stage.setScene(new Scene(root)); // Set the loaded scene
            stage.show(); // Show the new window

            // Close the current main page window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print error if scene loading fails
        }
    }

    /**
     * Handles the event when the "View All Services" button is clicked.
     * Loads the StoredServices scene and opens it in a new window.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    private void viewAllServices(ActionEvent event) {
        try {
            // Get the current stage (main page)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the StoredServices.fxml file and create a new root node
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StoredServices.fxml"));
            Parent root = loader.load();

            // Create a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("View All Your Services"); // Set window title
            stage.setScene(new Scene(root)); // Set the loaded scene
            stage.show(); // Show the new window

            // Close the current main page window
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print error if scene loading fails
        }
    }

    /**
     * Handles the event when the "Back" (logout) button is clicked.
     * Shows a confirmation alert asking if the user is sure they want to log out.
     * If confirmed, logs the user out and navigates to the login page.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    private void handleBack(ActionEvent event) {
        // Create a warning alert for logout confirmation
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Logout Confirmation"); // Set alert title
        alert.setHeaderText("Warning"); // Set alert header
        alert.setContentText("Are you sure you want to logout? You will go to the login page.");

        // Define the button types (Yes and Cancel)
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);

        // Show the alert and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        // If user confirms, proceed to log out
        if (result.isPresent() && result.get() == yesButton) {
            try {
                // Get the current stage (main page)
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Load the LoginScene.fxml and create a new root node
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
                Parent root = loader.load();

                // Create a new stage (login window)
                Stage stage = new Stage();
                stage.setTitle("Login Page"); // Set window title
                stage.setScene(new Scene(root)); // Set the loaded scene
                stage.show(); // Show the new login window

                // Close the current main page window
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace(); // Print error if scene loading fails
            }
        }
    }
}
