package com.example.medicalappointmentsystem;

public class Afspraak {
    private String behandelingssoort;
    private String voornaam;
    private String achternaam;
    private String afspraakdatum;
    private String afspraaktijd;
    private String artsnaam;
    private String notitie;

    public Afspraak(String behandelingssoort, String voornaam, String achternaam, String afspraakdatum, String afspraaktijd, String artsnaam, String notitie) {
        this.behandelingssoort = behandelingssoort;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.afspraakdatum = afspraakdatum;
        this.afspraaktijd = afspraaktijd;
        this.artsnaam = artsnaam;
        this.notitie = notitie;
    }

    public String getBehandelingssoort() {
        return behandelingssoort;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getAfspraakdatum() {
        return afspraakdatum;
    }

    public String getAfspraaktijd() {
        return afspraaktijd;
    }

    public String getArtsnaam() {
        return artsnaam;
    }

    public String getNotitie() {
        return notitie;
    }
}



