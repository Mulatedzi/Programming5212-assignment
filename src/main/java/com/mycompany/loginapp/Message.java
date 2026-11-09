package com.mycompany.loginapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;
    private String sender;

    public Message(int messageNumber, String recipient, String messageText, String sender, String status) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.sender = sender;
        this.status = status;
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
    public String getSender() { return sender; }

    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageText == null || messageText.isEmpty()) {
            return "";
        }
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        lastWord = lastWord.replaceAll("[^a-zA-Z]", "");
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+27\\d{9,10}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Saves message to JSON (Manual formatting, no external libraries)
    public void storeMessageToJSON() {
        String json = "{\n" +
                      "  \"messageID\": \"" + messageID + "\",\n" +
                      "  \"messageNumber\": " + messageNumber + ",\n" +
                      "  \"sender\": \"" + sender + "\",\n" +
                      "  \"recipient\": \"" + recipient + "\",\n" +
                      "  \"messageText\": \"" + messageText.replace("\"", "\\\"") + "\",\n" +
                      "  \"messageHash\": \"" + messageHash + "\",\n" +
                      "  \"status\": \"" + status + "\"\n" +
                      "}\n";
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // Reads a JSON line and creates a Message object
    public static Message parseJSON(String json) {
        try {
            String id = extractValue(json, "messageID");
            int num = Integer.parseInt(extractValue(json, "messageNumber"));
            String snd = extractValue(json, "sender");
            String rec = extractValue(json, "recipient");
            String text = extractValue(json, "messageText");
            String stat = extractValue(json, "status");
            return new Message(num, rec, text, snd, stat);
        } catch (Exception e) {
            return null;
        }
    }

    private static String extractValue(String json, String key) {
        String search = "\"" + key + "\": ";
        int start = json.indexOf(search);
        if (start == -1) return "";
        start += search.length();
        if (json.charAt(start) == '"') {
            start++;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } else {
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("\n", start);
            if (end == -1) end = json.length();
            return json.substring(start, end).replace(",", "").replace("}", "").trim();
        }
    }
}