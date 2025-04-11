package com.cpsc329pm;

import java.util.*;
import java.util.regex.*;
import java.util.List;

public class MasterLogin {
    private final MasterLoginStorage masterStorage;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String[] COMMON_PASSWORDS = {
        "password", "123456", "qwerty", "admin", "welcome",
        "letmein", "monkey", "dragon", "baseball", "football"
    };

    public MasterLogin() {
        this.masterStorage = new MasterLoginStorage();
    }

    /**
     * Verifies that the username and its confirmation match
     * @param username The original username
     * @param confirmUsername The confirmation username
     * @return true if usernames match, false otherwise
     */
    public boolean verifyUsernameMatch(String username, String confirmUsername) {
        if (username == null || confirmUsername == null) {
            return false;
        }
        return username.equals(confirmUsername);
    }

    /**
     * Verifies that the password and its confirmation match
     * @param password The original password
     * @param confirmPassword The confirmation password
     * @return true if passwords match, false otherwise
     */
    public boolean verifyPasswordMatch(String password, String confirmPassword) {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);
    }

    /**
     * Registers a new user with confirmation checks
     * @param username The username for the master account
     * @param confirmUsername The confirmation username
     * @param password The password for the master account
     * @param confirmPassword The confirmation password
     * @throws IllegalArgumentException if inputs are empty, don't match, or password doesn't meet requirements
     */
    public void registerWithConfirmation(String username, String confirmUsername, 
                                       String password, String confirmPassword) {
        // Check if usernames match
        if (!verifyUsernameMatch(username, confirmUsername)) {
            throw new IllegalArgumentException("Usernames do not match");
        }

        // Check if passwords match
        if (!verifyPasswordMatch(password, confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // Proceed with regular registration
        register(username, password);
    }

    /**
     * Validates if a password meets security requirements
     * @param password The password to validate
     * @return true if password meets requirements, false otherwise
     */
    private boolean isValidPassword(String password) {

        // Check minimum length
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }

        // Check for at least one uppercase letter
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }

        // Check for at least one lowercase letter
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }

        // Check for at least one number
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }

        // Check for at least one special character
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) {
            return false;
        }

        // Check for spaces
        if (password.contains(" ")) {
            return false;
        }

        // Check against common passwords
        String lowercasePassword = password.toLowerCase();
        for (String common : COMMON_PASSWORDS) {
            if (lowercasePassword.contains(common)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets a description of password requirements
     * @return String describing password requirements
     */
    public String getPasswordRequirements() {
        return "Password must:\n" +
               "- Be at least " + MIN_PASSWORD_LENGTH + " characters long\n" +
               "- Contain at least one uppercase letter\n" +
               "- Contain at least one lowercase letter\n" +
               "- Contain at least one number\n" +
               "- Contain at least one special character (!@#$%^&*(),.?\":{}|<>)\n" +
               "- Not contain spaces\n" +
               "- Not contain common words or patterns";
    }

    /**
     * Registers a new user by saving their master login credentials
     * @param username The username for the master account
     * @param masterPassword The password for the master account
     * @throws IllegalArgumentException if username or password is empty or password doesn't meet requirements
     */
    public void register(String username, String masterPassword) {
        // Validate inputs
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (masterPassword == null || masterPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Validate password strength
        if (!isValidPassword(masterPassword)) {
            throw new IllegalArgumentException("Password does not meet security requirements.\n" + getPasswordRequirements());
        }

        // Create a new Data object with the master credentials
        Data masterData = new Data("master", username, Encryption.encrypt(masterPassword));
        
        // Save the master data
        masterStorage.saveMasterData(masterData);
    }

    /**
     * Authenticates the username
     * @param username The username to authenticate
     * @return true if username is valid and matches stored username, false otherwise
     * @throws IllegalArgumentException if username is empty
     */
//    public boolean authenticateUsername(String username) {
//        // Validate input
//        if (username == null || username.trim().isEmpty()) {
//            return false;
////            throw new IllegalArgumentException("Username cannot be empty");
//        }
//
//        System.out.println("We have also been here");
//        // Get the stored master data
//        Data storedData = masterStorage.getMasterData();
//        System.out.println(storedData.getUsername() + "      " + storedData.getPassword() + "      " + storedData.getPlatform());
//
//        // Check if master data exists and username matches
//        if (storedData == null){
//            return false;
//        }
//        return storedData != null && storedData.getUsername().equals(username);
//    }

    /**
     * Authenticates the password
     * @param password The password to verify
     * @return true if password matches stored password, false otherwise
     * @throws IllegalArgumentException if password is empty
     */
//    public boolean authenticatePassword(String password) {
//        // Validate input
//        if (password == null || password.trim().isEmpty()) {
////            throw new IllegalArgumentException("Password cannot be empty");
//            return false;
//        }
//        System.out.println("We have been here");
//        // Get the stored master data
//        Data storedData = masterStorage.getMasterData();
//        System.out.println(storedData.getUsername() + storedData.getPassword() + storedData.getPlatform());
//
//        // Check if master data exists and verify password
//        return storedData != null && Encryption.VerifyPassword(password, storedData.getPassword());
//    }

    /**
     * Authenticates both username and password
     * @param username The username to authenticate
     * @param masterPassword The password to verify
     * @return true if both username and password are valid, false otherwise
     * @throws IllegalArgumentException if username or password is empty
     */
    public boolean authenticate(String username, String masterPassword) {
        List<Data> masterEntries = masterStorage.getAllMasterData();

        for (Data entry : masterEntries) {
            if (entry.getPlatform().equals("master") &&
                    entry.getUsername().equals(username) &&
                    Encryption.verifyPassword(masterPassword, entry.getPassword())) {
                return true; // Found a matching username + password
            }
        }
        return false; // No match found
    }


    /**
     * Change user's master password
     * @param username
     * @param masterPassword
     * @param newPassword
     * @return
     */
    boolean changeMasterPassword(String username, String masterPassword, String newPassword) {
        return false;
    }

    /**
     * Reset password with security question
     * @param username
     * @param securityAnswer
     * @param newPassword
     * @return
     */
    boolean resetPassword(String username, String securityAnswer, String newPassword) {
        return false;
    }

    /**
     * Log the user out
     * @param username
     */
    void logout(String username) {

    }

    /**
     * Check if account is locked due to failed logins
     * @param username
     * @return
     */
    boolean isLocked(String username) {
        return false;
    }

    /**
     * Get the number of failed login attempts
     * @param username
     * @return
     */
    int getFailedAttempts(String username) {
        return 0;
    }

    /**
     *  Reset failed login counter
     * @param username
     */
    void resetFailedAttempts(String username) {

    }

    /**
     * Lock account if too many failed login attempts
     * @param username
     * @return
     */
    boolean lockAccount(String username) {
        return false;
    }


    /**
     * Admin privilege, unlock an account
     * @param username
     * @return
     */
    boolean unlockAccount(String username, String adminKey) {
        return false;
    }

    /**
     * Set up security question
     * @param username
     * @param question
     * @param answer
     * @return
     */
    boolean addSecurityQuestion(String username, String question, String answer) {
        return false;
    }

    /**
     * Verify security question answer
     * @param username
     * @param answer
     * @return
     */
    boolean validateSecurityAnswer(String username, String answer) {
        return false;
    }

    /**
     * Delete the user's account
     * @param username
     * @param masterPassword
     * @return
     */
    boolean deleteUser(String username, String masterPassword) {
        return false;
    }

    /**
     * Get list of registered usernames
     * @return
     */
    List<String> getUsernames() {
        return null;
    }

    /**
     * Check if user has input valid username and password
     * @param username the username that the user input
     * @param password the password that the user input
     * @return  return whether the inputs were valid or not
     */
    boolean validateInput(String username, String password) {
        return false;
    }
}
