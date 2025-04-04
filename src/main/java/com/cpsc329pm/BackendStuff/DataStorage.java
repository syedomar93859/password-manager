package com.cpsc329pm.BackendStuff;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;

public class DataStorage {

    private List<Data> data; // List to store login information
    private String filename; // File to store/retrieve data
    private ObjectMapper mapper; // Jackson ObjectMapper for JSON processing

    /**
     * Constructor initializes the storage system with a specified filename.
     * It also attempts to load existing data from the file.
     *
     * @param filename The name of the JSON file used for storage.
     */
    public DataStorage(String filename) {
        this.filename = filename;
        this.mapper = new ObjectMapper();
        this.data = new ArrayList<>();
        loadFromJSON(); // Load existing data if available
    }

    /**
     * Saves the current list of login information to the JSON file.
     * If an error occurs during saving, it prints an error message.
     */
    public void saveToJSON() {
        try {
            mapper.writeValue(new File(filename), data);
        } catch (IOException e) {
            System.out.println("Error while saving data: " + e.getMessage());
        }
    }

    /**
     * Loads login information from the JSON file if it exists.
     * If an error occurs during loading, it prints an error message.
     */
    public void loadFromJSON() {
        File file = new File(filename);
        if (file.exists()) {
            try {
                Data[] loadedData = mapper.readValue(file, Data[].class);
                data = new ArrayList<>();
                Collections.addAll(data, loadedData); // Convert array to list
            } catch (IOException e) {
                System.out.println("Error loading data from file: " + filename);
            }
        }
    }

    /**
     * Adds new login information (platform, username, and hashed password) to storage.
     *
     * @param platform The name of the platform for the login credentials.
     * @param username The username associated with the login.
     * @param password The plaintext password (hashed before storing).
     */
    public void addData(String platform, String username, String password) {
        data.add(new Data(platform, username, Encryption.HashingSalting(password))); // Hash and store password
        saveToJSON(); // Save updated data to file
    }
}
