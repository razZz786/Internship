package com.project.internship.employer.model;

/**
 * Created by Rahul Bhardwaj on 5/3/2016.
 */
public class SearchNameContacts {
    private String name,dob,studentid,contactnumber,email,address,cgpa,json;

    public SearchNameContacts(String name,String dob,String studentid,String contactnumber,String email,String address,String cgpa,String json){
     this.setName(name);
        this.setDob(dob);
        this.setStudentid(studentid);
        this.setContactnumber(contactnumber);
        this.setEmail(email);
        this.setAddress(address);
        this.setCgpa(cgpa);
        this.setJson(json);
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
