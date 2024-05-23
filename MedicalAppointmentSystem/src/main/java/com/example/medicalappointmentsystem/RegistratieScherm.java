package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistratieScherm {
    private UserService userService;
    private Stage primaryStage;
    private Stage loginStage;

    public RegistratieScherm(UserService userService, Stage primaryStage, Stage loginStage) {
        this.userService = userService;
        this.primaryStage = primaryStage;
        this.loginStage = loginStage;
    }

    public void show() {
        Stage registerStage = new Stage();
        registerStage.setTitle("Registreren");

        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.setSpacing(10);

        Label lblVoornaam = new Label("Voornaam:");
        TextField tfVoornaam = new TextField();

        Label lblAchternaam = new Label("Achternaam:");
        TextField tfAchternaam = new TextField();

        Label lblPassword = new Label("Wachtwoord:");
        PasswordField pfPassword = new PasswordField();

        Label lblRole = new Label("Rol:");
        ComboBox<String> cbRole = new ComboBox<>();
        cbRole.getItems().addAll("Arts", "Receptionist", "Zuster");

        Button btnRegister = new Button("Registreren");
        btnRegister.setOnAction(e -> {
            String voornaam = tfVoornaam.getText();
            String achternaam = tfAchternaam.getText();
            String password = pfPassword.getText();
            String role = cbRole.getValue();

            if (userService.registerUser(voornaam, achternaam, password, role)) {
                showAlert(Alert.AlertType.INFORMATION, "Registratie succesvol!");
                registerStage.close();
                loginStage.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Registratie mislukt!");
            }
        });

        Button btnBackToLogin = new Button("Terug naar Login");
        btnBackToLogin.setOnAction(e -> {
            registerStage.close();
            loginStage.show();
        });

        content.getChildren().addAll(lblVoornaam, tfVoornaam, lblAchternaam, tfAchternaam, lblPassword, pfPassword, lblRole, cbRole, btnRegister, btnBackToLogin);
        Scene scene = new Scene(content, 300, 400);
        registerStage.setScene(scene);
        registerStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

