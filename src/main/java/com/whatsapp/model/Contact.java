package com.whatsapp.model;

public class Contact {

    private String name;
    private String phoneNumber;
    private String message;

    public Contact() {
    }

    public Contact(String name, String phoneNumber, String message) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}