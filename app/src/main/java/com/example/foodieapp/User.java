package com.example.foodieapp;

public class User {

    private String name;
    private String email;
    private Integer contact;
    private String pw;
    private String Cpw;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getCpw() {
        return Cpw;
    }

    public void setCpw(String cpw) {
        Cpw = cpw;
    }

    public User(){}


}
