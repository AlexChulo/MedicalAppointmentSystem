module com.example.medicalappointmentsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.medicalappointmentsystem to javafx.fxml;
    exports com.example.medicalappointmentsystem;
}