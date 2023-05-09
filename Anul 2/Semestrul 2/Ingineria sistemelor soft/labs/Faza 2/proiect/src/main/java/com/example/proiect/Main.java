package com.example.proiect;

import com.example.proiect.controllers.LogInController;
import com.example.proiect.repository.*;
import com.example.proiect.repository.utils.HibernateUtils;
import com.example.proiect.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LogIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 210, 500);
            LogInController controller=fxmlLoader.getController();
            SessionFactory sessionFactory = HibernateUtils.initialize();
            controller.SetService(Service.getInstance(sessionFactory));
            stage.setTitle("LogIn");
            stage.setScene(scene);
            stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}