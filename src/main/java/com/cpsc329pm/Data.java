package com.cpsc329pm;

/**
 * Class representing a single login data entry with platform, username, and password.
 */
public class Data {

    // The name of the platform (e.g., Facebook, Gmail)
    private String platform;

    // The user's username for the platform
    private String username;

    // The hashed and salted password for the user
    private String password;

    /**
     * Default constructor required for JSON deserialization.
     * This allows external libraries to instantiate the object using reflection.
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

    // ------------------- Getters and Setters -------------------

    /**
     * Gets the name of the platform.
     *
     * @return The platform name.
     */
    public String getPlatform() { return platform; }

    /**
     * Sets the name of the platform.
     *
     * @param platform The platform name to set.
     */
    public void setPlatform(String platform) { this.platform = platform; }

    /**
     * Gets the username for the platform.
     *
     * @return The username.
     */
    public String getUsername() { return username; }

    /**
     * Sets the username for the platform.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Gets the password associated with the username and platform.
     *
     * @return The hashed and salted password.
     */
    public String getPassword() { return password; }

    /**
     * Sets the password associated with the username and platform.
     *
     * @param password The hashed password to set.
     */
    public void setPassword(String password) { this.password = password; }

}
