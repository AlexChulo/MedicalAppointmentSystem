package com.example.medicalappointmentsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public boolean registerUser(String voornaam, String achternaam, String username, String password, String role) {
        String query = "INSERT INTO users (voornaam, achternaam, username, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, voornaam);
            pstmt.setString(2, achternaam);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, role);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countUsersByRole(String role) {
        String query = "SELECT COUNT(*) FROM users WHERE role = ?";
        int count = 0;

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, role);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}




