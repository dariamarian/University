package com.example.proiect.controllers;

import com.example.proiect.Main;
import com.example.proiect.model.Angajat;
import com.example.proiect.exceptions.RepoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.example.proiect.service.IService;

import java.util.Objects;

public class LogInController {
    IService service;
    private Angajat currentAngajat;
    @FXML
    public AnchorPane loginPane;
    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    public Button LogInButton;
    public void SetService(IService service) {
        this.service = service;
        loginPane.setVisible(true);
        usernameTextField.setText("dariam");
    }
    @FXML
    public void onLogInButtonAction() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        try {
            currentAngajat = service.getAngajatByUsername(username);
            if(currentAngajat==null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Angajat doesn't exist", ButtonType.OK);
                alert.show();
                return;
            }
            if (!Objects.equals(password, currentAngajat.getPassword())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password", ButtonType.OK);
                alert.show();
                return;
            }
            changeScene();
        } catch (RepoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    private void changeScene()
    {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("MainInterface.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            MainController controller = loader.getController();
            controller.SetService(service,currentAngajat);

            Stage currentStage=(Stage) LogInButton.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setTitle("LogIn");
            currentStage.close();
            newStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
