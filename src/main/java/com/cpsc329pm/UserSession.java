package com.cpsc329pm;

/**
 * A utility class to manage the current user's session.
 * This class stores the username of the currently logged-in user and provides
 * methods to set, retrieve, and clear that session information.
 */
public class UserSession {

    // Static variable to store the username of the current session
    private static String username;

    /**
     * Sets the username for the current session.
     *
     * @param user the username to be stored
     */
    public static void setUsername(String user) {
        username = user;
    }

    /**
     * Retrieves the username of the current session.
     *
     * @return the current session's username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Clears the current session by setting the username to null.
     * Should be called during logout or when the session ends.
     */
    public static void clearSession() {
        username = null;
    }
}
