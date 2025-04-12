package com.cpsc329pm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import com.fasterxml.jackson.databind.*;

/**
 * This class handles the storage and retrieval of master login data.
 * It manages saving and loading master login credentials to/from a file in JSON format.
 */
public class MasterLoginStorage {
    private static final Logger logger = Logger.getLogger(MasterLoginStorage.class.getName()); // Logger for logging errors and events
    private static final String MASTER_LOGIN_FILE = "master_login.json"; // File path to store the master login data
    private final ObjectMapper mapper; // ObjectMapper for JSON processing
    //    private Data masterData; // The master data object (commented out, as only a list is used now)
    private List<Data> allMasterData = new ArrayList<>(); // List to hold all master login data

    /**
     * Constructor initializes the ObjectMapper and loads existing master login data from a file.
     */
    public MasterLoginStorage() {
        this.mapper = new ObjectMapper(); // Initialize ObjectMapper for JSON operations
        loadMasterData(); // Load the existing master data from the file
    }

    /**
     * Saves the provided master login data to a file in JSON format.
     * @param master The master login data to be saved.
     * @throws StorageException if an error occurs during saving.
     */
    public void saveMasterData(Data master) {
        try {
            // Write the list of all master data to the file in JSON format
            mapper.writeValue(new File(MASTER_LOGIN_FILE), allMasterData);
        } catch (IOException e) {
            // Log the error and throw a custom exception
            logger.severe("Error while saving master login data: " + e.getMessage());
            throw new StorageException("Failed to save master login data", e);
        }
    }

    /**
     * Loads the master login data from the JSON file.
     * If the file does not exist, it does nothing.
     * @throws StorageException if an error occurs during loading.
     */
    private void loadMasterData() {
        File file = new File(MASTER_LOGIN_FILE); // Create a File object for the master login file
        if (!file.exists()) return; // If the file does not exist, return early

        try {
            // Deserialize JSON into a list of Data objects
            allMasterData = mapper.readValue(file,
                    mapper.getTypeFactory().constructCollectionType(List.class, Data.class));

            // Pick the first entry if available (commented out code for single entry support)
//            this.masterData = masterDataList.isEmpty() ? null : masterDataList.get(0);
        } catch (IOException e) {
            // Log the error and throw a custom exception
            logger.severe("Error loading master login data: " + e.getMessage());
            throw new StorageException("Failed to load master login data", e);
        }
    }

    /**
     * Returns a list of all master login data loaded from the file.
     * @return A list containing all master login data.
     */
    public List<Data> getAllMasterData(){
        return allMasterData; // Return the list of all master data
    }

//    /**
//     * Validates the master login credentials against stored data (currently commented out).
//     * @param username The username to validate.
//     * @param password The password to validate.
//     * @return true if credentials are valid, false otherwise.
//     */
//    public boolean validateMasterLogin(String username, String password) {
//        if (masterData == null) return false; // If no master data is loaded, return false
//        return masterData.getUsername().equals(username) && // Check if the username matches
//               Encryption.VerifyPassword(password, masterData.getPassword()); // Verify the password
//    }

    /**
     * Custom exception class to handle errors during storage operations.
     */
    public static class StorageException extends RuntimeException {
        /**
         * Constructs a new StorageException with the specified detail message and cause.
         * @param message The detail message.
         * @param cause The cause of the exception.
         */
        public StorageException(String message, Throwable cause) {
            super(message, cause); // Pass the message and cause to the superclass
        }
    }
}
