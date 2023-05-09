package com.example.proiect.controllers;

import com.example.proiect.Main;
import com.example.proiect.exceptions.RepoException;
import com.example.proiect.model.MedicalPersonnel;
import com.example.proiect.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MedicalPersonnelController {
    Service service;
    private MedicalPersonnel loggedPersonnel =null;
    @FXML
    public Button LogOutButton;

    public void SetService(Service service, MedicalPersonnel personnel) throws RepoException {
        this.service = service;
        this.loggedPersonnel = personnel;
        //init_lists();
        //initialize();
    }

    @FXML
    public void onLogoutButtonAction(){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LogIn.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 210, 500);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        LogInController controller=loader.getController();
        controller.SetService(service);
        Stage currentStage= (Stage) LogOutButton.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("LogIn");
        currentStage.close();
        newStage.show();
    }
}