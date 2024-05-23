package com.example.medicalappointmentsystem;

public class Afspraak {
    private String behandelingssoort;
    private String patientnaam;

    public String getBehandelingssoort() {
        return behandelingssoort;
    }

    public void setBehandelingssoort(String behandelingssoort) {
        this.behandelingssoort = behandelingssoort;
    }

    public String getPatientnaam() {
        return patientnaam;
    }

    public void setPatientnaam(String patientnaam) {
        this.patientnaam = patientnaam;
    }

    public String getAfspraakdatum() {
        return afspraakdatum;
    }

    public void setAfspraakdatum(String afspraakdatum) {
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

    private String afspraakdatum;
    private String afspraaktijd;
    private String artsnaam;
    private String notitie;

    public Afspraak(String behandelingssoort, String patientnaam, String afspraakdatum, String afspraaktijd, String artsnaam, String notitie) {
        this.behandelingssoort = behandelingssoort;
        this.patientnaam = patientnaam;
        this.afspraakdatum = afspraakdatum;
        this.afspraaktijd = afspraaktijd;
        this.artsnaam = artsnaam;
        this.notitie = notitie;
    }


}

