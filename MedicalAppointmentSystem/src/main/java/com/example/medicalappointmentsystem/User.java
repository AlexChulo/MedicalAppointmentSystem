package com.example.medicalappointmentsystem;

public class User {
    private String voornaam;
    private String achternaam;
    private String username;
    private String password;
    private String role;

    public User(String voornaam, String achternaam, String username, String password, String role) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}