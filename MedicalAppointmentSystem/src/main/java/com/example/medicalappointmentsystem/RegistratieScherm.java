package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistratieScherm {
    private UserService userService;
    private Stage primaryStage;
    private MedischAfspraakSysteem mainApp;

    public RegistratieScherm(UserService userService, Stage primaryStage, MedischAfspraakSysteem mainApp) {
        this.userService = userService;
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
    }

    public void show() {
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        Label lblVoornaam = new Label("Voornaam:");
        TextField tfVoornaam = new TextField();
        tfVoornaam.setPromptText("Voornaam");

        Label lblAchternaam = new Label("Achternaam:");
        TextField tfAchternaam = new TextField();
        tfAchternaam.setPromptText("Achternaam");

        Label lblUsername = new Label("Username:");
        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Username");

        Label lblPassword = new Label("Password:");
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");

        Label lblRole = new Label("Role:");
        ComboBox<String> cbRole = new ComboBox<>();
        cbRole.getItems().addAll("Arts", "Receptionist", "Zuster");

        Button btnRegister = new Button("Register");
        btnRegister.setOnAction(e -> {
            String voornaam = tfVoornaam.getText();
            String achternaam = tfAchternaam.getText();
            String username = tfUsername.getText();
            String password = pfPassword.getText();
            String role = cbRole.getValue();

            if (userService.registerUser(voornaam, achternaam, username, password, role)) {
                showAlert(Alert.AlertType.INFORMATION, "Registratie succesvol!");
                new LoginScherm(userService, primaryStage, mainApp).show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Registratie mislukt!");
            }
        });

        Button btnBack = new Button("Back to Login");
        btnBack.setOnAction(e -> new LoginScherm(userService, primaryStage, mainApp).show());

        root.getChildren().addAll(lblVoornaam, tfVoornaam, lblAchternaam, tfAchternaam, lblUsername, tfUsername, lblPassword, pfPassword, lblRole, cbRole, btnRegister, btnBack);

        Scene scene = new Scene(root, 300, 600);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}