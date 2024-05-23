package com.example.medicalappointmentsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AfspraakService {
    public boolean saveAfspraak(Afspraak afspraak) {
        String sql = "INSERT INTO afspraken(behandelingssoort, patientnaam, afspraakdatum, afspraaktijd, artsnaam, notitie) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, afspraak.getBehandelingssoort());
            pstmt.setString(2, afspraak.getPatientnaam());
            pstmt.setString(3, afspraak.getAfspraakdatum());
            pstmt.setString(4, afspraak.getAfspraaktijd());
            pstmt.setString(5, afspraak.getArtsnaam());
            pstmt.setString(6, afspraak.getNotitie());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

