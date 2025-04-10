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

    private final DataStorage dataStorage = new DataStorage("master_login.json"); // Assuming you have this class

    @FXML
    public void initialize() {
        colService.setCellValueFactory(new PropertyValueFactory<>("platform"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        loadDataIntoTable();
    }


    private void loadDataIntoTable() {
        List<Data> dataList = dataStorage.getAllData();

        // DEBUG: Check if data is retrieved
        System.out.println("Retrieved Data: " + dataList);

        ObservableList<Data> observableList = FXCollections.observableArrayList(dataList);

        // DEBUG: Check if ObservableList is not empty
        System.out.println("ObservableList Size: " + observableList.size());

        tableView.setItems(observableList);
    }

}
