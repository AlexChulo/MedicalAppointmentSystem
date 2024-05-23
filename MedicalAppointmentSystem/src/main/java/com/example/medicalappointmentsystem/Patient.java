package com.example.medicalappointmentsystem;

public class Patient {
    private String voornaam;
    private String achternaam;

    public Patient(String voornaam, String achternaam) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }
}

