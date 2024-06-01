package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BehandelingScherm {
    private PatientService patientService;
    private Afspraak afspraak;
    private MedischAfspraakSysteem systeem;

    public BehandelingScherm(PatientService patientService, Afspraak afspraak, MedischAfspraakSysteem app) {
        this.patientService = patientService;
        this.afspraak = afspraak;
        this.systeem = app;
    }

    public void show() {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        TextArea taBehandeling = new TextArea();

        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> {
            String behandelingNotitie = taBehandeling.getText();
            if (!behandelingNotitie.isEmpty()) {
                patientService.addBehandeling(afspraak.getId(), behandelingNotitie);
                systeem.updateAgenda();
                stage.close();
            } else {
                showAlert(Alert.AlertType.WARNING, "Behandeling notitie mag niet leeg zijn.");
            }
        });

        root.getChildren().addAll(
                new Label("Behandeling Notitie"), taBehandeling,
                btnSave
        );

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }
}
