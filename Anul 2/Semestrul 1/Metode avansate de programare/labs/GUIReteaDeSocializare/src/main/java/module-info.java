module com.example.guireteadesocializare {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens domain;
    opens com.example.guireteadesocializare to javafx.fxml;
    exports com.example.guireteadesocializare;
    exports domain;
    exports exceptions;
    exports service;
}