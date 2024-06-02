package com.example.medicalappointmentsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    // Methode om een nieuwe gebruiker te registreren
    public boolean registerUser(String voornaam, String achternaam, String username, String password, String role) {
        try (Connection conn = databaseConnector.getConnection()) {
            String query = "INSERT INTO users (voornaam, achternaam, username, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, voornaam);
            stmt.setString(2, achternaam);
            stmt.setString(3, username);
            stmt.setString(4, password);
            stmt.setString(5, role);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Methode om een gebruiker te authenticeren op basis van gebruikersnaam en wachtwoord
    public boolean authenticateUser(String username, String password) {
        try (Connection conn = databaseConnector.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Methode om alle namen van artsen op te halen
    public List<String> getAllArtsNames() {
        List<String> artsNames = new ArrayList<>();
        try (Connection conn = databaseConnector.getConnection()) {
            String query = "SELECT voornaam, achternaam FROM users WHERE role = 'Arts'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                artsNames.add(rs.getString("voornaam") + " " + rs.getString("achternaam"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artsNames;
    }

    // Methode om het aantal gebruikers per rol op te halen
    public int countUsersByRole(String role) {
        try (Connection conn = databaseConnector.getConnection()) {
            String query = "SELECT COUNT(*) AS count FROM users WHERE role = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}