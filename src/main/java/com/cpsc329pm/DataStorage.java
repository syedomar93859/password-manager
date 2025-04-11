package com.cpsc329pm;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.*;

public class DataStorage {
    private static final Logger logger = Logger.getLogger(DataStorage.class.getName());
    private final Map<String, Data> dataMap;
    private final String filename;
    private final ObjectMapper mapper;
    private boolean isDirty;

    public DataStorage(String filename) {
        this.filename = filename;
        this.mapper = new ObjectMapper();
        this.dataMap = new ConcurrentHashMap<>();
        this.isDirty = false;
        loadFromJSON();
    }

    public void saveToJSON() {
        try {
            List<Data> dataList = new ArrayList<>(dataMap.values()); // Convert Map to List
            mapper.writeValue(new File(filename), dataList); // Write as JSON array
        } catch (IOException e) {
            logger.severe("Error saving data to file: " + filename);
            throw new StorageException("Failed to save data", e);
        }
    }


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


    public void addData(String platform, String username, String password) {
        String key = generateKey(platform, username);
        if (dataMap.containsKey(key)) {
            throw new DuplicateEntryException("Entry already exists for platform: " + platform + ", username: " + username);
        }

        dataMap.put(key, new Data(platform, username, Encryption.HashingSalting(password)));
        isDirty = true;
    }

    public Data getData(String platform, String username) {
        return dataMap.get(generateKey(platform, username));
    }

    public void updateData(String platform, String username, String newUsername, String newPassword) {
        String key = generateKey(platform, newUsername);

//        if (!dataMap.containsKey(key)) {
//            throw new EntryNotFoundException("No entry found for platform: " + platform + ", username: " + newUsername);
//        }
        deleteData(platform, username);

        dataMap.put(key, new Data(platform, newUsername, Encryption.HashingSalting(newPassword)));
        isDirty = true;
    }

    public void deleteData(String platform, String username) {
        if (dataMap.remove(generateKey(platform, username)) != null) {
            isDirty = true;
        }
    }

    public List<Data> getAllData() {
        return new ArrayList<>(dataMap.values());
    }

    private String generateKey(String platform, String username) {
        return platform + ":" + username;
    }

    // Custom exceptions
    public static class StorageException extends RuntimeException {
        public StorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class DuplicateEntryException extends RuntimeException {
        public DuplicateEntryException(String message) {
            super(message);
        }
    }

    public static class EntryNotFoundException extends RuntimeException {
        public EntryNotFoundException(String message) {
            super(message);
        }
    }
}
