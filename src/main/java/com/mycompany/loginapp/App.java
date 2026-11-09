package com.mycompany.loginapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class App {

    // Parallel Arrays for Part 3
    static Message[] sentMessages = new Message[50];
    static Message[] disregardedMessages = new Message[50];
    static Message[] storedMessages = new Message[50];
    
    static int sentCount = 0;
    static int disregardedCount = 0;
    static int storedCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        // --- PART 1: LOGIN ---
        System.out.println("===== REGISTRATION =====");
        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Username (must contain _ and <=5 chars): ");
        String username = scanner.nextLine().trim();
        System.out.print("Password (>=8 chars, upper, digit, special): ");
        String password = scanner.nextLine().trim();
        System.out.print("Cell phone (+27...): ");
        String cellPhone = scanner.nextLine().trim();

        System.out.println(login.registerUser(username, password, cellPhone, firstName, lastName));

        System.out.println("\n===== LOGIN =====");
        System.out.print("Username: ");
        String loginUser = scanner.nextLine().trim();
        System.out.print("Password: ");
        String loginPass = scanner.nextLine().trim();

        if (!login.loginUser(loginUser, loginPass)) {
            System.out.println("Login failed. Exiting.");
            return;
        }
        System.out.println(login.returnLoginStatus(true));

        // Pre-load the JSON file into the Stored Messages array
        loadStoredMessages();

        // --- PART 2 & 3: QUICKCHAT ---
        System.out.println("\nWelcome to QuickChat.");
        boolean quit = false;

        while (!quit) {
            System.out.println("\n--- Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");

            String choiceStr = scanner.nextLine().trim();
            switch (choiceStr) {
                case "1":
                    sendMessageFlow(scanner, loginUser);
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    quit = true;
                    System.out.println("Exiting QuickChat. Goodbye!");
                    break;
                case "4":
                    storedMessagesMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }
        }
        scanner.close();
    }

    private static void sendMessageFlow(Scanner scanner, String sender) {
        System.out.print("How many messages do you wish to enter? ");
        int numMessages = 0;
        try {
            numMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        for (int i = 0; i < numMessages; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");
            System.out.print("Enter recipient cell number (+27...): ");
            String recipient = scanner.nextLine().trim();
            System.out.print("Enter message (max 250 chars): ");
            String text = scanner.nextLine().trim();

            // Create a temporary message to check length and recipient
            Message msg = new Message(i, recipient, text, sender, "Pending");

            if (!msg.checkMessageLength().equals("Message ready to send.")) {
                System.out.println(msg.checkMessageLength());
                continue;
            }
            if (!msg.checkRecipientCell().equals("Cell phone number successfully captured.")) {
                System.out.println(msg.checkRecipientCell());
                continue;
            }

            System.out.println("Message ID: " + msg.getMessageID());
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("Choose action: 1) Send  2) Store  3) Disregard");
            int action = Integer.parseInt(scanner.nextLine().trim());

            if (action == 1) {
                msg = new Message(i, recipient, text, sender, "Sent");
                sentMessages[sentCount++] = msg;
                System.out.println("Message successfully sent.");
            } else if (action == 2) {
                msg = new Message(i, recipient, text, sender, "Stored");
                storedMessages[storedCount++] = msg;
                msg.storeMessageToJSON();
                System.out.println("Message successfully stored.");
            } else {
                msg = new Message(i, recipient, text, sender, "Disregarded");
                disregardedMessages[disregardedCount++] = msg;
                System.out.println("Press 0 to delete the message.");
            }
        }
    }

    private static void storedMessagesMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search messages for a particular recipient");
            System.out.println("e) Delete a message using the message hash");
            System.out.println("f) Display a report of all stored messages");
            System.out.println("g) Back to Main Menu");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    for (int i = 0; i < storedCount; i++) {
                        System.out.println("Sender: " + storedMessages[i].getSender() + " | Recipient: " + storedMessages[i].getRecipient());
                    }
                    break;
                case "b":
                    Message longest = storedMessages[0];
                    for (int i = 1; i < storedCount; i++) {
                        if (storedMessages[i].getMessageText().length() > longest.getMessageText().length()) {
                            longest = storedMessages[i];
                        }
                    }
                    if (longest != null) System.out.println("Longest Message: " + longest.getMessageText());
                    break;
                case "c":
                    System.out.print("Enter Message ID: ");
                    String searchID = scanner.nextLine().trim();
                    for (int i = 0; i < storedCount; i++) {
                        if (storedMessages[i].getMessageID().equals(searchID)) {
                            System.out.println("Recipient: " + storedMessages[i].getRecipient());
                            System.out.println("Message: " + storedMessages[i].getMessageText());
                            return;
                        }
                    }
                    System.out.println("Message ID not found.");
                    break;
                case "d":
                    System.out.print("Enter Recipient Number: ");
                    String searchRec = scanner.nextLine().trim();
                    for (int i = 0; i < storedCount; i++) {
                        if (storedMessages[i].getRecipient().equals(searchRec)) {
                            System.out.println("Message: " + storedMessages[i].getMessageText());
                        }
                    }
                    break;
                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    String searchHash = scanner.nextLine().trim();
                    boolean deleted = false;
                    for (int i = 0; i < storedCount; i++) {
                        if (storedMessages[i].getMessageHash().equalsIgnoreCase(searchHash)) {
                            // Shift array elements to delete
                            for (int j = i; j < storedCount - 1; j++) {
                                storedMessages[j] = storedMessages[j + 1];
                            }
                            storedMessages[storedCount - 1] = null;
                            storedCount--;
                            System.out.println("Message successfully deleted.");
                            deleted = true;
                            break;
                        }
                    }
                    if (!deleted) System.out.println("Hash not found.");
                    break;
                case "f":
                    System.out.println("\n--- Full Message Report ---");
                    for (int i = 0; i < storedCount; i++) {
                        System.out.println("Hash: " + storedMessages[i].getMessageHash());
                        System.out.println("Recipient: " + storedMessages[i].getRecipient());
                        System.out.println("Message: " + storedMessages[i].getMessageText());
                        System.out.println("-------------------------");
                    }
                    break;
                case "g":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Reads messages.json into the parallel array
    private static void loadStoredMessages() {
        try (BufferedReader br = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            StringBuilder jsonBlock = new StringBuilder();
            while ((line = br.readLine()) != null) {
                jsonBlock.append(line).append("\n");
                if (line.trim().equals("}")) {
                    Message msg = Message.parseJSON(jsonBlock.toString());
                    if (msg != null && storedCount < 50) {
                        storedMessages[storedCount++] = msg;
                    }
                    jsonBlock = new StringBuilder();
                }
            }
        } catch (Exception e) {
            // File doesn't exist yet, which is fine
        }
    }
}