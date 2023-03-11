module com.example.examenmariandaria {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.examenmariandaria to javafx.fxml;
    exports com.example.examenmariandaria;
    exports com.example.examenmariandaria.controllers;
    exports com.example.examenmariandaria.domain;
    exports com.example.examenmariandaria.repository;
    exports com.example.examenmariandaria.service;
    opens com.example.examenmariandaria.controllers to javafx.fxml;
    opens com.example.examenmariandaria.domain;
    opens com.example.examenmariandaria.repository;
    opens com.example.examenmariandaria.service;
}