package com.example.guireteadesocializare;

import domain.Utilizator;
import exceptions.RepoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Service;


import java.util.Objects;

public class LogInSignupController {
    Service service;
    private Utilizator currentUser;
    @FXML
    public AnchorPane loginPane;
    @FXML
    public AnchorPane signupPane;
    @FXML
    public TextField emailLoginTextField;
    @FXML
    public PasswordField passwordLoginTextField;
    @FXML
    public TextField emailSignupTextField;
    @FXML
    public TextField firstnameSignupTextField;
    @FXML
    public TextField lastnameSignupTextField;
    @FXML
    public PasswordField passwordSignupTextField;
    @FXML
    public PasswordField repeatpasswordSignupTextField;
    @FXML
    public Button LogInButton;
    @FXML
    public Button SignUpButton;
    @FXML
    public Button LogInButtonFromSignup;
    @FXML
    public Button SignUpButtonFromLogin;
    public void SetService(Service service) {
        this.service = service;
        loginPane.setVisible(true);
        signupPane.setVisible(false);
        emailLoginTextField.setText("dariamarian15@gmail.com");
    }
    @FXML
    public void onLogInButtonAction() {
        String email = emailLoginTextField.getText();
        String password = passwordLoginTextField.getText();
        try {
            currentUser = service.getUserByEmail(email);
            if(currentUser==null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "User doesn't exist", ButtonType.OK);
                alert.show();
                return;
            }
            if (!Objects.equals(password, currentUser.getPassword())) {
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
    @FXML
    public void onSignUpButtonAction() {
        String firstName = firstnameSignupTextField.getText();
        String lastName = lastnameSignupTextField.getText();
        String email = emailSignupTextField.getText();
        String password = passwordSignupTextField.getText();
        String repeatpassword = repeatpasswordSignupTextField.getText();
        try {
            if(Objects.equals(repeatpassword, password))
            {
                service.add(firstName, lastName, email, password);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Signed-up successfuly", ButtonType.OK);
                alert.show();
                onLogInButtonFromSignupAction();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords don't match", ButtonType.OK);
                alert.show();
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    @FXML
    public void onLogInButtonFromSignupAction(){
        loginPane.setVisible(true);
        signupPane.setVisible(false);
    }
    @FXML
    public void onSignUpButtonFromLoginAction(){
        loginPane.setVisible(false);
        signupPane.setVisible(true);
    }
    private void changeScene()
    {
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("MainInterface.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            MainController controller = loader.getController();
            controller.SetService(service,currentUser);

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
