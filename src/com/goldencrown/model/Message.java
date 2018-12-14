package com.goldencrown.model;

public class Message {
    private Kingdom sender, receiver;
    private String content;

    public Kingdom getSender() {
        return sender;
    }

    public Kingdom getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }
}
