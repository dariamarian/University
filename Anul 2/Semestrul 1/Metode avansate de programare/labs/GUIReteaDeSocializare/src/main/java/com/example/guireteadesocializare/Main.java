package com.example.guireteadesocializare;

import domain.Message;
import domain.Prietenie;
import domain.Utilizator;
import domain.validators.PrietenieValidator;
import domain.validators.UtilizatorValidator;
import domain.validators.Validator;
import exceptions.RepoException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.Retea;
import network.ReteaUtilizator;
import repository.DataBase.MessageDBRepo;
import repository.DataBase.PrietenieDBRepo;
import repository.DataBase.UtilizatorDBRepo;
import repository.Repository;
import service.Service;
import service.UserFriendshipService;

import java.io.IOException;

public class Main extends Application {

    String url = "jdbc:postgresql://localhost:5432/ReteaDeSocializare";
    String userName = "postgres";
    String password = "userdaria";
    Validator<Utilizator> validatorU = UtilizatorValidator.getInstance();
    Validator<Prietenie> validatorP = PrietenieValidator.getInstance();
    Repository<Utilizator> userRepository = new UtilizatorDBRepo(url, userName, password);
    Repository<Prietenie> friendshipRepository = new PrietenieDBRepo(url, userName, password,(UtilizatorDBRepo) userRepository);
    Repository<Message> messageRepository = new MessageDBRepo(url, userName, password, (UtilizatorDBRepo) userRepository);

    Retea userNetwork = new ReteaUtilizator();
    Service service = new UserFriendshipService(validatorU,validatorP,(UtilizatorDBRepo) userRepository,(PrietenieDBRepo) friendshipRepository,(MessageDBRepo) messageRepository,userNetwork);

    @Override
    public void start(Stage stage) throws IOException, RepoException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LogInSignUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 210, 500);
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