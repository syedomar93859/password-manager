package com.cpsc329pm;

import com.cpsc329pm.DataStorage;
import com.cpsc329pm.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StoredServicesController {
    @FXML private TableView<Data> tableView;
    @FXML private TableColumn<Data, String> colService;
    @FXML private TableColumn<Data, String> colUsername;
    @FXML private TableColumn<Data, String> colPassword;

    private final DataStorage dataStorage = new DataStorage(); // Assuming you have this class

    @FXML
    public void initialize() {
        colService.setCellValueFactory(new PropertyValueFactory<>("platform"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        loadDataIntoTable();
    }

    private void loadDataIntoTable() {
        dataStorage.loadFromJSON(); // Load data from file
        List<Data> dataList = dataStorage.getAllData(); // Get list of data

        ObservableList<Data> observableList = FXCollections.observableArrayList(dataList);
        tableView.setItems(observableList);
    }
}
