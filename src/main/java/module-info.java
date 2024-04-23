module co.edu.uniquindio.storify {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires javafx.web;
    requires com.google.api.services.youtube;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.jackson2;
    exports co.edu.uniquindio.storify.app;


    opens co.edu.uniquindio.storify to javafx.fxml;
    exports co.edu.uniquindio.storify;
}