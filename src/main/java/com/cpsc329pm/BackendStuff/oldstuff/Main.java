package com.cpsc329pm.BackendStuff.oldstuff;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {}

    private static void Input() {
        System.out.println("Reply with Yes or No for each question.");
        boolean createAccount = false;
        Scanner firstInfo = new Scanner(System.in);
        System.out.println("Do you already have an account?");
        String accountExistence = firstInfo.nextLine();
        if (accountExistence == "YES" || accountExistence == "Yes" || accountExistence == "yes") {

        } else if (accountExistence == "NO" || accountExistence == "No" || accountExistence == "no"){
            Scanner status = new Scanner(System.in);
            System.out.println("Do you want to create a new account?:");
            String choice = status.nextLine();
            if (choice == "YES" || choice == "Yes" || choice == "yes"){
                createAccount = true;
            } else if (choice == "NO" || choice == "No" || choice == "no"){

            }else{
                System.out.println("Your input is invalid.");
            }
        }else{
            System.out.println("Your input is invalid.");
        }
        if (createAccount == true) {
            Scanner secondInfo = new Scanner(System.in);
            System.out.println("Please enter your username: ");
            String username = secondInfo.nextLine();

            Scanner thirdInfo = new Scanner(System.in);
            System.out.println("Please enter your password: ");
            String password = thirdInfo.nextLine();

        }
    }
}
