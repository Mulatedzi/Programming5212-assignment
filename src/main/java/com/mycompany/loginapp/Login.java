package com.mycompany.loginapp;

/**
 * PROG5121 – Part 1: Registration and Login Feature
 * 
 * This class provides user registration and login validation.
 * It checks username, password complexity, and South African cell phone numbers.
 * 
 * @author [Your Name]
 * @version 1.0
 */
public class Login {

    // Registered user details
    private String username;
    private String password;
    private String cellPhone;
    private String firstName;
    private String lastName;
    private boolean isRegistered;

    public Login() {
        this.isRegistered = false;
    }

    /**
     * Validates username: must contain '_' and be at most 5 characters long.
     * @param username the username to check
     * @return true if valid, false otherwise
     */
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    /**
     * Validates password:
     * - at least 8 characters
     * - contains at least one uppercase letter
     * - contains at least one digit
     * - contains at least one special character (non‑alphanumeric)
     * @param password the password to check
     * @return true if meets complexity, false otherwise
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Validates South African cell phone number.
     * Must start with international code +27 followed by 9 or 10 digits.
     * 
     * <p>Regex attribution: Adapted from common patterns in developer forums
     * to match the assignment's requirement.
     * 
     * @param phone the phone number to validate
     * @return true if valid, false otherwise
     */
    public boolean checkCellPhoneNumber(String phone) {
        return phone != null && phone.matches("^\\+27\\d{9,10}$");
    }

    /**
     * Registers a new user if all validations pass.
     * Returns appropriate success or error message.
     * 
     * @param username   chosen username
     * @param password   chosen password
     * @param cellPhone  South African cell number
     * @param firstName  user's first name
     * @param lastName   user's last name
     * @return status message
     */
    public String registerUser(String username, String password, String cellPhone,
                               String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isRegistered = true;
        return "Cell phone number successfully added.";
    }

    /**
     * Verifies login credentials against stored data.
     * @param enteredUsername entered username
     * @param enteredPassword entered password
     * @return true if credentials match, false otherwise
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return isRegistered && username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    /**
     * Returns the appropriate login status message.
     * @param loginSuccess boolean indicating success or failure
     * @return welcome message or error message
     */
    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Getters for testing (optional)
    public boolean isRegistered() { return isRegistered; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getCellPhone() { return cellPhone; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}