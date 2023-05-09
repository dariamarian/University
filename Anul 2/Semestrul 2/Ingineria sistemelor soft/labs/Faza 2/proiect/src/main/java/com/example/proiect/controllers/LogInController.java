package com.example.proiect.controllers;

import com.example.proiect.Main;
import com.example.proiect.model.Admin;
import com.example.proiect.model.MedicalPersonnel;
import com.example.proiect.model.Pharmaceutist;
import com.example.proiect.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class LogInController {
    Service service;
    private Admin currentAdmin;
    private Pharmaceutist currentPharmaceutist;
    private MedicalPersonnel currentPersonnel;
    @FXML
    public AnchorPane loginPane;
    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    public ChoiceBox loginChoiceBox = new ChoiceBox();
    @FXML
    public Button LogInButton;

    public void SetService(Service service) {
        this.service = service;
        loginPane.setVisible(true);
        usernameTextField.setText("dariam");
        loginChoiceBox.getItems().add("admin");
        loginChoiceBox.getItems().add("pharmaceutist");
        loginChoiceBox.getItems().add("medical personnel");
    }
    @FXML
    public void onLogInButtonAction() {
        String choice = (String) loginChoiceBox.getValue();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if(Objects.equals(choice, "admin"))
        {
            try {
                Admin trying = new Admin(null,username, password);
                Object admin = service.loginAdmin(trying);
                if(admin==null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Admin doesn't exist", ButtonType.OK);
                    alert.show();
                    return;

                }
                changeSceneAdmin();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.show();
            }
        }
        if(Objects.equals(choice, "pharmaceutist"))
        {
            try {
                Pharmaceutist trying = new Pharmaceutist(null,username, password);
                Object pharmaceutist = service.loginPharmaceutist(trying);
                if(pharmaceutist==null)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Pharmaceutist doesn't exist", ButtonType.OK);
                    alert.show();
                    return;
                }
                changeScenePharmaceutist();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.show();
            }
        }
        if(Objects.equals(choice, "medical personnel"))
        {
            try {
                MedicalPersonnel trying = new MedicalPersonnel(null,username, password,null);
                Object personnel = service.loginPersonnel(trying);
                if(personnel==null)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Personnel doesn't exist", ButtonType.OK);
                    alert.show();
                    return;
                }
                changeScenePersonnel();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.show();
            }
        }
    }
    private void changeSceneAdmin()
    {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("AdminInterface.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            AdminController controller = loader.getController();
            controller.SetService(service, currentAdmin);

            Stage currentStage=(Stage) LogInButton.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setTitle("Admin");
            currentStage.close();
            newStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void changeScenePharmaceutist()
    {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("PharmaceutistInterface.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            PharmaceutistController controller = loader.getController();
            controller.SetService(service, currentPharmaceutist);

            Stage currentStage=(Stage) LogInButton.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setTitle("Pharmaceutist");
            currentStage.close();
            newStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void changeScenePersonnel()
    {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("MedicalPersonnelInterface.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            MedicalPersonnelController controller = loader.getController();
            controller.SetService(service, currentPersonnel);

            Stage currentStage=(Stage) LogInButton.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setTitle("Medical Personnel");
            currentStage.close();
            newStage.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
