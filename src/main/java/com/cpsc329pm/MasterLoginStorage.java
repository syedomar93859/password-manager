package com.cpsc329pm;

import java.io.*;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.*;

public class MasterLoginStorage {
    private static final Logger logger = Logger.getLogger(MasterLoginStorage.class.getName());
    private static final String MASTER_LOGIN_FILE = "master_login.json";
    private final ObjectMapper mapper;
    private Data masterData;

    public MasterLoginStorage() {
        this.mapper = new ObjectMapper();
        loadMasterData();
    }

    public void saveMasterData(Data master) {
        try {
            mapper.writeValue(new File(MASTER_LOGIN_FILE), master);
            this.masterData = master;
        } catch (IOException e) {
            logger.severe("Error while saving master login data: " + e.getMessage());
            throw new StorageException("Failed to save master login data", e);
        }
    }

    private void loadMasterData() {
        File file = new File(MASTER_LOGIN_FILE);
        if (!file.exists()) return;

        try {
            this.masterData = mapper.readValue(file, Data.class);
        } catch (IOException e) {
            logger.severe("Error loading master login data: " + e.getMessage());
            throw new StorageException("Failed to load master login data", e);
        }
    }

    public Data getMasterData() {
        return masterData;
    }

    public boolean validateMasterLogin(String username, String password) {
        if (masterData == null) return false;
        return masterData.getUsername().equals(username) && 
               Encryption.VerifyPassword(password, masterData.getPassword());
    }

    public static class StorageException extends RuntimeException {
        public StorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
} 