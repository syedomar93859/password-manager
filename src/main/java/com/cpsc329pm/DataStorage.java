package com.cpsc329pm;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.*;

/**
 * This class handles storage and retrieval of user service data (platform, username, password).
 * It supports JSON serialization/deserialization using Jackson and manages data in memory with a map.
 */
public class DataStorage {

    // Logger to log errors and important events
    private static final Logger logger = Logger.getLogger(DataStorage.class.getName());

    // Thread-safe map for storing Data objects keyed by "platform:username"
    private final Map<String, Data> dataMap;

    // Path to the JSON file
    private final String filename;

    // Jackson object mapper for reading/writing JSON
    private final ObjectMapper mapper;

    // Indicates whether the data has been modified
    private boolean isDirty;

    /**
     * Constructor to initialize DataStorage with a file path.
     *
     * @param filename The path of the JSON file for persistence.
     */
    public DataStorage(String filename) {
        this.filename = filename;
        this.mapper = new ObjectMapper();
        this.dataMap = new ConcurrentHashMap<>();
        this.isDirty = false;
        loadFromJSON(); // Load existing data if available
    }

    /**
     * Saves the current dataMap to the JSON file.
     * Converts the map values to a list and serializes it.
     */
    public void saveToJSON() {
        try {
            List<Data> dataList = new ArrayList<>(dataMap.values()); // Convert Map to List
            mapper.writeValue(new File(filename), dataList); // Write as JSON array
        } catch (IOException e) {
            logger.severe("Error saving data to file: " + filename);
            throw new StorageException("Failed to save data", e);
        }
    }

    /**
     * Loads data from the JSON file if it exists.
     * Parses the file and fills the dataMap with the entries.
     */
    public void loadFromJSON() {
        File file = new File(filename);
        if (!file.exists()) return; // If the file doesn't exist, nothing to load

        try {
            // Read the JSON file as an array
            List<Data> loadedData = mapper.readValue(file,
                    mapper.getTypeFactory().constructCollectionType(List.class, Data.class));

            // Clear and update the data map
            dataMap.clear();
            for (Data data : loadedData) {
                dataMap.put(generateKey(data.getPlatform(), data.getUsername()), data);
            }
        } catch (IOException e) {
            logger.severe("Error loading data from file: " + filename);
            throw new StorageException("Failed to load data", e);
        }
    }

    /**
     * Adds new data to the storage after checking for duplicates.
     *
     * @param platform Platform name.
     * @param username Username associated with the service.
     * @param password Password to be encrypted and stored.
     */
    public void addData(String platform, String username, String password) {
        String key = generateKey(platform, username);
        if (dataMap.containsKey(key)) {
            throw new DuplicateEntryException("Entry already exists for platform: " + platform + ", username: " + username);
        }

        dataMap.put(key, new Data(platform, username, Encryption.encrypt(password)));
        isDirty = true;
    }

    /**
     * Retrieves a specific Data object based on platform and username.
     *
     * @param platform The platform name.
     * @param username The username.
     * @return Data object if found, otherwise null.
     */
    public Data getData(String platform, String username) {
        return dataMap.get(generateKey(platform, username));
    }

    /**
     * Updates existing data by deleting the old entry and creating a new one with updated credentials.
     *
     * @param platform     The platform name.
     * @param username     The old username.
     * @param newUsername  The new username.
     * @param newPassword  The new password to be stored.
     */
    public void updateData(String platform, String username, String newUsername, String newPassword) {
        String key = generateKey(platform, newUsername);

//        if (!dataMap.containsKey(key)) {
//            throw new EntryNotFoundException("No entry found for platform: " + platform + ", username: " + newUsername);
//        }

        deleteData(platform, username); // Remove old entry

        // Add updated entry
        dataMap.put(key, new Data(platform, newUsername, Encryption.encrypt(newPassword)));
        isDirty = true;
    }

    /**
     * Deletes a data entry based on platform and username.
     *
     * @param platform The platform name.
     * @param username The username.
     */
    public void deleteData(String platform, String username) {
        if (dataMap.remove(generateKey(platform, username)) != null) {
            isDirty = true;
        }
    }

    /**
     * Clears all stored data and immediately saves the empty list to file.
     */
    public void clearData() {
        dataMap.clear();
        saveToJSON(); // Save empty state to file
    }

    /**
     * Gets a list of all stored data entries.
     *
     * @return List of Data objects.
     */
    public List<Data> getAllData() {
        return new ArrayList<>(dataMap.values());
    }

    /**
     * Generates a unique key for a given platform and username.
     *
     * @param platform Platform name.
     * @param username Username.
     * @return Combined key in format "platform:username".
     */
    private String generateKey(String platform, String username) {
        return platform + ":" + username;
    }

    // -------------------- Custom Exception Classes --------------------

    /**
     * Exception thrown when saving or loading from storage fails.
     */
    public static class StorageException extends RuntimeException {
        public StorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception thrown when attempting to add a duplicate entry.
     */
    public static class DuplicateEntryException extends RuntimeException {
        public DuplicateEntryException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when trying to update or access an entry that does not exist.
     */
    public static class EntryNotFoundException extends RuntimeException {
        public EntryNotFoundException(String message) {
            super(message);
        }
    }
}
