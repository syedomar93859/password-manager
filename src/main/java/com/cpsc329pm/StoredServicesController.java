package com.cpsc329pm;

import com.cpsc329pm.DataStorage;
import com.cpsc329pm.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the "Stored Services" scene.
 * Responsible for displaying saved service credentials,
 * handling user navigation, and managing stored data operations like deletion.
 */
public class StoredServicesController {

    private int num = 0; // Counter (currently unused for logic)

    @FXML private TableView<Data> tableView; // Table to display stored data
    @FXML private TableColumn<Data, String> colService; // Column for service/platform
    @FXML private TableColumn<Data, String> colUsername; // Column for username
    @FXML private TableColumn<Data, String> colPassword; // Column for decrypted password
    @FXML private TableColumn<Data, String> colNumber; // Column for row numbering

    // DataStorage instance initialized with the master login file
    private final DataStorage dataStorage = new DataStorage("master_login.json");

    /**
     * Initializes the table view and populates it with user data.
     * Called automatically by JavaFX when the FXML is loaded.
     */
    @FXML
    public void initialize() {
        // Set value factories to map Data fields to columns
        colService.setCellValueFactory(new PropertyValueFactory<>("platform"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Decrypt password before displaying
        colPassword.setCellValueFactory(cellData -> {
            String encrypted = cellData.getValue().getPassword();
            String decrypted = Encryption.decrypt(encrypted); // Replace with your actual decrypt logic
            return new javafx.beans.property.SimpleStringProperty(decrypted);
        });

        // Display row number based on index in table
        colNumber.setCellValueFactory(cellData -> {
            int index = tableView.getItems().indexOf(cellData.getValue()) + 1;
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(index));
        });

        // Load data into the table on initialization
        loadDataIntoTable();
    }

    /**
     * Loads data from storage into the table view.
     * Converts the list of data into an observable list for the table.
     */
    @FXML
    private void loadDataIntoTable() {
        num++; // Incrementing counter (not currently used)

        List<Data> dataList = dataStorage.getAllData(); // Get all stored data

        // Convert list to observable list for JavaFX table
        ObservableList<Data> observableList = FXCollections.observableArrayList(dataList);

        // Set items to display in the table
        tableView.setItems(observableList);
    }

    /**
     * Handles the action of returning to the main page.
     * Loads the ReformattedMainPage scene and passes the current username to it.
     *
     * @param event the action event triggered by the back button
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Get current window stage from event
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the main page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReformattedMainPage.fxml"));
            Parent root = loader.load();

            // Get the controller to pass the current user
            MainPageController controller = loader.getController();
            String currentUser = UserSession.getUsername();
            controller.setCurrUsername(currentUser);  // Pass username to controller

            // Set up and show the new scene
            Stage stage = new Stage();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the previous scene
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print error stack trace if loading fails
        }
    }

    /**
     * Handles deleting all stored services.
     * Prompts the user with a confirmation dialog before clearing all data.
     *
     * @param event the action event triggered by the delete all button
     */
    @FXML
    private void deleteAllHandling(ActionEvent event) {
        DataStorage storage = new DataStorage("master_login.json");

        // Check if there is data to delete
        if (!storage.getAllData().isEmpty()) {
            // Show confirmation alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Everything");
            alert.setHeaderText("Warning");
            alert.setContentText("Are you sure you want to delete all the services, including any you are using to login to this password manager?");

            // Set up custom buttons
            ButtonType yesButton = new ButtonType("Delete All");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButton, cancelButton);

            // Show alert and get user response
            Optional<ButtonType> result = alert.showAndWait();

            // If user confirmed deletion
            if (result.isPresent() && result.get() == yesButton) {
                storage.clearData(); // Clear all data from storage

                // Show confirmation message
                Alert confirm = new Alert(Alert.AlertType.INFORMATION);
                confirm.setTitle("Success");
                confirm.setHeaderText(null);
                confirm.setContentText("All data has been deleted. Go back to the main page and click View All Services to see that everything has been deleted.");
                confirm.showAndWait();
            }
        } else {
            // Alert if there’s nothing to delete
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deleting All Services Failed");
            alert.setHeaderText(null);
            alert.setContentText("There are currently no services to delete.");
            alert.showAndWait();
        }
    }

}
