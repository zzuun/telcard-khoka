package com.example.zunnorain.telcard_khokha.Classes;

/**
 * Created by Zunnorain on 13/06/2018.
 */

public class User {


    private String username;
    private String phone;
    private String password;

    public User( String username, String phone, String password) {
        this.username = username;
        this.phone = phone;
        this.password = password;
    }


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
