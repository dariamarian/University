module com.example.proiect {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.apache.logging.log4j;

    opens com.example.proiect to javafx.fxml;
    exports com.example.proiect;
    opens com.example.proiect.model;
    opens com.example.proiect.repository;
    opens com.example.proiect.controllers;
    exports com.example.proiect.model;
    exports com.example.proiect.repository;
    exports com.example.proiect.controllers;
}