package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScherm {
    private UserService userService;
    private Stage primaryStage;

    public LoginScherm(UserService userService, Stage primaryStage) {
        this.userService = userService;
        this.primaryStage = primaryStage;
    }

    public void show() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.setSpacing(10);

        Label lblUsername = new Label("Voornaam:");
        TextField tfUsername = new TextField();

        Label lblPassword = new Label("Wachtwoord:");
        PasswordField pfPassword = new PasswordField();

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            String username = tfUsername.getText();
            String password = pfPassword.getText();

            if (userService.loginUser(username, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Login succesvol!");
                loginStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login mislukt!");
            }
        });

        Button btnShowRegister = new Button("Registreren");
        btnShowRegister.setOnAction(e -> {
            loginStage.hide();
            new RegistratieScherm(userService, primaryStage, loginStage).show();
        });

        content.getChildren().addAll(lblUsername, tfUsername, lblPassword, pfPassword, btnLogin, btnShowRegister);
        Scene scene = new Scene(content, 300, 400);
        loginStage.setScene(scene);
        loginStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

