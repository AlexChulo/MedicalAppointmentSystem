package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// De klasse BehandelingScherm is verantwoordelijk voor het weergeven van het scherm waar een behandeling notitie kan worden toegevoegd
public class BehandelingScherm {
    private PatientService patientService;
    private Afspraak afspraak;
    private MedischAfspraakSysteem systeem;

    // Constructor voor het BehandelingScherm, ontvangt de benodigde services en objecten
    public BehandelingScherm(PatientService patientService, Afspraak afspraak, MedischAfspraakSysteem app) {
        this.patientService = patientService;
        this.afspraak = afspraak;
        this.systeem = app;
    }

    // Methode om het scherm te tonen
    public void show() {
        Stage stage = new Stage(); // Maak een nieuw venster (stage) aan
        VBox root = new VBox(10); // Maak een VBox layout met 10 pixels tussenruimte tussen de kinderen
        root.setPadding(new Insets(20)); // Stel de padding van de layout in

        TextArea taBehandeling = new TextArea(); // Maak een TextArea voor de behandeling notitie

        Button btnSave = new Button("Save"); // Maak een knop om de behandeling notitie op te slaan
        btnSave.setOnAction(e -> {
            String behandelingNotitie = taBehandeling.getText(); // Haal de tekst uit de TextArea
            if (!behandelingNotitie.isEmpty()) { // Controleer of de notitie niet leeg is
                patientService.addBehandeling(afspraak.getId(), behandelingNotitie); // Voeg de behandeling notitie toe via de patient service
                systeem.updateAgenda(); // Werk de agenda bij in het hoofdprogramma
                stage.close(); // Sluit het venster
            } else {
                showAlert(Alert.AlertType.WARNING, "Behandeling notitie mag niet leeg zijn."); // Toon een waarschuwing als de notitie leeg is
            }
        });

        // Voeg alle componenten toe aan de layout
        root.getChildren().addAll(
                new Label("Behandeling Notitie"), taBehandeling,
                btnSave
        );

        // Maak een nieuwe scene met de layout, stel de afmetingen in, en voeg deze toe aan het venster
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.showAndWait(); // Toon het venster en wacht tot het gesloten wordt
    }

    // Hulpmethode om een alert te tonen
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK); // Maak een nieuwe alert aan
        alert.showAndWait(); // Toon de alert en wacht tot de gebruiker een actie onderneemt
    }
}
