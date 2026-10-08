package com.mycompany.loginapp;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        // ================= PART 1: LOGIN =================
        System.out.println("===== REGISTRATION =====");
        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Username (must contain _ and be <=5 chars): ");
        String username = scanner.nextLine().trim();
        System.out.print("Password (>=8 chars, upper, digit, special): ");
        String password = scanner.nextLine().trim();
        System.out.print("Cell phone (+27...): ");
        String cellPhone = scanner.nextLine().trim();

        String regMsg = login.registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println(regMsg);

        if (!login.isRegistered()) {
            System.out.println("Registration failed. Exiting.");
            scanner.close();
            return;
        }

        System.out.println("\n===== LOGIN =====");
        System.out.print("Username: ");
        String loginUser = scanner.nextLine().trim();
        System.out.print("Password: ");
        String loginPass = scanner.nextLine().trim();

        boolean success = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(success));

        if (!success) {
            System.out.println("Login failed. Exiting.");
            scanner.close();
            return;
        }

        // ================= PART 2: QUICKCHAT =================
        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages do you wish to enter? ");
        int numMessages = 0;
        try {
            numMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Exiting.");
            scanner.close();
            return;
        }

        boolean quit = false;
        int messagesEntered = 0;

        while (!quit) {
            System.out.println("\n--- Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String choiceStr = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (messagesEntered >= numMessages) {
                        System.out.println("You have already entered the maximum number of messages.");
                        break;
                    }
                    // FOR LOOP for entering assigned number of messages
                    for (int i = messagesEntered; i < numMessages; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");
                        
                        // Recipient
                        System.out.print("Enter recipient cell number (+27...): ");
                        String recipient = scanner.nextLine().trim();

                        // Message Text
                        System.out.print("Enter message (max 250 chars): ");
                        String text = scanner.nextLine().trim();

                        Message msg = new Message(i, recipient, text);

                        // Validate ID
                        if (!msg.checkMessageID()) {
                            System.out.println("Message ID generation failed.");
                            continue;
                        }

                        // Validate Length
                        String lengthCheck = msg.checkMessageLength();
                        System.out.println(lengthCheck);
                        if (!lengthCheck.equals("Message ready to send.")) {
                            continue; // Skip if too long
                        }

                        // Validate Recipient
                        String recipientCheck = msg.checkRecipientCell();
                        System.out.println(recipientCheck);
                        if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                            continue; // Skip if invalid
                        }

                        // Display details
                        System.out.println("Message ID: " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.getMessageHash());

                        // Ask to Send/Store/Discard
                        System.out.println("Choose action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Store Message to send later");
                        System.out.println("3) Disregard Message");
                        System.out.print("Option: ");
                        int action = 1;
                        try {
                            action = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid action, defaulting to Send.");
                        }

                        String actionResult = msg.SentMessage(action);
                        System.out.println(actionResult);

                        messagesEntered++;
                    }
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    quit = true;
                    System.out.println("Exiting QuickChat. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        // Display final summary
        System.out.println("\n===== SUMMARY =====");
        System.out.println(Message.printMessages());
        System.out.println("Total messages sent: " + Message.returnTotalMessages());

        scanner.close();
    }
}