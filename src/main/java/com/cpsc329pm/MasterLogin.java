package com.cpsc329pm;

import java.util.*;
import java.util.regex.*;
import java.util.List;

/**
 * This class handles the registration, authentication, and password validation for the master login system.
 * It provides methods for user registration, password validation, authentication, and account management tasks.
 */
public class MasterLogin {
    private final MasterLoginStorage masterStorage; // Storage for master login credentials
    private static final int MIN_PASSWORD_LENGTH = 8; // Minimum length for passwords
    private static final String[] COMMON_PASSWORDS = { // Common passwords to check against for security
            "password", "123456", "qwerty", "admin", "welcome",
            "letmein", "monkey", "dragon", "baseball", "football"
    };

    // Constructor initializes the masterStorage object.
    public MasterLogin() {
        this.masterStorage = new MasterLoginStorage();
    }

    /**
     * Verifies that the username and its confirmation match.
     * @param username The original username.
     * @param confirmUsername The confirmation username.
     * @return true if usernames match, false otherwise.
     */
    public boolean verifyUsernameMatch(String username, String confirmUsername) {
        // Ensure both usernames are not null and match each other.
        if (username == null || confirmUsername == null) {
            return false;
        }
        return username.equals(confirmUsername);
    }

    /**
     * Verifies that the password and its confirmation match.
     * @param password The original password.
     * @param confirmPassword The confirmation password.
     * @return true if passwords match, false otherwise.
     */
    public boolean verifyPasswordMatch(String password, String confirmPassword) {
        // Ensure both passwords are not null and match each other.
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);
    }

    /**
     * Registers a new user with username and password confirmation checks.
     * @param username The username for the master account.
     * @param confirmUsername The confirmation username.
     * @param password The password for the master account.
     * @param confirmPassword The confirmation password.
     * @throws IllegalArgumentException if inputs are empty or do not match, or if password doesn't meet security requirements.
     */
    public void registerWithConfirmation(String username, String confirmUsername,
                                         String password, String confirmPassword) {
//        // Check if the usernames match
//        if (!verifyUsernameMatch(username, confirmUsername)) {
//            throw new IllegalArgumentException("Usernames do not match");
//        }
//
//        // Check if the passwords match
//        if (!verifyPasswordMatch(password, confirmPassword)) {
//            throw new IllegalArgumentException("Passwords do not match");
//        }
//
//        // Proceed with regular registration if everything is valid
//        register(username, password);
    }

    /**
     * Validates if a password meets all security requirements.
     * @param password The password to validate.
     * @return true if password meets all security requirements, false otherwise.
     */
    public boolean isValidPassword(String password) {

        // Check if password length is less than the minimum length
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

        // Check for spaces in the password (invalid if contains spaces)
        if (password.contains(" ")) {
            return false;
        }

        // Check against common passwords (in lowercase)
        String lowercasePassword = password.toLowerCase();
        for (String common : COMMON_PASSWORDS) {
            if (lowercasePassword.contains(common)) {
                return false; // Password is weak if it contains a common password
            }
        }

        return true; // Return true if all security checks pass
    }

    /**
     * Provides a string describing password requirements.
     * @return String describing password requirements.
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
     * Registers a new user by saving their master login credentials after validating the password.
     * @param username The username for the master account.
     * @param masterPassword The password for the master account.
     * @throws IllegalArgumentException if username or password is empty or doesn't meet security requirements.
     */
    public void register(String username, String masterPassword) {
        // Validate username input
//        if (username == null || username.trim().isEmpty()) {
//            throw new IllegalArgumentException("Username cannot be empty");
//        }
//
//        // Validate password input
//        if (masterPassword == null || masterPassword.trim().isEmpty()) {
//            throw new IllegalArgumentException("Password cannot be empty");
//        }
//
//        // Validate password strength
//        if (!isValidPassword(masterPassword)) {
//            throw new IllegalArgumentException("Password does not meet security requirements.\n" + getPasswordRequirements());
//        }
//
//        // Encrypt password and create a Data object with the master credentials
//        Data masterData = new Data("master", username, Encryption.encrypt(masterPassword));
//
//        // Save the master data to storage
//        masterStorage.saveMasterData(masterData);
    }

    /**
     * Authenticates the username and password by comparing input with stored credentials.
     * @param username The username to authenticate.
     * @param masterPassword The password to verify.
     * @return true if both username and password match the stored credentials, false otherwise.
     */
    public boolean authenticate(String username, String masterPassword) {
        // Retrieve all stored master data entries
        List<Data> masterEntries = masterStorage.getAllMasterData();

        // Loop through each stored entry and check if any match
        for (Data entry : masterEntries) {
            if (entry.getPlatform().equals("master") &&
                    entry.getUsername().equals(username) &&
                    Encryption.verifyPassword(masterPassword, entry.getPassword())) {
                return true; // Match found
            }
        }
        return false; // No match found
    }

    // Below methods are placeholders for other account management tasks

    /**
     * Change the user's master password (not yet implemented).
     * @param username The username of the account.
     * @param masterPassword The current master password.
     * @param newPassword The new password.
     * @return false (not implemented).
     */
    boolean changeMasterPassword(String username, String masterPassword, String newPassword) {
        return false; // Not yet implemented
    }

    /**
     * Reset password using a security question (not yet implemented).
     * @param username The username of the account.
     * @param securityAnswer The answer to the security question.
     * @param newPassword The new password.
     * @return false (not implemented).
     */
    boolean resetPassword(String username, String securityAnswer, String newPassword) {
        return false; // Not yet implemented
    }

    /**
     * Log the user out (not yet implemented).
     * @param username The username of the account.
     */
    void logout(String username) {
        // Not yet implemented
    }

    /**
     * Check if the account is locked due to multiple failed login attempts (not yet implemented).
     * @param username The username of the account.
     * @return false (not implemented).
     */
    boolean isLocked(String username) {
        return false; // Not yet implemented
    }

    /**
     * Get the number of failed login attempts for an account (not yet implemented).
     * @param username The username of the account.
     * @return 0 (not implemented).
     */
    int getFailedAttempts(String username) {
        return 0; // Not yet implemented
    }

    /**
     * Reset the failed login attempts for an account (not yet implemented).
     * @param username The username of the account.
     */
    void resetFailedAttempts(String username) {
        // Not yet implemented
    }

    /**
     * Lock an account if too many failed login attempts are made (not yet implemented).
     * @param username The username of the account.
     * @return false (not implemented).
     */
    boolean lockAccount(String username) {
        return false; // Not yet implemented
    }

    /**
     * Admin privilege to unlock an account (not yet implemented).
     * @param username The username of the locked account.
     * @param adminKey The admin key for unlocking.
     * @return false (not implemented).
     */
    boolean unlockAccount(String username, String adminKey) {
        return false; // Not yet implemented
    }

    /**
     * Set up a security question for an account (not yet implemented).
     * @param username The username of the account.
     * @param question The security question.
     * @param answer The security answer.
     * @return false (not implemented).
     */
    boolean addSecurityQuestion(String username, String question, String answer) {
        return false; // Not yet implemented
    }

    /**
     * Verify the security question answer for an account (not yet implemented).
     * @param username The username of the account.
     * @param answer The security question answer.
     * @return false (not implemented).
     */
    boolean validateSecurityAnswer(String username, String answer) {
        return false; // Not yet implemented
    }

    /**
     * Delete a user account (not yet implemented).
     * @param username The username of the account.
     * @param masterPassword The master password for verification.
     * @return false (not implemented).
     */
    boolean deleteUser(String username, String masterPassword) {
        return false; // Not yet implemented
    }

    /**
     * Get a list of all registered usernames (not yet implemented).
     * @return null (not implemented).
     */
    List<String> getUsernames() {
        return null; // Not yet implemented
    }

    /**
     * Validate the username and password input (not yet implemented).
     * @param username The username input.
     * @param password The password input.
     * @return false (not implemented).
     */
    boolean validateInput(String username, String password) {
        return false; // Not yet implemented
    }
}
