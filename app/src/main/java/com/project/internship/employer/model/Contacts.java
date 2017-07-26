package com.project.internship.employer.model;

/**
 * Created by Rahul Bhardwaj on 5/3/2016.
 */
public class Contacts {
    private String senderid, reciverid, message, date;

    public Contacts(String senderid, String reciverid, String message, String date) {
        this.senderid = senderid;
        this.reciverid = reciverid;
        this.message = message;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getReciverid() {
        return reciverid;
    }

    public void setReciverid(String reciverid) {
        this.reciverid = reciverid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
