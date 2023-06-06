package com.example.proiect;

import com.example.proiect.controllers.AdminController;
import com.example.proiect.controllers.LogInController;
import com.example.proiect.controllers.MedicalPersonnelController;
import com.example.proiect.controllers.PharmaceutistController;
import com.example.proiect.repository.utils.HibernateUtils;
import com.example.proiect.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LogIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 210, 500);
            LogInController controller=fxmlLoader.getController();
            SessionFactory sessionFactory = HibernateUtils.initialize();
            controller.SetService(Service.getInstance(sessionFactory));

            FXMLLoader adminLoader = new FXMLLoader(Main.class.getResource("AdminInterface.fxml"));
            Parent parentForAdmin=adminLoader.load();
            AdminController adminController =adminLoader.getController();
            adminController.SetService(Service.getInstance(sessionFactory));
            controller.setAdminController(adminController);
            controller.setParentForAdmin(parentForAdmin);

            FXMLLoader pharmaceutistLoader = new FXMLLoader(Main.class.getResource("PharmaceutistInterface.fxml"));
            Parent parentForPharmaceutist=pharmaceutistLoader.load();
            PharmaceutistController pharmaceutistController =pharmaceutistLoader.getController();
            pharmaceutistController.SetService(Service.getInstance(sessionFactory));
            controller.setPharmaceutistController(pharmaceutistController);
            controller.setParentForPharmaceutist(parentForPharmaceutist);

            FXMLLoader personnelLoader = new FXMLLoader(Main.class.getResource("MedicalPersonnelInterface.fxml"));
            Parent parentForPersonnel=personnelLoader.load();
            MedicalPersonnelController personnelController =personnelLoader.getController();
            personnelController.SetService(Service.getInstance(sessionFactory));
            controller.setPersonnelController(personnelController);
            controller.setParentForPersonnel(parentForPersonnel);

            stage.setTitle("LogIn");
            stage.setScene(scene);
            stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}