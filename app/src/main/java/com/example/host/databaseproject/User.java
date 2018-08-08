package com.example.host.databaseproject;

import android.graphics.Bitmap;

import java.io.Serializable;

public class  User implements Serializable {
    private String email;
    private String name;
    private String surname;
    private String fatherName;
    private Bitmap avatar;
    private String userID;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public User(String email, String name, String surname, String fatherName, String userID){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.fatherName = fatherName;
        this.userID = userID;
    }
}
