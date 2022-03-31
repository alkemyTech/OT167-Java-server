package com.alkemy.ong.enums;

public enum MailMessage {

    REGISTER_TITLE("Account registration: "),
    WELCOME_SUBJECT("Welcome to "),
    CONTACT_MAIL("Mail: "),
    CONTACT_PHONE("Telephone contact: "),
    CONTACT_ADDRESS("Address: ");

    private String value;

    MailMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getWelcomeMsg(String firstName, String lastName) {
        return "Welcome " + firstName + " " + lastName + ", thank you for registering on our website!";
    }

    public static String GetRegisterContactMsg(String firstName) {
        return "Hello " + firstName + "! Thank you for your registration !";
    }
}
