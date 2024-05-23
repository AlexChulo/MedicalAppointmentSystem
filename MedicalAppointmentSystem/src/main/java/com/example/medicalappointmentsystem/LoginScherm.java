package com.example.medicalappointmentsystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScherm {
    private UserService userService;
    private Stage stage;
    private MedischAfspraakSysteem app;

    public LoginScherm(UserService userService, Stage stage, MedischAfspraakSysteem app) {
        this.userService = userService;
        this.stage = stage;
        this.app = app;
    }

    public void show() {
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        Label lblUsername = new Label("Username:");
        TextField tfUsername = new TextField();

        Label lblPassword = new Label("Password:");
        PasswordField pfPassword = new PasswordField();

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            String username = tfUsername.getText();
            String password = pfPassword.getText();

            if (userService.validateLogin(username, password)) {
                app.showMainApp();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login mislukt!");
            }
        });

        Button btnRegister = new Button("Register");
        btnRegister.setOnAction(e -> new RegistratieScherm(userService, stage, app).show());

        root.getChildren().addAll(lblUsername, tfUsername, lblPassword, pfPassword, btnLogin, btnRegister);

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



