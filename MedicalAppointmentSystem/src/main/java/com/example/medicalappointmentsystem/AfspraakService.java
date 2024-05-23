package com.example.medicalappointmentsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class AfspraakService {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public boolean saveAfspraak(Afspraak afspraak) {
        String query = "INSERT INTO afspraken (behandelingssoort, voornaam, achternaam, afspraakdatum, afspraaktijd, artsnaam, notitie) VALUES (?, ?, ?, ?, ?, ?, ?)";

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

    public boolean updateAfspraak(Afspraak oldAfspraak, Afspraak newAfspraak) {
        String query = "UPDATE afspraken SET behandelingssoort = ?, voornaam = ?, achternaam = ?, afspraakdatum = ?, afspraaktijd = ?, artsnaam = ?, notitie = ? WHERE behandelingssoort = ? AND voornaam = ? AND achternaam = ? AND afspraakdatum = ? AND afspraaktijd = ? AND artsnaam = ? AND notitie = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newAfspraak.getBehandelingssoort());
            pstmt.setString(2, newAfspraak.getVoornaam());
            pstmt.setString(3, newAfspraak.getAchternaam());
            pstmt.setDate(4, Date.valueOf(newAfspraak.getAfspraakdatum()));
            pstmt.setTime(5, Time.valueOf(newAfspraak.getAfspraaktijd() + ":00"));
            pstmt.setString(6, newAfspraak.getArtsnaam());
            pstmt.setString(7, newAfspraak.getNotitie());

            pstmt.setString(8, oldAfspraak.getBehandelingssoort());
            pstmt.setString(9, oldAfspraak.getVoornaam());
            pstmt.setString(10, oldAfspraak.getAchternaam());
            pstmt.setDate(11, Date.valueOf(oldAfspraak.getAfspraakdatum()));
            pstmt.setTime(12, Time.valueOf(oldAfspraak.getAfspraaktijd() + ":00"));
            pstmt.setString(13, oldAfspraak.getArtsnaam());
            pstmt.setString(14, oldAfspraak.getNotitie());

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
                String afspraakdatum = rs.getDate("afspraakdatum").toString();
                String afspraaktijd = rs.getTime("afspraaktijd").toString().substring(0, 5);
                String artsnaam = rs.getString("artsnaam");
                String notitie = rs.getString("notitie");

                Afspraak afspraak = new Afspraak(behandelingssoort, voornaam, achternaam, afspraakdatum, afspraaktijd, artsnaam, notitie);
                afspraken.add(afspraak);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return afspraken;
    }

    public ObservableList<Patient> getAllPatienten() {
        ObservableList<Patient> patienten = FXCollections.observableArrayList();
        String query = "SELECT DISTINCT voornaam, achternaam FROM afspraken";

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String voornaam = rs.getString("voornaam");
                String achternaam = rs.getString("achternaam");

                Patient patient = new Patient(voornaam, achternaam);
                patienten.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patienten;
    }
}




