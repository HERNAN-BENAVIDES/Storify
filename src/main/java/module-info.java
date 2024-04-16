module co.edu.uniquindio.storify {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires javafx.web;


    opens co.edu.uniquindio.storify to javafx.fxml;
    exports co.edu.uniquindio.storify;
}