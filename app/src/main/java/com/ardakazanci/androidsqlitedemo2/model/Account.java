package com.ardakazanci.androidsqlitedemo2.model;

public class Account {

    private String userName;
    private String email;

    public Account() {

    }

    public Account(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
