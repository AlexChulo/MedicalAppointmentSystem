package com.example.medicalappointmentsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

// AfspraakService is verantwoordelijk voor het afhandelen van operaties met betrekking tot afspraken
public class AfspraakService {
    // Logger instantie voor het loggen van gebeurtenissen
    private static final Logger logger = Logger.getLogger(AfspraakService.class.getName());

    // Database connector instantie voor het maken van verbindingen
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    // Slaat een afspraak op en retourneert true als succesvol, anders false
    public boolean saveAfspraak(Afspraak afspraak) {
        // Zorg ervoor dat de patiënt bestaat of wordt opgeslagen voordat de afspraak wordt opgeslagen
        if (!saveOrUpdatePatient(afspraak.getVoornaam(), afspraak.getAchternaam(), afspraak.getEmail(), afspraak.getGeboortedatum())) {
            return false;
        }

        // SQL query om een nieuwe afspraak in de database in te voegen
        String query = "INSERT INTO afspraken (behandelingssoort, voornaam, achternaam, afspraakdatum, afspraaktijd, artsnaam, notitie, email, geboortedatum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Stel parameters in voor de prepared statement
            pstmt.setString(1, afspraak.getBehandelingssoort());
            pstmt.setString(2, afspraak.getVoornaam());
            pstmt.setString(3, afspraak.getAchternaam());
            pstmt.setDate(4, Date.valueOf(afspraak.getAfspraakdatum()));
            pstmt.setTime(5, Time.valueOf(afspraak.getAfspraaktijd() + ":00"));
            pstmt.setString(6, afspraak.getArtsnaam());
            pstmt.setString(7, afspraak.getNotitie());
            pstmt.setString(8, afspraak.getEmail());
            pstmt.setDate(9, Date.valueOf(afspraak.getGeboortedatum()));

            // Voer de update uit
            pstmt.executeUpdate();
            logger.info("Afspraak succesvol opgeslagen: " + afspraak);
            return true;
        } catch (SQLException e) {
            logger.severe("Fout bij het opslaan van afspraak: " + e.getMessage());
            return false;
        }
    }

    // Slaat patiëntinformatie op of werkt deze bij en retourneert true als succesvol, anders false
    public boolean saveOrUpdatePatient(String voornaam, String achternaam, String email, LocalDate geboortedatum) {
        // SQL query om te controleren of de patiënt al bestaat
        String query = "SELECT COUNT(*) FROM patienten WHERE voornaam = ? AND achternaam = ?";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, voornaam);
            pstmt.setString(2, achternaam);

            ResultSet rs = pstmt.executeQuery();
            // Als de patiënt bestaat, retourneer true
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Patiënt bestaat al
            }

            // SQL query om een nieuwe patiënt in de database in te voegen
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

    // Haalt een lijst van alle patiënten uit de database
    public ObservableList<Patient> getAllPatienten() {
        ObservableList<Patient> patienten = FXCollections.observableArrayList();
        String query = "SELECT * FROM patienten";
        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Loop door de result set en voeg elke patiënt toe aan de lijst
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

    // Werkt een bestaande afspraak bij en retourneert true als succesvol, anders false
    public boolean updateAfspraak(Afspraak oldAfspraak, Afspraak newAfspraak) {
        // SQL query om een afspraak in de database bij te werken
        String query = "UPDATE afspraken SET behandelingssoort = ?, voornaam = ?, achternaam = ?, afspraakdatum = ?, afspraaktijd = ?, artsnaam = ?, notitie = ?, email = ?, geboortedatum = ? WHERE behandelingssoort = ? AND voornaam = ? AND achternaam = ? AND afspraakdatum = ? AND afspraaktijd = ? AND artsnaam = ? AND notitie = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Stel parameters in voor de prepared statement
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

    // Verwijdert een afspraak en retourneert true als succesvol, anders false
    public boolean deleteAfspraak(Afspraak afspraak) {
        // SQL query om een afspraak uit de database te verwijderen
        String query = "DELETE FROM afspraken WHERE behandelingssoort = ? AND voornaam = ? AND achternaam = ? AND afspraakdatum = ? AND afspraaktijd = ? AND artsnaam = ? AND notitie = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Stel parameters in voor de prepared statement
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

    // Haalt een lijst van alle afspraken uit de database
    public ObservableList<Afspraak> getAllAfspraken() {
        ObservableList<Afspraak> afspraken = FXCollections.observableArrayList();
        String query = "SELECT * FROM afspraken";

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Loop door de result set en voeg elke afspraak toe aan de lijst
            while (rs.next()) {
                int id = rs.getInt("id");
                String behandelingssoort = rs.getString("behandelingssoort");
                String voornaam = rs.getString("voornaam");
                String achternaam = rs.getString("achternaam");
                LocalDate afspraakdatum = rs.getDate("afspraakdatum") != null ? rs.getDate("afspraakdatum").toLocalDate() : null;
                String afspraaktijd = rs.getTime("afspraaktijd") != null ? rs.getTime("afspraaktijd").toString().substring(0, 5) : null;
                String artsnaam = rs.getString("artsnaam");
                String notitie = rs.getString("notitie");
                String email = rs.getString("email");
                LocalDate geboortedatum = rs.getDate("geboortedatum") != null ? rs.getDate("geboortedatum").toLocalDate() : null;

                Afspraak afspraak = new Afspraak(id, behandelingssoort, voornaam, achternaam, afspraakdatum, afspraaktijd, artsnaam, notitie, email, geboortedatum);
                afspraken.add(afspraak);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return afspraken;
    }
}
