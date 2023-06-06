package com.example.proiect.controllers;

import com.example.proiect.Main;
import com.example.proiect.model.Admin;
import com.example.proiect.model.MedicalPersonnel;
import com.example.proiect.model.Pharmaceutist;
import com.example.proiect.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    public Button LogInButton;
    private AdminController adminController;
    private PharmaceutistController pharmaceutistController;
    private MedicalPersonnelController personnelController;
    private Parent parentForAdmin, parentForPharmaceutist, parentForPersonnel;

    public void SetService(Service service) {
        this.service = service;
        loginPane.setVisible(true);
        usernameTextField.setText("dariam");
    }
    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }
    public void setPharmaceutistController(PharmaceutistController pharmaceutistController) {
        this.pharmaceutistController = pharmaceutistController;
    }
    public void setPersonnelController(MedicalPersonnelController personnelController) {
        this.personnelController = personnelController;
    }
    public void setParentForAdmin(Parent parent) {
        this.parentForAdmin = parent;
    }
    public void setParentForPharmaceutist(Parent parent) {
        this.parentForPharmaceutist = parent;
    }
    public void setParentForPersonnel(Parent parent) {
        this.parentForPersonnel = parent;
    }
    @FXML
    public void onLogInButtonAction() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        Object admin = service.findAdminByUsername(username);
        try{
            if(admin==null) {
                Object pharmaceutist = service.findPharmaceutistByUsername(username);
                if(pharmaceutist==null)
                {
                    Object personnel = service.findPersonnelByUsername(username);
                    if(personnel==null)
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Account doesn't exist", ButtonType.OK);
                        alert.show();
                    }
                    else {
                        MedicalPersonnel tryingM = new MedicalPersonnel(null,username, password,null);
                        currentPersonnel = service.loginPersonnel(tryingM,personnelController);
                        changeScenePersonnel();
                    }
                }
                else {
                    Pharmaceutist tryingP = new Pharmaceutist(null,username, password);
                    currentPharmaceutist = service.loginPharmaceutist(tryingP,pharmaceutistController);
                    changeScenePharmaceutist();
                }
            }
            else {
                Admin tryingA = new Admin(null,username, password);
                currentAdmin = service.loginAdmin(tryingA,adminController);
                changeSceneAdmin();
            }
        }
        catch(Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.show();
      }
    }
    private void changeSceneAdmin()
    {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("AdminInterface.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            AdminController controller = loader.getController();
            controller.SetService(service);
            controller.SetAdmin(currentAdmin);
            controller.setStage((Stage) usernameTextField.getScene().getWindow());

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
            controller.SetService(service);
            controller.SetPharmaceutist(currentPharmaceutist);
            controller.setStage((Stage) usernameTextField.getScene().getWindow());
            controller.init();

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
            controller.SetService(service);
            controller.SetPersonnel(currentPersonnel);
            controller.setStage((Stage) usernameTextField.getScene().getWindow());
            controller.init();

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
