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

public class DeleteServiceSceneController {

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField serviceField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField confirmUsernameField;

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
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
    private void handleDelete(ActionEvent event) {
        String platform = serviceField.getText();
        String username = usernameField.getText();
        String confirmUser = confirmUsernameField.getText();

        if (username.equals(confirmUser)) {
            try {
                DataStorage ds = new DataStorage("master_login.json");

                // Check if the service exists
                Data existingData = ds.getData(platform, username);
                if (existingData == null) {
                    // If the service doesn't exist, show error alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Delete Service");
                    alert.setHeaderText("Error");
                    alert.setContentText("No service found with the provided platform and username.");
                    alert.showAndWait();
                    return; // Exit method early if service doesn't exist
                }

                // If service exists, proceed with deletion
                ds.deleteData(platform, username);
                ds.saveToJSON();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Service");
                alert.setHeaderText("Success");
                alert.setContentText("Service deleted successfully.");
                alert.showAndWait();

                // Reload the main page
                Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // If username does not match the confirm username field
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Service");
            alert.setHeaderText("Error");
            alert.setContentText("Username does not match Confirm Username.");
            alert.showAndWait();
        }
    }
}
