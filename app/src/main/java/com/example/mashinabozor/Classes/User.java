package com.example.mashinabozor.Classes;

public class User {

    private String full_name;
    private String phone_number;
    private String password;

    public User(String full_name, String phone_number, String password) {
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.password = password;
    }

    public User() {
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
