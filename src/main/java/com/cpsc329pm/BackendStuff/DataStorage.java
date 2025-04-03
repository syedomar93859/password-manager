package com.cpsc329pm.BackendStuff;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;

public class DataStorage {

    private List<Data> data;
    private String filename;
    private ObjectMapper mapper;

    public DataStorage(String filename) {
        this.filename = filename;
        this.mapper = new ObjectMapper();
        this.data = new ArrayList<>();
        loadFromJSON();
    }

    /**
     * Save login information to the storage json file
     */
    public void saveToJSON() {
        try {
            mapper.writeValue(new File(filename), data);
        } catch (IOException e) {
            System.out.println("Error while saving data: " + e.getMessage());
        }
    }

    /**
     * Load login information from the storage json file
     */
    public void loadFromJSON() {
        File file = new File(filename);
        if (file.exists()) {
            try {
                Data[] loadedData =  mapper.readValue(file, Data[].class);
                data = new ArrayList<>();
                Collections.addAll(data, loadedData); // originally for loop, intellij recommended
            } catch (IOException e) {
                System.out.println("Error loading data from file: " + filename);;
            }
        }
    }

    public void addData(String platform, String username, String password) {
        data.add(new Data(platform, username, Encryption.HashingSalting(password)));
        saveToJSON();
    }
}