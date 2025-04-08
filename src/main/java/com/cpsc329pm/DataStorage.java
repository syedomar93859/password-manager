package com.cpsc329pm;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;


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
        if (!isDirty) return;
        
        try {
            List<Data> dataList = new ArrayList<>(dataMap.values());
            mapper.writeValue(new File(filename), dataList);
            isDirty = false;
        } catch (IOException e) {
            logger.severe("Error while saving data: " + e.getMessage());
            throw new StorageException("Failed to save data", e);
        }
    }

    public void loadFromJSON() {
        File file = new File(filename);
        if (!file.exists()) return;

        try {
            Data[] loadedData = mapper.readValue(file, Data[].class);
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

    public void updateData(String platform, String username, String newPassword) {
        String key = generateKey(platform, username);
        if (!dataMap.containsKey(key)) {
            throw new EntryNotFoundException("No entry found for platform: " + platform + ", username: " + username);
        }
        
        dataMap.put(key, new Data(platform, username, Encryption.HashingSalting(newPassword)));
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
