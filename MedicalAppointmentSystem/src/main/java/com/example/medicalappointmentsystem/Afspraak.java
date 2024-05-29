package com.example.medicalappointmentsystem;

import java.time.LocalDate;

public class Afspraak {
    private String behandelingssoort;
    private String voornaam;
    private String achternaam;
    private LocalDate afspraakdatum;
    private String afspraaktijd;
    private String artsnaam;
    private String notitie;
    private String email;
    private LocalDate geboortedatum;

    public Afspraak(String behandelingssoort, String voornaam, String achternaam, LocalDate afspraakdatum, String afspraaktijd, String artsnaam, String notitie, String email, LocalDate geboortedatum) {
        this.behandelingssoort = behandelingssoort;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.afspraakdatum = afspraakdatum;
        this.afspraaktijd = afspraaktijd;
        this.artsnaam = artsnaam;
        this.notitie = notitie;
        this.email = email;
        this.geboortedatum = geboortedatum;
    }

    // Getters en Setters
    public String getBehandelingssoort() {
        return behandelingssoort;
    }

    public void setBehandelingssoort(String behandelingssoort) {
        this.behandelingssoort = behandelingssoort;
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

    public LocalDate getAfspraakdatum() {
        return afspraakdatum;
    }

    public void setAfspraakdatum(LocalDate afspraakdatum) {
        this.afspraakdatum = afspraakdatum;
    }

    public String getAfspraaktijd() {
        return afspraaktijd;
    }

    public void setAfspraaktijd(String afspraaktijd) {
        this.afspraaktijd = afspraaktijd;
    }

    public String getArtsnaam() {
        return artsnaam;
    }

    public void setArtsnaam(String artsnaam) {
        this.artsnaam = artsnaam;
    }

    public String getNotitie() {
        return notitie;
    }

    public void setNotitie(String notitie) {
        this.notitie = notitie;
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