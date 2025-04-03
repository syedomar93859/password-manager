package com.cpsc329pm.BackendStuff;

import java.io.*;
import java.util.*;
import java.security.*;
import javax.crypto.*;

public class MasterLogin {

    /**
     * Registers a new user
     * @param username
     * @param masterPassword
     * @return
     */
    boolean register(String username, String masterPassword) {


        return false;
    }

    /**
     * Authenticates login information
     * @param username
     * @param masterPassword
     * @return
     */
    boolean authenticate(String username, String masterPassword) {



        return false;
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
