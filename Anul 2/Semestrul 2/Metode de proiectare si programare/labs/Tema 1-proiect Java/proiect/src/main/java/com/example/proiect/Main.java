package com.example.proiect;

import com.example.proiect.controllers.LogInController;
import com.example.proiect.exceptions.RepoException;
import com.example.proiect.exceptions.ValidationException;
import com.example.proiect.model.Angajat;
import com.example.proiect.model.Bilet;
import com.example.proiect.model.Spectacol;
import com.example.proiect.model.validators.AngajatValidator;
import com.example.proiect.model.validators.BiletValidator;
import com.example.proiect.model.validators.SpectacolValidator;
import com.example.proiect.model.validators.Validator;
import com.example.proiect.repository.RepoAngajat;
import com.example.proiect.repository.RepoBilet;
import com.example.proiect.repository.RepoSpectacol;
import com.example.proiect.service.IService;
import com.example.proiect.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties properties=new Properties();
        try{
            properties.load(new FileReader("bd.config"));

            Validator<Angajat> validatorA = AngajatValidator.getInstance();
            Validator<Spectacol> validatorS = SpectacolValidator.getInstance();
            Validator<Bilet> validatorB = BiletValidator.getInstance();
            RepoAngajat repoAngajat=new RepoAngajat(properties);
            RepoSpectacol repoSpectacol=new RepoSpectacol(properties);
            RepoBilet repoBilet=new RepoBilet(properties);
            IService service = new Service(validatorA,validatorS,validatorB,repoAngajat,repoSpectacol,repoBilet);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LogIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 210, 500);
            LogInController controller=fxmlLoader.getController();
            controller.SetService(service);
            stage.setTitle("LogIn");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex) {
            System.out.println("Cannot find bd.config");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}