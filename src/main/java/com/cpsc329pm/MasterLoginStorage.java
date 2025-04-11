package com.cpsc329pm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import com.fasterxml.jackson.databind.*;

public class MasterLoginStorage {
    private static final Logger logger = Logger.getLogger(MasterLoginStorage.class.getName());
    private static final String MASTER_LOGIN_FILE = "master_login.json";
    private final ObjectMapper mapper;
//    private Data masterData;
    private List<Data> allMasterData = new ArrayList<>();

    public MasterLoginStorage() {
        this.mapper = new ObjectMapper();
        loadMasterData();
    }

    public void saveMasterData(Data master) {
        try {
            mapper.writeValue(new File(MASTER_LOGIN_FILE), allMasterData);
        } catch (IOException e) {
            logger.severe("Error while saving master login data: " + e.getMessage());
            throw new StorageException("Failed to save master login data", e);
        }
    }

    private void loadMasterData() {
        File file = new File(MASTER_LOGIN_FILE);
        if (!file.exists()) return;

        try {
            // Deserialize JSON into a list of Data objects
            allMasterData = mapper.readValue(file,
                    mapper.getTypeFactory().constructCollectionType(List.class, Data.class));

            // Pick the first entry if available
//            this.masterData = masterDataList.isEmpty() ? null : masterDataList.get(0);
        } catch (IOException e) {
            logger.severe("Error loading master login data: " + e.getMessage());
            throw new StorageException("Failed to load master login data", e);
        }
    }

    public List<Data> getAllMasterData(){
        return allMasterData;
    }

//    public boolean validateMasterLogin(String username, String password) {
//        if (masterData == null) return false;
//        return masterData.getUsername().equals(username) &&
//               Encryption.VerifyPassword(password, masterData.getPassword());
//    }

    public static class StorageException extends RuntimeException {
        public StorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
} 