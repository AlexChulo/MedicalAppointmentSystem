module com.example.medicalappointmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.medicalappointmentsystem to javafx.fxml;
    exports com.example.medicalappointmentsystem;
}