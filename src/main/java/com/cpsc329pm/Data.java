package com.cpsc329pm;

/**
 * Class representing a single login data entry with platform, username, and password.
 */
public class Data {

    private String platform; // The name of the platform (e.g., Facebook, Gmail)
    private String username; // The user's username for the platform
    private String password; // The hashed and salted password for the user

    /**
     * Default constructor required for JSON deserialization.
     */
    public Data() {}

    /**
     * Constructs a Data object with specified platform, username, and password.
     *
     * @param platform The name of the platform.
     * @param username The user's username.
     * @param password The user's hashed password.
     */
    public Data(String platform, String username, String password) {
        this.platform = platform;
        this.username = username;
        this.password = password;
    }

    // Getter and setter for platform

    /**
     * @return The platform name.
     */
    public String getPlatform() { return platform; }

    /**
     * @param platform The platform name to set.
     */
    public void setPlatform(String platform) { this.platform = platform; }

    // Getter and setter for username

    /**
     * @return The username.
     */
    public String getUsername() { return username; }

    /**
     * @param username The username to set.
     */
    public void setUsername(String username) { this.username = username; }

    // Getter and setter for password

    /**
     * @return The hashed and salted password.
     */
    public String getPassword() { return password; }

    /**
     * @param password The hashed password to set.
     */
    public void setPassword(String password) { this.password = password; }

}
