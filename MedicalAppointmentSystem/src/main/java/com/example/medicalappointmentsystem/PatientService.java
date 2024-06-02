package com.example.medicalappointmentsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PatientService {
    private DatabaseConnector databaseConnector;
    private AfspraakService afspraakService;

    // Constructor om een PatientService object te initialiseren met een DatabaseConnector en een AfspraakService
    public PatientService(DatabaseConnector databaseConnector, AfspraakService afspraakService) {
        this.databaseConnector = databaseConnector;
        this.afspraakService = afspraakService;
    }

    // Haalt alle patiënten op uit de database en retourneert ze als een ObservableList
    public ObservableList<Patient> getAllPatienten() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        String query = "SELECT * FROM patienten";

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Itereer door de resultaten en maak patiëntobjecten op basis van de opgehaalde gegevens
            while (rs.next()) {
                int id = rs.getInt("id");
                String voornaam = rs.getString("voornaam");
                String achternaam = rs.getString("achternaam");
                String email = rs.getString("email");
                LocalDate geboortedatum = rs.getDate("geboortedatum").toLocalDate();

                Patient patient = new Patient(id, voornaam, achternaam, email, geboortedatum);
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    // Voegt een nieuwe patiënt toe aan de database
    public boolean addPatient(Patient patient) {
        String query = "INSERT INTO patienten (voornaam, achternaam, geboortedatum, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, patient.getVoornaam());
            pstmt.setString(2, patient.getAchternaam());
            pstmt.setDate(3, Date.valueOf(patient.getGeboortedatum()));
            pstmt.setString(4, patient.getEmail());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Werkt een bestaande patiënt bij in de database
    public boolean updatePatient(Patient patient) {
        if (patient.getId() == null) {
            System.out.println("Patient ID is null for patient: " + patient);
            throw new IllegalArgumentException("Patient ID cannot be null");
        }

        String query = "UPDATE patienten SET voornaam = ?, achternaam = ?, geboortedatum = ?, email = ? WHERE id = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, patient.getVoornaam());
            pstmt.setString(2, patient.getAchternaam());
            pstmt.setDate(3, Date.valueOf(patient.getGeboortedatum()));
            pstmt.setString(4, patient.getEmail());
            pstmt.setInt(5, patient.getId());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verwijdert een patiënt uit de database op basis van de opgegeven patiënt-ID
    public boolean deletePatient(int id) {
        String query = "DELETE FROM patienten WHERE id = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Voegt een behandeling toe voor een specifieke patiënt
    public boolean addBehandeling(int patientId, String behandelingNotitie) {
        String query = "INSERT INTO behandelingen (afspraak_id, behandeling_notitie) VALUES (?, ?)";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, patientId);
            pstmt.setString(2, behandelingNotitie);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Controleert of een patiënt al behandelingen heeft
    public boolean hasBehandeling(int patientId) {
        String query = "SELECT COUNT(*) FROM behandelingen WHERE afspraak_id = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, patientId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Roep de methode saveOrUpdatePatient van de afspraakService aan om een patiënt op te slaan of bij te werken
    public boolean saveOrUpdatePatient(String voornaam, String achternaam, String email, LocalDate geboortedatum) {
        return afspraakService.saveOrUpdatePatient(voornaam, achternaam, email, geboortedatum);
    }
}
