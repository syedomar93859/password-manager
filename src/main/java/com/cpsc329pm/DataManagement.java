package com.cpsc329pm;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Old version of a data management system for handling user logins and registrations
 * through plain text file storage.
 */
public class DataManagement {
    private static HashMap<String, String[]> loginInfo = new HashMap<>(); // Stores login info, though unused here
    private static String USERNAME;
    private static String PASSWORD;

    /**
     * Tries to log in a user by checking credentials against entries in a file.
     */
    private static void LoginUser() {
        try (BufferedReader br = new BufferedReader(new FileReader("UserInfo.txt"))) {
            String line = br.readLine();
            boolean detailsFound = false;

            while (line != null) {
                String[] details = line.split(":"); // Assumes username and password are stored like "username:password"
                String name = details[0];
                String pass = details[1];

                // Comparing strings with '==' will not work as expected, should use .equals()
                if (name.equals(USERNAME) && pass.equals(PASSWORD)) {
                    detailsFound = true;
                }

                line = br.readLine(); // Move to the next line
            }

            if (detailsFound) {
                System.out.println("You are logged in.");
            } else {
                System.out.println("Your username or password or both are wrong.");
            }

        } catch (FileNotFoundException e) {
            // If file not found, prompt user to register
            Scanner scanner = new Scanner(System.in);
            System.out.println("Login Info not found. Would you like to register? (Y/N)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                RegisterUser();
            }
        } catch (IOException e) {
            System.out.println("Unexpected Error, terminating program.");
        }
    }

    /**
     * Registers a new user by saving their username and password to a file.
     * This currently overwrites the entire file instead of appending.
     */
    private static void RegisterUser() {
        Scanner scanner = new Scanner(System.in);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("UserInfo.txt"))); // Overwrites file
            System.out.println("Please enter a username: ");
            String username = scanner.nextLine();
            System.out.println("Please enter a password: ");
            String password = scanner.nextLine();

            // Save credentials in the format: username:password
            bw.write(username + ":" + password + "\n");
            bw.close();

            // Prompt for login after registration
            System.out.println("Would you like to try to login? (Y/N)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("Y")) {
                USERNAME = username;
                PASSWORD = password;
                LoginUser();
            }
        } catch (IOException e) {
            System.out.println("Unexpected Error, terminating program.");
        }
    }

    /**
     * Collects and stores login information from user input under a given label.
     * Currently only collects input, does not store it anywhere.
     */
    private static void AddLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please create a label for login info: ");
        String label = scanner.nextLine();
        System.out.println("Please enter the username: ");
        String username = scanner.nextLine();
        System.out.println("Please enter the password: ");
        String password = scanner.nextLine();

        // Currently this info is not saved or used.
        // This should store info to a structure or file.
    }
}
