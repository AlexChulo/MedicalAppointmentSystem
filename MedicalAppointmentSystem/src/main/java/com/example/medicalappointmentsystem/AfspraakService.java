package com.example.medicalappointmentsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class AfspraakService {
    private static final Logger logger = Logger.getLogger(AfspraakService.class.getName());
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public boolean saveAfspraak(Afspraak afspraak) {
        if (!saveOrUpdatePatient(afspraak.getVoornaam(), afspraak.getAchternaam(), afspraak.getEmail(), afspraak.getGeboortedatum())) {
            return false;
        }

        String query = "INSERT INTO afspraken (behandelingssoort, voornaam, achternaam, afspraakdatum, afspraaktijd, artsnaam, notitie, email, geboortedatum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, afspraak.getBehandelingssoort());
            pstmt.setString(2, afspraak.getVoornaam());
            pstmt.setString(3, afspraak.getAchternaam());
            pstmt.setDate(4, Date.valueOf(afspraak.getAfspraakdatum()));
            pstmt.setTime(5, Time.valueOf(afspraak.getAfspraaktijd() + ":00"));
            pstmt.setString(6, afspraak.getArtsnaam());
            pstmt.setString(7, afspraak.getNotitie());
            pstmt.setString(8, afspraak.getEmail());
            pstmt.setDate(9, Date.valueOf(afspraak.getGeboortedatum()));

            pstmt.executeUpdate();
            logger.info("Afspraak succesvol opgeslagen: " + afspraak);
            return true;
        } catch (SQLException e) {
            logger.severe("Fout bij het opslaan van afspraak: " + e.getMessage());
            return false;
        }
    }

    private boolean saveOrUpdatePatient(String voornaam, String achternaam, String email, LocalDate geboortedatum) {
        String query = "SELECT COUNT(*) FROM patienten WHERE voornaam = ? AND achternaam = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, voornaam);
            pstmt.setString(2, achternaam);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Patiënt bestaat al
            }

            // Voeg patiënt toe
            String insertQuery = "INSERT INTO patienten (voornaam, achternaam, email, geboortedatum) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertPstmt = conn.prepareStatement(insertQuery)) {
                insertPstmt.setString(1, voornaam);
                insertPstmt.setString(2, achternaam);
                insertPstmt.setString(3, email);
                insertPstmt.setDate(4, Date.valueOf(geboortedatum));
                insertPstmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Fout bij het opslaan of bijwerken van patiënt: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<Patient> getAllPatienten() {
        ObservableList<Patient> patienten = FXCollections.observableArrayList();
        String query = "SELECT * FROM patienten";
        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("voornaam"),
                        rs.getString("achternaam"),
                        rs.getString("email"),
                        rs.getDate("geboortedatum").toLocalDate()
                );
                patienten.add(patient);
            }
        } catch (SQLException e) {
            logger.severe("Fout bij het ophalen van patiënten: " + e.getMessage());
        }
        return patienten;
    }

    public boolean updateAfspraak(Afspraak oldAfspraak, Afspraak newAfspraak) {
        String query = "UPDATE afspraken SET behandelingssoort = ?, voornaam = ?, achternaam = ?, afspraakdatum = ?, afspraaktijd = ?, artsnaam = ?, notitie = ?, email = ?, geboortedatum = ? WHERE behandelingssoort = ? AND voornaam = ? AND achternaam = ? AND afspraakdatum = ? AND afspraaktijd = ? AND artsnaam = ? AND notitie = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newAfspraak.getBehandelingssoort());
            pstmt.setString(2, newAfspraak.getVoornaam());
            pstmt.setString(3, newAfspraak.getAchternaam());
            pstmt.setDate(4, Date.valueOf(newAfspraak.getAfspraakdatum()));
            pstmt.setTime(5, Time.valueOf(newAfspraak.getAfspraaktijd() + ":00"));
            pstmt.setString(6, newAfspraak.getArtsnaam());
            pstmt.setString(7, newAfspraak.getNotitie());
            pstmt.setString(8, newAfspraak.getEmail());
            pstmt.setDate(9, Date.valueOf(newAfspraak.getGeboortedatum()));

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAfspraak(Afspraak afspraak) {
        String query = "DELETE FROM afspraken WHERE behandelingssoort = ? AND voornaam = ? AND achternaam = ? AND afspraakdatum = ? AND afspraaktijd = ? AND artsnaam = ? AND notitie = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, afspraak.getBehandelingssoort());
            pstmt.setString(2, afspraak.getVoornaam());
            pstmt.setString(3, afspraak.getAchternaam());
            pstmt.setDate(4, Date.valueOf(afspraak.getAfspraakdatum()));
            pstmt.setTime(5, Time.valueOf(afspraak.getAfspraaktijd() + ":00"));
            pstmt.setString(6, afspraak.getArtsnaam());
            pstmt.setString(7, afspraak.getNotitie());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Afspraak> getAllAfspraken() {
        ObservableList<Afspraak> afspraken = FXCollections.observableArrayList();
        String query = "SELECT * FROM afspraken";

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String behandelingssoort = rs.getString("behandelingssoort");
                String voornaam = rs.getString("voornaam");
                String achternaam = rs.getString("achternaam");
                LocalDate afspraakdatum = rs.getDate("afspraakdatum") != null ? rs.getDate("afspraakdatum").toLocalDate() : null;
                String afspraaktijd = rs.getTime("afspraaktijd") != null ? rs.getTime("afspraaktijd").toString().substring(0, 5) : null;
                String artsnaam = rs.getString("artsnaam");
                String notitie = rs.getString("notitie");
                String email = rs.getString("email");
                LocalDate geboortedatum = rs.getDate("geboortedatum") != null ? rs.getDate("geboortedatum").toLocalDate() : null;

                Afspraak afspraak = new Afspraak(behandelingssoort, voornaam, achternaam, afspraakdatum, afspraaktijd, artsnaam, notitie, email, geboortedatum);
                afspraken.add(afspraak);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return afspraken;
    }

}