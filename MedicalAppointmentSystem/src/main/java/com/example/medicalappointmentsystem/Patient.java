package com.example.medicalappointmentsystem;

import java.time.LocalDate;

public class Patient {
    private Integer id;
    private String voornaam;
    private String achternaam;
    private String email;
    private LocalDate geboortedatum;

    public Patient(Integer id, String voornaam, String achternaam, String email, LocalDate geboortedatum) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.geboortedatum = geboortedatum;
    }

    // Getters en setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }
}

