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

    public UpdatePatientScherm(PatientService patientService, Patient patient, MedischAfspraakSysteem app) {
        this.patientService = patientService;
        this.patient = patient;
        this.app = app;
    }

    public void show() {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        TextField tfVoornaam = new TextField(patient != null ? patient.getVoornaam() : "");
        TextField tfAchternaam = new TextField(patient != null ? patient.getAchternaam() : "");
        DatePicker dpGeboortedatum = new DatePicker(patient != null ? patient.getGeboortedatum() : null);
        TextField tfEmail = new TextField(patient != null ? patient.getEmail() : "");

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

        root.getChildren().addAll(
                new Label("Voornaam"), tfVoornaam,
                new Label("Achternaam"), tfAchternaam,
                new Label("Geboortedatum"), dpGeboortedatum,
                new Label("Email"), tfEmail,
                btnSave
        );

        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
