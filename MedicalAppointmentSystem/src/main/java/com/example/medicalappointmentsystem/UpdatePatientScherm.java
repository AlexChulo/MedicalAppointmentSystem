package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdatePatientScherm {
    private PatientService patientService;
    private Patient patient;
    private MedischAfspraakSysteem app;

    // Constructor om een UpdatePatientScherm object te initialiseren met een PatientService, een patient en een MedischAfspraakSysteem
    public UpdatePatientScherm(PatientService patientService, Patient patient, MedischAfspraakSysteem app) {
        this.patientService = patientService;
        this.patient = patient;
        this.app = app;
    }

    // Methode om het scherm voor het bijwerken van de patient te tonen
    public void show() {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        // TextFields en DatePicker voor het invoeren van patiëntgegevens
        TextField tfVoornaam = new TextField(patient != null ? patient.getVoornaam() : "");
        TextField tfAchternaam = new TextField(patient != null ? patient.getAchternaam() : "");
        DatePicker dpGeboortedatum = new DatePicker(patient != null ? patient.getGeboortedatum() : null);
        TextField tfEmail = new TextField(patient != null ? patient.getEmail() : "");

        // Knop om patiëntgegevens op te slaan
        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> {
            if (patient == null) {
                // Voeg nieuwe patiënt toe
                boolean newPatient = patientService.saveOrUpdatePatient(tfVoornaam.getText(), tfAchternaam.getText(), tfEmail.getText(), dpGeboortedatum.getValue());
                if (newPatient) {
                    app.updatePatienten();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Toevoegen van patiënt mislukt!");
                }
            } else {
                // Update bestaande patiënt
                patient.setVoornaam(tfVoornaam.getText());
                patient.setAchternaam(tfAchternaam.getText());
                patient.setGeboortedatum(dpGeboortedatum.getValue());
                patient.setEmail(tfEmail.getText());
                if (patientService.updatePatient(patient)) {
                    app.updatePatienten();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Updaten van patiënt mislukt!");
                }
            }
            stage.close();
        });

        // Voeg alle elementen toe aan de root VBox
        root.getChildren().addAll(
                new Label("Voornaam"), tfVoornaam,
                new Label("Achternaam"), tfAchternaam,
                new Label("Geboortedatum"), dpGeboortedatum,
                new Label("Email"), tfEmail,
                btnSave
        );

        // Maak een scene en toon het scherm op het stage
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }

    // Methode om een waarschuwing weer te geven
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}