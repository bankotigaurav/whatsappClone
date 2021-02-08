package com.gaurav.mywhatsapp;

public class User {

    private String sender;
    private String recipient;
    private String message;
    private String time;

    public User(){

    }

    public User(String sender, String recipient, String message, String time) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
