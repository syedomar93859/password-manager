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

public class StoredServicesController {

    private int num = 0;

    @FXML private TableView<Data> tableView;
    @FXML private TableColumn<Data, String> colService;
    @FXML private TableColumn<Data, String> colUsername;
    @FXML private TableColumn<Data, String> colPassword;
    @FXML private TableColumn<Data, String> colNumber;

    private final DataStorage dataStorage = new DataStorage("master_login.json"); // Assuming you have this class

    @FXML
    public void initialize() {
        // Set cell value factories for actual data columns
        colService.setCellValueFactory(new PropertyValueFactory<>("platform"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(cellData -> {
            String encrypted = cellData.getValue().getPassword();
            String decrypted = Encryption.decrypt(encrypted); // Replace with your actual decrypt logic
            return new javafx.beans.property.SimpleStringProperty(decrypted);
        });

        // Set a custom cell value factory for row numbers
        colNumber.setCellValueFactory(cellData -> {
            int index = tableView.getItems().indexOf(cellData.getValue()) + 1;
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(index));
        });

        loadDataIntoTable();
    }

    @FXML
    private void loadDataIntoTable() {
        num++;
        List<Data> dataList = dataStorage.getAllData();

        // DEBUG: Check if data is retrieved
//        System.out.println("Retrieved Data: " + dataList);

        ObservableList<Data> observableList = FXCollections.observableArrayList(dataList);

        // DEBUG: Check if ObservableList is not empty
//        System.out.println("ObservableList Size: " + observableList.size());

        tableView.setItems(observableList);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReformattedMainPage.fxml"));
            //  Now get the controller from the loader


            Parent root = loader.load();

            MainPageController controller = loader.getController();
            String currentUser = UserSession.getUsername();
            controller.setCurrUsername(currentUser);  // Pass username to controller
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
    private void deleteAllHandling(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete Everything");
        alert.setHeaderText("Warning");
        alert.setContentText("Are you sure you want to delete all the services, including any you are using to login to this password manager?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);

        // Show the alert and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            DataStorage storage = new DataStorage("master_login.json");
            storage.clearData();

            Alert confirm = new Alert(Alert.AlertType.INFORMATION);
            confirm.setTitle("Success");
            confirm.setHeaderText(null);
            confirm.setContentText("All data has been deleted. Go back to the main page and click View All Services to see that everything has been deleted.");
            confirm.showAndWait();
        }

    }

}
