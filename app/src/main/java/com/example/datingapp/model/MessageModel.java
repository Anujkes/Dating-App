package com.example.datingapp.model;

public class MessageModel {

    String date,message,senderId,time;

    public MessageModel() {
    }

    public MessageModel(String date, String message, String senderId, String time) {
        this.date = date;
        this.message = message;
        this.senderId = senderId;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
