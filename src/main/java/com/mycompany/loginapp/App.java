package com.mycompany.loginapp;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("===== REGISTRATION =====");
        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Username (must contain _ and be ≤5 chars): ");
        String username = scanner.nextLine().trim();
        System.out.print("Password (≥8 chars, upper, digit, special): ");
        String password = scanner.nextLine().trim();
        System.out.print("Cell phone (+27...): ");
        String cellPhone = scanner.nextLine().trim();

        String regMsg = login.registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println(regMsg);

        if (login.isRegistered()) {
            System.out.println("\n===== LOGIN =====");
            System.out.print("Username: ");
            String loginUser = scanner.nextLine().trim();
            System.out.print("Password: ");
            String loginPass = scanner.nextLine().trim();

            boolean success = login.loginUser(loginUser, loginPass);
            System.out.println(login.returnLoginStatus(success));
        } else {
            System.out.println("Registration failed. Please restart.");
        }
        scanner.close();
    }
}
