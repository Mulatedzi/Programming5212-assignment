package com.mycompany.loginapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;

    // Static lists to keep track of messages during the program run
    private static final List<Message> allMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    // Constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Getters
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }

    // Generates a random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    // Checks if the message ID is not more than 10 characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Checks if the recipient cell number is correctly formatted
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+27\\d{9,10}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Creates the Message Hash
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageText == null || messageText.isEmpty()) {
            return "";
        }
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        // Remove punctuation from last word
        lastWord = lastWord.replaceAll("[^a-zA-Z]", "");
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Checks if the message length is within 250 characters
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    // Handles Send, Store, or Disregard
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                this.status = "Sent";
                totalMessagesSent++;
                allMessages.add(this);
                return "Message successfully sent.";
            case 2:
                this.status = "Stored";
                storeMessage(); // Automatically store to JSON
                return "Message successfully stored.";
            case 3:
                this.status = "Disregarded";
                return "Press 0 to delete the message.";
            default:
                return "Invalid option.";
        }
    }

    // Returns all messages sent during the program run
    public static String printMessages() {
        if (allMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : allMessages) {
            sb.append("Message ID: ").append(m.getMessageID()).append("\n");
            sb.append("Message Hash: ").append(m.getMessageHash()).append("\n");
            sb.append("Recipient: ").append(m.getRecipient()).append("\n");
            sb.append("Message: ").append(m.getMessageText()).append("\n");
            sb.append("-----------------------------\n");
        }
        return sb.toString();
    }

    // Returns the total number of messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Stores the message to a JSON file using standard Java (No external libraries needed!)
    public void storeMessage() {
        String json = "{\n" +
                      "  \"messageID\": \"" + messageID + "\",\n" +
                      "  \"messageNumber\": " + messageNumber + ",\n" +
                      "  \"recipient\": \"" + recipient + "\",\n" +
                      "  \"messageText\": \"" + messageText.replace("\"", "\\\"") + "\",\n" +
                      "  \"messageHash\": \"" + messageHash + "\",\n" +
                      "  \"status\": \"Stored\"\n" +
                      "}\n";
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Error storing message to JSON: " + e.getMessage());
        }
    }
}