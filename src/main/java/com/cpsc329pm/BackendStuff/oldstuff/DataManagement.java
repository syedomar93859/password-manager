package com.cpsc329pm.BackendStuff.oldstuff;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class DataManagement {
    private static HashMap<String, String[]> loginInfo = new HashMap<>();
    private static String USERNAME;
    private static String PASSWORD;

    private static void LoginUser() {
        try (BufferedReader br = new BufferedReader(new FileReader("UserInfo.txt"))) {
            String line = br.readLine();
            boolean detailsFound = false;
            while(line != null){
                String[] details = line.split(":");
                String name = details[0];
                String pass = details[1];
                if (name == USERNAME && pass == PASSWORD){
                    detailsFound = true;
                }
            }
            if (detailsFound == true){
                System.out.println("You are logged in.");
            }else{
                System.out.println("Your username or password or both are wrong.");
            }

        } catch (FileNotFoundException e) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Login Info not found. Would you like to register? (Y/N)");
            String response = scanner.nextLine();
            if (response.equals("Y")) {
                RegisterUser();
            }
        } catch (IOException e) {
            System.out.println("Unexpected Error, terminating program.");
        }
    }

    private static void RegisterUser() {
        Scanner scanner = new Scanner(System.in);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("UserInfo.txt")));
            System.out.println("Please enter a username: ");
            String username = scanner.nextLine();
            System.out.println("Please enter a password: ");
            String password = scanner.nextLine();
            bw.write(username);
            bw.write(password);
            System.out.println("Would you like to try to login? (Y/N)");
            String response = scanner.nextLine();
            if (response.equals("Y")) {
                LoginUser();
            }
        } catch (IOException e) {
            System.out.println("Unexpected Error, terminating program.");
        }
    }

    private static void AddLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please create a label for login info: ");
        String label = scanner.nextLine();
        System.out.println("Please enter the username: ");
        String username = scanner.nextLine();
        System.out.println("Please enter the password: ");
        String password = scanner.nextLine();
    }
}
