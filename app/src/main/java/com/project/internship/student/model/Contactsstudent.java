package com.project.internship.student.model;

/**
 * Created by Rahul Bhardwaj on 5/3/2016.
 */
public class Contactsstudent {
    private String name,title,date,location;

    public Contactsstudent(String name, String title, String date, String location){
       this.setName(name);
        this.setTitle(title);
        this.setDate(date);
        this.setLocation(location);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
