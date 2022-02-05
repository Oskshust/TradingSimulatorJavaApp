module com.example.tradingsimapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tradingsimapp to javafx.fxml;
    exports com.example.tradingsimapp;
}