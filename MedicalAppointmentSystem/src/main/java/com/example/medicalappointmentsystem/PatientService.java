package com.example.medicalappointmentsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PatientService {
    private DatabaseConnector databaseConnector;
    private AfspraakService afspraakService;

    public PatientService(DatabaseConnector databaseConnector, AfspraakService afspraakService) {
        this.databaseConnector = databaseConnector;
        this.afspraakService = afspraakService;
    }

    public ObservableList<Patient> getAllPatienten() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        String query = "SELECT * FROM patienten";

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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

    public boolean saveOrUpdatePatient(String voornaam, String achternaam, String email, LocalDate geboortedatum) {
        return afspraakService.saveOrUpdatePatient(voornaam, achternaam, email, geboortedatum);
    }
}
