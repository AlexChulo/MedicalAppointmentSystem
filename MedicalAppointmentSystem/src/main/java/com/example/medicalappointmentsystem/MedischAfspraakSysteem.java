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

public class MedischAfspraakSysteem extends Application {
    private UserService userService = new UserService();
    private AfspraakService afspraakService = new AfspraakService();

    @Override
    public void start(Stage primaryStage) {
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

        // Create the main content areas
        VBox contentAfspraak = createAfspraakContent();
        VBox contentAgenda = createAgendaContent();
        VBox contentDashboard = createDashboardContent();

        root.setLeft(sidebar);
        root.setCenter(contentAfspraak);

        btnAfspraak.setOnAction(e -> root.setCenter(contentAfspraak));
        btnAgenda.setOnAction(e -> root.setCenter(contentAgenda));
        btnDashboard.setOnAction(e -> root.setCenter(contentDashboard));

        Scene scene = new Scene(root, 800, 600);
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

        Label lblPatientnaam = new Label("Patiëntnaam:");
        TextField tfPatientnaam = new TextField();

        Label lblAfspraakdatum = new Label("Afspraakdatum:");
        DatePicker dpAfspraakdatum = new DatePicker();

        Label lblAfspraaktijd = new Label("Afspraaktijd:");
        ComboBox<String> cbAfspraaktijd = new ComboBox<>();
        cbAfspraaktijd.getItems().addAll("09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30");

        Label lblArtsnaam = new Label("Artsnaam:");
        ComboBox<String> cbArtsnaam = new ComboBox<>();
        cbArtsnaam.getItems().addAll("Dr. Jansen", "Dr. de Vries", "Dr. Bakker");

        Label lblNotitie = new Label("Notitie:");
        TextArea taNotitie = new TextArea();
        taNotitie.setPrefRowCount(5);

        Button btnSubmit = new Button("Opslaan");
        btnSubmit.setOnAction(e -> {
            String behandelingssoort = cbBehandelingssoort.getValue();
            String patientnaam = tfPatientnaam.getText();
            String afspraakdatum = dpAfspraakdatum.getValue().toString();
            String afspraaktijd = cbAfspraaktijd.getValue();
            String artsnaam = cbArtsnaam.getValue();
            String notitie = taNotitie.getText();

            Afspraak afspraak = new Afspraak(behandelingssoort, patientnaam, afspraakdatum, afspraaktijd, artsnaam, notitie);
        });

        content.getChildren().addAll(lblBehandelingssoort, cbBehandelingssoort, lblPatientnaam, tfPatientnaam, lblAfspraakdatum, dpAfspraakdatum, lblAfspraaktijd, cbAfspraaktijd, lblArtsnaam, cbArtsnaam, lblNotitie, taNotitie, btnSubmit);
        return content;
    }

    private VBox createAgendaContent() {
        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.setSpacing(10);

        TableView<Afspraak> tabel = new TableView<>();

        TableColumn<Afspraak, String> colBehandelingssoort = new TableColumn<>("Behandelingssoort");
        colBehandelingssoort.setCellValueFactory(new PropertyValueFactory<>("behandelingssoort"));

        TableColumn<Afspraak, String> colPatientnaam = new TableColumn<>("Patiëntnaam");
        colPatientnaam.setCellValueFactory(new PropertyValueFactory<>("patientnaam"));

        TableColumn<Afspraak, String> colAfspraakdatum = new TableColumn<>("Afspraakdatum");
        colAfspraakdatum.setCellValueFactory(new PropertyValueFactory<>("afspraakdatum"));

        TableColumn<Afspraak, String> colAfspraaktijd = new TableColumn<>("Afspraaktijd");
        colAfspraaktijd.setCellValueFactory(new PropertyValueFactory<>("afspraaktijd"));

        TableColumn<Afspraak, String> colArtsnaam = new TableColumn<>("Artsnaam");
        colArtsnaam.setCellValueFactory(new PropertyValueFactory<>("artsnaam"));

        TableColumn<Afspraak, String> colNotitie = new TableColumn<>("Notitie");
        colNotitie.setCellValueFactory(new PropertyValueFactory<>("notitie"));

        tabel.getColumns().addAll(colBehandelingssoort, colPatientnaam, colAfspraakdatum, colAfspraaktijd, colArtsnaam, colNotitie);

        content.getChildren().add(tabel);
        return content;
    }

    private VBox createDashboardContent() {
        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.setSpacing(10);

        Label lblTitle = new Label("Dashboard");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblArtsen = new Label("Aantal Artsen: " + userService.countUsersByRole("Arts"));
        Label lblReceptionisten = new Label("Aantal Receptionisten: " + userService.countUsersByRole("Receptionist"));
        Label lblZusters = new Label("Aantal Zusters: " + userService.countUsersByRole("Zuster"));

        content.getChildren().addAll(lblTitle, lblArtsen, lblReceptionisten, lblZusters);
        return content;
    }


    public static void main(String[] args) {
        launch(args);
    }
}






