package com.cpsc329pm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class EditInfoSceneController {

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField serviceNameField;

    @FXML
    private TextField editUsernameField;

    @FXML
    private PasswordField editPasswordField;

    @FXML
    private void handleEdit(ActionEvent event) {
        String platform = serviceNameField.getText();
        String username = editUsernameField.getText();
        String newPassword = editPasswordField.getText();
        try {
            DataStorage ds = new DataStorage("master_login.json");
            ds.updateData(platform, username, newPassword);
            ds.saveToJSON();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit Info");
            alert.setHeaderText("Success");
            alert.setContentText("Data updated successfully.");
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Edit Info");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to update data.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        String platform = serviceNameField.getText();
        String username = editUsernameField.getText();
        try {
            DataStorage ds = new DataStorage("master_login.json");
            ds.deleteData(platform, username);
            ds.saveToJSON();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Info");
            alert.setHeaderText("Success");
            alert.setContentText("Data deleted successfully.");
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Info");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to delete data.");
            alert.showAndWait();
        }
    }

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
}

