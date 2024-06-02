package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

// De klasse LoginScherm is verantwoordelijk voor het weergeven van het inlogscherm
public class LoginScherm {
    private UserService userService;
    private Stage primaryStage;
    private MedischAfspraakSysteem mainApp;

    // Constructor voor het LoginScherm, ontvangt de benodigde services en objecten
    public LoginScherm(UserService userService, Stage primaryStage, MedischAfspraakSysteem mainApp) {
        this.userService = userService;
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
    }

    // Methode om het scherm te tonen
    public void show() {
        VBox root = new VBox(); // Maak een VBox layout aan
        root.setPadding(new Insets(20)); // Stel de padding van de layout in
        root.setSpacing(10); // Stel de ruimte tussen de elementen in

        TextField tfUsername = new TextField(); // Maak een tekstveld voor de gebruikersnaam aan
        tfUsername.setPromptText("Username"); // Stel de prompttekst in

        PasswordField pfPassword = new PasswordField(); // Maak een wachtwoordveld aan
        pfPassword.setPromptText("Password"); // Stel de prompttekst in

        Button btnLogin = new Button("Login"); // Maak een knop aan om in te loggen
        btnLogin.setOnAction(e -> {
            String username = tfUsername.getText(); // Haal de ingevoerde gebruikersnaam op
            String password = pfPassword.getText(); // Haal het ingevoerde wachtwoord op

            if (userService.authenticateUser(username, password)) { // Controleer de gebruikersgegevens via de userService
                mainApp.showMainApp(); // Toon de hoofdapplicatie als de inloggegevens correct zijn
            } else {
                showAlert(Alert.AlertType.ERROR, "Login mislukt! Verkeerde username of password."); // Toon een foutmelding bij verkeerde inloggegevens
            }
        });

        Button btnRegister = new Button("Register"); // Maak een knop aan om te registreren
        btnRegister.setOnAction(e -> new RegistratieScherm(userService, primaryStage, mainApp).show()); // Toon het registratiescherm bij klikken

        // Voeg alle componenten toe aan de layout
        root.getChildren().addAll(tfUsername, pfPassword, btnLogin, btnRegister);

        // Maak een nieuwe scene met de layout, stel de afmetingen in, en voeg deze toe aan het venster
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("Login"); // Stel de titel van het venster in
        primaryStage.setScene(scene); // Voeg de scene toe aan het venster
        primaryStage.show(); // Toon het venster
    }

    // Hulpmethode om een alert te tonen
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType); // Maak een nieuwe alert aan
        alert.setContentText(message); // Stel de berichttekst in
        alert.showAndWait(); // Toon de alert en wacht tot de gebruiker een actie onderneemt
    }
}