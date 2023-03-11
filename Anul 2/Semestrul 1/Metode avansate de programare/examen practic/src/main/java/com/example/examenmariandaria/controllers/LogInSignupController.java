package com.example.examenmariandaria.controllers;

import com.example.examenmariandaria.Main;
import com.example.examenmariandaria.domain.Orase;
import com.example.examenmariandaria.domain.Persoana;
import com.example.examenmariandaria.repository.RepoException;
import com.example.examenmariandaria.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class LogInSignupController {
    Service service;
    private Persoana currentUser;
    @FXML
    public AnchorPane loginPane;
    @FXML
    public AnchorPane signupPane;
    @FXML
    public TextField numeSignupTextField;
    @FXML
    public TextField prenumeSignupTextField;
    @FXML
    public TextField usernameSignupTextField;
    @FXML
    public TextField stradaSignupTextField;
    @FXML
    public TextField numarstradaSignupTextField;
    @FXML
    public TextField telefonSignupTextField;
    @FXML
    public PasswordField parolaSignupTextField;
    @FXML
    public PasswordField repeatparolaSignupTextField;
    @FXML
    public Button SignUpButton;
    @FXML
    public Button LogInButtonFromSignup;
    @FXML
    public Button SignUpButtonFromLogin;
    @FXML
    private ComboBox<Orase> orasComboBox = new ComboBox<>();
    @FXML
    private ListView<String> loginlist;
    @FXML
    public Button refreshButton;
    ObservableList<String> usernameList = FXCollections.observableArrayList();
    @FXML
    public void init_lists() {
        loginlist.getItems().clear();
        for (Persoana persoana : service.getAllPersoane())
        {
            usernameList.add(persoana.getUsername());
        }
        if(usernameList != null)
            loginlist.setItems(usernameList);
    }

    @FXML
    public void onMouseClicked()
    {
        String username = loginlist.getSelectionModel().getSelectedItem();
        try {
            currentUser = service.getPersoanaByUsername(username);
            changeScene();
        } catch (RepoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    public void SetService(Service service) {
        this.service = service;
        loginPane.setVisible(true);
        signupPane.setVisible(false);
        orasComboBox.setItems( FXCollections.observableArrayList( Orase.values()));
        init_lists();
    }
    private String checkErrorsAtSignUp(String nume, String prenume, String username, String parola, String repeat_parola,Orase oras, String strada, String numarstrada, String telefon) {
        String errors = "";
        if (nume.isEmpty()) {
            errors += "Name can't be empty.\n";
        }
        if (prenume.isEmpty()) {
            errors += "Prenume can't be empty.\n";
        }
        if (username.isEmpty()) {
            errors += "Username can't be empty.\n";
        }
        if (parola.length()<3) {
            errors += "The password must have at least 3 characters.\n";
        }
        if (repeat_parola.length()<3) {
            errors += "The second password must have at least 3 characters.\n";
        }
        if(oras==null){
            errors += "City can't be empty.\n";
        }
        if (strada.isEmpty()) {
            errors += "Street can't be empty.\n";
        }
        if (numarstrada.isEmpty()) {
            errors += "The street number can't be empty.\n";
        }
        if (telefon.length() != 10) {
            errors += "Phone number is not valid.\n";
        }
        return errors;
    }
    @FXML
    public void onSignUpButtonAction() {
        String nume = numeSignupTextField.getText();
        String prenume = prenumeSignupTextField.getText();
        String username = usernameSignupTextField.getText();
        String parola = parolaSignupTextField.getText();
        String repeatparola = repeatparolaSignupTextField.getText();
        Orase oras = orasComboBox.getValue();
        String strada = stradaSignupTextField.getText();
        String numarstrada = numarstradaSignupTextField.getText();
        String telefon = telefonSignupTextField.getText();
        try {
            String errors = checkErrorsAtSignUp(nume, prenume, username,parola,repeatparola,oras,strada,numarstrada,telefon);
            if (!errors.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, errors, ButtonType.OK);
                alert.show();
                return;
            }
            if(Objects.equals(repeatparola, parola))
            {
                service.addPersoana(nume,prenume,username,parola,oras,strada,numarstrada,telefon);
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
    public void onLogInButtonFromSignupAction() {
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
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 700, 600);
            MainController controller = loader.getController();
            controller.SetService(service,currentUser);

            Stage currentStage=(Stage) SignUpButtonFromLogin.getScene().getWindow();

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
    @FXML
    public void onRefresh() {
        init_lists();
    }
}
