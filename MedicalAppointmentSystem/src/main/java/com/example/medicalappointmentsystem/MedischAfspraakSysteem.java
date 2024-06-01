package com.example.medicalappointmentsystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MedischAfspraakSysteem extends Application {
    private PatientService patientService;
    private TableView<Patient> patientTabel;
    private UserService userService = new UserService();
    private AfspraakService afspraakService = new AfspraakService();
    private Stage primaryStage;

    private TableView<Afspraak> afspraakTableView;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        new LoginScherm(userService, primaryStage, this).show();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        patientService = new PatientService(databaseConnector, new AfspraakService());
    }

    public void showMainApp() {
        BorderPane root = new BorderPane();

        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #FF0000;");
        sidebar.setPrefWidth(200);
        sidebar.setSpacing(20);
        sidebar.setPadding(new Insets(20, 10, 20, 10));

        Button btnAfspraak = new Button("Afspraak");
        Button btnPatienten = new Button("Patienten");
        Button btnAgenda = new Button("Agenda");
        Button btnDashboard = new Button("Dashboard");

        sidebar.getChildren().addAll(btnAfspraak, btnPatienten, btnAgenda, btnDashboard);

        VBox contentAfspraak = createAfspraakContent();
        VBox contentPatienten = createPatientenContent();
        VBox contentAgenda = createAgendaContent();
        VBox contentDashboard = createDashboardContent();

        root.setLeft(sidebar);
        root.setCenter(contentAfspraak);

        btnAfspraak.setOnAction(e -> root.setCenter(contentAfspraak));
        btnPatienten.setOnAction(e -> root.setCenter(contentPatienten));
        btnAgenda.setOnAction(e -> root.setCenter(contentAgenda));
        btnDashboard.setOnAction(e -> root.setCenter(contentDashboard));

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Medisch Afspraak Systeem");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createAfspraakContent() {
        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.setSpacing(10);

        Label lblBehandelingssoort = new Label("Behandelingssoort:");
        ComboBox<String> cbBehandelingssoort = new ComboBox<>();
        cbBehandelingssoort.getItems().addAll("Consultatie", "Follow-up", "Routinecontrole");

        Label lblVoornaam = new Label("Patiënt Voornaam:");
        TextField tfVoornaam = new TextField();

        Label lblAchternaam = new Label("Patiënt Achternaam:");
        TextField tfAchternaam = new TextField();

        Label lblEmail = new Label("Patiënt Email:");
        TextField tfEmail = new TextField();

        Label lblGeboortedatum = new Label("Patiënt Geboortedatum:");
        DatePicker dpGeboortedatum = new DatePicker();

        Label lblAfspraakdatum = new Label("Afspraakdatum:");
        DatePicker dpAfspraakdatum = new DatePicker();

        Label lblAfspraaktijd = new Label("Afspraaktijd:");
        ComboBox<String> cbAfspraaktijd = new ComboBox<>();
        cbAfspraaktijd.getItems().addAll("09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30");

        Label lblArtsnaam = new Label("Artsnaam:");
        ComboBox<String> cbArtsnaam = new ComboBox<>();
        loadArtsNames(cbArtsnaam);

        Label lblNotitie = new Label("Notitie:");
        TextArea taNotitie = new TextArea();
        taNotitie.setPrefRowCount(5);

        Button btnSubmit = new Button("Opslaan");
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

            Afspraak afspraak = new Afspraak(behandelingssoort, voornaam, achternaam, afspraakdatum, afspraaktijd, artsnaam, notitie, email, geboortedatum);

            if (afspraakService.saveAfspraak(afspraak)) {
                showAlert(Alert.AlertType.INFORMATION, "Afspraak succesvol opgeslagen!");
                clearAfspraakFields(cbBehandelingssoort, tfVoornaam, tfAchternaam, tfEmail, dpGeboortedatum, dpAfspraakdatum, cbAfspraaktijd, cbArtsnaam, taNotitie);
                updateAgenda();
            } else {
                showAlert(Alert.AlertType.ERROR, "Opslaan van afspraak mislukt!");
            }
        });

        content.getChildren().addAll(lblBehandelingssoort, cbBehandelingssoort, lblVoornaam, tfVoornaam, lblAchternaam, tfAchternaam, lblEmail, tfEmail, lblGeboortedatum, dpGeboortedatum, lblAfspraakdatum, dpAfspraakdatum, lblAfspraaktijd, cbAfspraaktijd, lblArtsnaam, cbArtsnaam, lblNotitie, taNotitie, btnSubmit);
        return content;
    }

    public void loadArtsNames(ComboBox<String> cbArtsnaam) {
        ObservableList<String> artsNames = FXCollections.observableArrayList(userService.getAllArtsNames());
        cbArtsnaam.setItems(artsNames);
    }

    private VBox createAgendaContent() {
        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.setSpacing(10);

        afspraakTableView = new TableView<>();
        afspraakTableView.setItems(afspraakService.getAllAfspraken());

        TableColumn<Afspraak, String> colBehandelingssoort = new TableColumn<>("Behandelingssoort");
        colBehandelingssoort.setCellValueFactory(new PropertyValueFactory<>("behandelingssoort"));

        TableColumn<Afspraak, String> colVoornaam = new TableColumn<>("Patiënt Voornaam");
        colVoornaam.setCellValueFactory(new PropertyValueFactory<>("voornaam"));

        TableColumn<Afspraak, String> colAchternaam = new TableColumn<>("Patiënt Achternaam");
        colAchternaam.setCellValueFactory(new PropertyValueFactory<>("achternaam"));

        TableColumn<Afspraak, LocalDate> colAfspraakdatum = new TableColumn<>("Afspraakdatum");
        colAfspraakdatum.setCellValueFactory(new PropertyValueFactory<>("afspraakdatum"));

        TableColumn<Afspraak, String> colAfspraaktijd = new TableColumn<>("Afspraaktijd");
        colAfspraaktijd.setCellValueFactory(new PropertyValueFactory<>("afspraaktijd"));

        TableColumn<Afspraak, String> colArtsnaam = new TableColumn<>("Artsnaam");
        colArtsnaam.setCellValueFactory(new PropertyValueFactory<>("artsnaam"));

        TableColumn<Afspraak, String> colNotitie = new TableColumn<>("Notitie");
        colNotitie.setCellValueFactory(new PropertyValueFactory<>("notitie"));

        TableColumn<Afspraak, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Afspraak, LocalDate> colGeboortedatum = new TableColumn<>("Geboortedatum");
        colGeboortedatum.setCellValueFactory(new PropertyValueFactory<>("geboortedatum"));

        afspraakTableView.getColumns().addAll(colBehandelingssoort, colVoornaam, colAchternaam, colAfspraakdatum, colAfspraaktijd, colArtsnaam, colNotitie, colEmail, colGeboortedatum);

        // Add CRUD buttons
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");


        btnUpdate.setOnAction(e -> {
            Afspraak selectedAfspraak = afspraakTableView.getSelectionModel().getSelectedItem();
            if (selectedAfspraak != null) {
                new UpdateAfspraakScherm(afspraakService, selectedAfspraak, this).show();
            } else {
                showAlert(Alert.AlertType.WARNING, "Selecteer een afspraak om te updaten.");
            }
        });

        btnDelete.setOnAction(e -> {
            Afspraak selectedAfspraak = afspraakTableView.getSelectionModel().getSelectedItem();
            if (selectedAfspraak != null) {
                if (afspraakService.deleteAfspraak(selectedAfspraak)) {
                    showAlert(Alert.AlertType.INFORMATION, "Afspraak succesvol verwijderd!");
                    updateAgenda();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Verwijderen van afspraak mislukt!");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Selecteer een afspraak om te verwijderen.");
            }
        });

        content.getChildren().addAll(afspraakTableView, btnUpdate, btnDelete);
        return content;
    }

    public void updatePatienten() {
        patientTabel.setItems(patientService.getAllPatienten());
    }

    private VBox createPatientenContent() {
        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.setSpacing(10);

        patientTabel = new TableView<>();
        patientTabel.setItems(patientService.getAllPatienten());

        TableColumn<Patient, String> colVoornaam = new TableColumn<>("Voornaam");
        colVoornaam.setCellValueFactory(new PropertyValueFactory<>("voornaam"));

        TableColumn<Patient, String> colAchternaam = new TableColumn<>("Achternaam");
        colAchternaam.setCellValueFactory(new PropertyValueFactory<>("achternaam"));

        TableColumn<Patient, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Patient, LocalDate> colGeboortedatum = new TableColumn<>("Geboortedatum");
        colGeboortedatum.setCellValueFactory(new PropertyValueFactory<>("geboortedatum"));

        patientTabel.getColumns().addAll(colVoornaam, colAchternaam, colEmail, colGeboortedatum);

        Button btnAdd = new Button("Add");
        btnAdd.setOnAction(e -> new UpdatePatientScherm(patientService, null, this).show());

        Button btnUpdate = new Button("Update");
        btnUpdate.setOnAction(e -> {
            Patient selectedPatient = patientTabel.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                new UpdatePatientScherm(patientService, selectedPatient, this).show();
            } else {
                showAlert(Alert.AlertType.WARNING, "Selecteer een patiënt om te updaten.");
            }
        });

        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> {
            Patient selectedPatient = patientTabel.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                if (patientService.deletePatient(selectedPatient.getId())) {
                    showAlert(Alert.AlertType.INFORMATION, "Patiënt succesvol verwijderd!");
                    updatePatienten();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Verwijderen van patiënt mislukt!");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Selecteer een patiënt om te verwijderen.");
            }
        });

        HBox buttons = new HBox(10, btnAdd, btnUpdate, btnDelete);

        content.getChildren().addAll(patientTabel, buttons);
        return content;
    }

    private VBox createDashboardContent() {
        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.setSpacing(10);

        int aantalArtsen = userService.countUsersByRole("Arts");
        int aantalReceptionisten = userService.countUsersByRole("Receptionist");
        int aantalZusters = userService.countUsersByRole("Zuster");

        Label lblArtsen = new Label("Aantal artsen: " + aantalArtsen);
        Label lblReceptionisten = new Label("Aantal receptionisten: " + aantalReceptionisten);
        Label lblZusters = new Label("Aantal zusters: " + aantalZusters);

        content.getChildren().addAll(lblArtsen, lblReceptionisten, lblZusters);
        return content;
    }

    private void clearAfspraakFields(ComboBox<String> cbBehandelingssoort, TextField tfVoornaam, TextField tfAchternaam, TextField tfEmail, DatePicker dpGeboortedatum, DatePicker dpAfspraakdatum, ComboBox<String> cbAfspraaktijd, ComboBox<String> cbArtsnaam, TextArea taNotitie) {
        cbBehandelingssoort.setValue(null);
        tfVoornaam.clear();
        tfAchternaam.clear();
        tfEmail.clear();
        dpGeboortedatum.setValue(null);
        dpAfspraakdatum.setValue(null);
        cbAfspraaktijd.setValue(null);
        cbArtsnaam.setValue(null);
        taNotitie.clear();
    }

    public void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateAgenda() {
        afspraakTableView.setItems(afspraakService.getAllAfspraken());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
