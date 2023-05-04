package angajat.controller;

import javafx.scene.Parent;
import proiectServerClientJava.domain.Angajat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import proiectServerClientJava.service.IService;
import proiectServerClientJava.service.MyException;

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
    private final Stage employeeStage = new Stage();
    private MainController mainController;
    private Parent mainParent;

    public void SetService(IService service) {
        this.service = service;
        loginPane.setVisible(true);
        usernameTextField.setText("dariam");
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setParent(Parent parent) {
        this.mainParent = parent;
    }
    @FXML
    public void onLogInButtonAction() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        try {
            Angajat trying = new Angajat(0L,"",username, password);
            Angajat employee = service.login(trying, mainController);
            if(employee==null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Angajat doesn't exist", ButtonType.OK);
                alert.show();
                return;
            }
            if (!Objects.equals(password, employee.getPassword())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password", ButtonType.OK);
                alert.show();
                return;
            }
            changeScene();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    private void changeScene() throws MyException {
        employeeStage.setTitle("Main Interface ");
        if (employeeStage.getScene()==null)
            employeeStage.setScene(new Scene(mainParent, 700, 600));
        employeeStage.show();
        mainController.setEmployee(currentAngajat);
        mainController.setService(service);
        mainController.setStage((Stage) usernameTextField.getScene().getWindow());
        mainController.init();
        Stage currentStage=(Stage) LogInButton.getScene().getWindow();
        currentStage.close();
    }
}
