package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginScherm {
    private UserService userService;
    private Stage primaryStage;
    private MedischAfspraakSysteem mainApp;

    public LoginScherm(UserService userService, Stage primaryStage, MedischAfspraakSysteem mainApp) {
        this.userService = userService;
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
    }

    public void show() {
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Username");

        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            String username = tfUsername.getText();
            String password = pfPassword.getText();

            if (userService.authenticateUser(username, password)) {
                mainApp.showMainApp();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login mislukt! Verkeerde username of password.");
            }
        });

        Button btnRegister = new Button("Register");
        btnRegister.setOnAction(e -> new RegistratieScherm(userService, primaryStage, mainApp).show());

        root.getChildren().addAll(tfUsername, pfPassword, btnLogin, btnRegister);

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}




