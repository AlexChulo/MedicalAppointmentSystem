package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class UpdateAfspraakScherm {
    private AfspraakService afspraakService;
    private Afspraak afspraak;
    private MedischAfspraakSysteem app;

    public UpdateAfspraakScherm(AfspraakService afspraakService, Afspraak afspraak, MedischAfspraakSysteem app) {
        this.afspraakService = afspraakService;
        this.afspraak = afspraak;
        this.app = app;
    }

    public void show() {
        Stage stage = new Stage();

        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        Label lblBehandelingssoort = new Label("Behandelingssoort:");
        ComboBox<String> cbBehandelingssoort = new ComboBox<>();
        cbBehandelingssoort.getItems().addAll("Consultatie", "Follow-up", "Routinecontrole");
        cbBehandelingssoort.setValue(afspraak.getBehandelingssoort());

        Label lblVoornaam = new Label("Patiënt Voornaam:");
        TextField tfVoornaam = new TextField(afspraak.getVoornaam());

        Label lblAchternaam = new Label("Patiënt Achternaam:");
        TextField tfAchternaam = new TextField(afspraak.getAchternaam());

        Label lblEmail = new Label("Patiënt Email:");
        TextField tfEmail = new TextField(afspraak.getEmail());

        Label lblGeboortedatum = new Label("Patiënt Geboortedatum:");
        DatePicker dpGeboortedatum = new DatePicker(afspraak.getGeboortedatum());

        Label lblAfspraakdatum = new Label("Afspraakdatum:");
        DatePicker dpAfspraakdatum = new DatePicker(afspraak.getAfspraakdatum());

        Label lblAfspraaktijd = new Label("Afspraaktijd:");
        ComboBox<String> cbAfspraaktijd = new ComboBox<>();
        cbAfspraaktijd.getItems().addAll("09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30");
        cbAfspraaktijd.setValue(afspraak.getAfspraaktijd());

        Label lblArtsnaam = new Label("Artsnaam:");
        ComboBox<String> cbArtsnaam = new ComboBox<>();
        app.loadArtsNames(cbArtsnaam);
        cbArtsnaam.setValue(afspraak.getArtsnaam());

        Label lblNotitie = new Label("Notitie:");
        TextArea taNotitie = new TextArea(afspraak.getNotitie());
        taNotitie.setPrefRowCount(5);

        Button btnSubmit = new Button("Update");
        btnSubmit.setOnAction(e -> {
            String behandelingssoort = cbBehandelingssoort.getValue();
            String voornaam = tfVoornaam.getText();
            String achternaam = tfAchternaam.getText();
            String email = tfEmail.getText();
            LocalDate geboortedatum = dpGeboortedatum.getValue();
            LocalDate afspraakdatum = dpAfspraakdatum.getValue();
            String afspraaktijd = cbAfspraaktijd.getValue();
            String artsnaam = cbArtsnaam.getValue();
            String notitie = taNotitie.getText();

            Afspraak updatedAfspraak = new Afspraak(behandelingssoort, voornaam, achternaam, afspraakdatum, afspraaktijd, artsnaam, notitie, email, geboortedatum);
            if (afspraakService.updateAfspraak(afspraak, updatedAfspraak)) {
                app.updateAgenda();
                stage.close();
                app.showAlert(Alert.AlertType.INFORMATION, "Afspraak succesvol geüpdatet!");
            } else {
                app.showAlert(Alert.AlertType.ERROR, "Updaten van afspraak mislukt!");
            }
        });

        root.getChildren().addAll(lblBehandelingssoort, cbBehandelingssoort, lblVoornaam, tfVoornaam, lblAchternaam, tfAchternaam, lblEmail, tfEmail, lblGeboortedatum, dpGeboortedatum, lblAfspraakdatum, dpAfspraakdatum, lblAfspraaktijd, cbAfspraaktijd, lblArtsnaam, cbArtsnaam, lblNotitie, taNotitie, btnSubmit);

        Scene scene = new Scene(root, 400, 600);
        stage.setTitle("Update Afspraak");
        stage.setScene(scene);
        stage.show();
    }
}
