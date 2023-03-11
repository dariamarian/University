package com.example.examenmariandaria;

import com.example.examenmariandaria.controllers.LogInSignupController;
import com.example.examenmariandaria.domain.Nevoie;
import com.example.examenmariandaria.domain.Persoana;
import com.example.examenmariandaria.repository.RepoException;
import com.example.examenmariandaria.repository.RepoNevoie;
import com.example.examenmariandaria.repository.RepoPersoana;
import com.example.examenmariandaria.repository.Repository;
import com.example.examenmariandaria.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    Repository<Persoana,Long> persoanaRepository = new RepoPersoana();
    Repository<Nevoie,Long> nevoieRepository = new RepoNevoie();
    Service service = new Service((RepoPersoana) persoanaRepository,(RepoNevoie) nevoieRepository);

    @Override
    public void start(Stage stage) throws IOException, RepoException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LogInSignUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 220, 500);
        LogInSignupController controller=fxmlLoader.getController();
        controller.SetService(service);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}