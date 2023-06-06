package com.example.proiect.controllers;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.*;
import com.example.proiect.service.IObserver;
import com.example.proiect.service.Service;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class AdminController implements IObserver {
    Service service;
    private final Duration refreshInterval = Duration.seconds(10);
    private Admin loggedAdmin =null;
    @FXML
    public Button LogOutButton;
    ObservableList<Pharmaceutist> pharmaceutistsList = FXCollections.observableArrayList();
    ObservableList<MedicalPersonnel> personnelsList = FXCollections.observableArrayList();
    ObservableList<Medication> medicationsList = FXCollections.observableArrayList();

    @FXML
    private TableView<Pharmaceutist> pharmaceutistsTable;
    @FXML
    TableColumn<Pharmaceutist,String> nameColumn1;
    @FXML
    TableColumn<Pharmaceutist,String> usernameColumn1;
    @FXML
    TableColumn<Pharmaceutist,String> passwordColumn1;
    @FXML
    public TextField nameField1;
    @FXML
    public TextField usernameField1;
    @FXML
    public TextField passwordField1;
    @FXML
    public Button addPharmaceutistButton;
    @FXML
    public Button updatePharmaceutistButton;
    @FXML
    public Button deletePharmaceutistButton;

    @FXML
    private TableView<MedicalPersonnel> personnelsTable;
    @FXML
    TableColumn<MedicalPersonnel,String> nameColumn2;
    @FXML
    TableColumn<MedicalPersonnel,String> usernameColumn2;
    @FXML
    TableColumn<MedicalPersonnel,String> passwordColumn2;
    @FXML
    TableColumn<MedicalPersonnel,String> sectionColumn2;
    @FXML
    public TextField nameField2;
    @FXML
    public TextField usernameField2;
    @FXML
    public TextField passwordField2;
    @FXML
    public TextField sectionField2;
    @FXML
    public Button addPersonnelButton;
    @FXML
    public Button updatePersonnelButton;
    @FXML
    public Button deletePersonnelButton;

    @FXML
    private TableView<Medication> medicationsTable;
    @FXML
    TableColumn<Medication,String> nameColumn3;
    @FXML
    TableColumn<Medication,String> detailsColumn3;
    @FXML
    TableColumn<Medication,Integer> stockColumn3;
    @FXML
    public TextField nameField3;
    @FXML
    public TextField detailsField3;
    @FXML
    public TextField stockField3;
    @FXML
    public Button addMedicationButton;
    @FXML
    public Button updateMedicationButton;
    @FXML
    public Button deleteMedicationButton;
    private Stage loginStage;

    public void SetService(Service service) {
        this.service = service;
        init_lists();
        initialize();
        startAutoRefresh();
    }
    public void SetAdmin(Admin admin)
    {
        this.loggedAdmin = admin;
    }
    @FXML
    public void setStage(Stage stage) {
        this.loginStage = stage;
    }
    private void startAutoRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(refreshInterval, event -> {
            onRefresh();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void init_lists()
    {
        List<Pharmaceutist> pharmaceutists = (List<Pharmaceutist>) this.service.getAllPharmaceutists();
        pharmaceutistsList = FXCollections.observableArrayList(pharmaceutists);

        List<MedicalPersonnel> personnels = (List<MedicalPersonnel>) this.service.getAllPersonnels();
        personnelsList = FXCollections.observableArrayList(personnels);

        List<Medication> medications = (List<Medication>) this.service.getAllMedications();
        medicationsList = FXCollections.observableArrayList(medications);
    }

    public void initialize()
    {
        nameColumn1.setCellValueFactory(new PropertyValueFactory<Pharmaceutist,String>("name"));
        usernameColumn1.setCellValueFactory(new PropertyValueFactory<Pharmaceutist,String>("username"));
        passwordColumn1.setCellValueFactory(new PropertyValueFactory<Pharmaceutist,String>("password"));
        pharmaceutistsTable.setItems(pharmaceutistsList);

        nameColumn2.setCellValueFactory(new PropertyValueFactory<MedicalPersonnel,String>("name"));
        usernameColumn2.setCellValueFactory(new PropertyValueFactory<MedicalPersonnel,String>("username"));
        passwordColumn2.setCellValueFactory(new PropertyValueFactory<MedicalPersonnel,String>("password"));
        sectionColumn2.setCellValueFactory(new PropertyValueFactory<MedicalPersonnel,String>("sectie"));
        personnelsTable.setItems(personnelsList);

        nameColumn3.setCellValueFactory(new PropertyValueFactory<Medication,String>("name"));
        detailsColumn3.setCellValueFactory(new PropertyValueFactory<Medication,String>("details"));
        stockColumn3.setCellValueFactory(new PropertyValueFactory<Medication,Integer>("stock"));
        medicationsTable.setItems(medicationsList);
    }

    @FXML
    public void onAddPharmaceutistButton()
    {
        try {
            String name=nameField1.getText();
            String username=usernameField1.getText();
            String password=passwordField1.getText();
            service.addPharmaceutist(new Pharmaceutist(name,username,password));
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pharmaceutist added", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onDeletePharmaceutistButton()
    {
        try {
            Pharmaceutist pharmaceutist = pharmaceutistsTable.getSelectionModel().getSelectedItem();
            service.removePharmaceutist(pharmaceutist.getId());
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pharmaceutist deleted", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onUpdatePharmaceutistButton()
    {
        try {
            Pharmaceutist pharmaceutist=service.getPharmaceutist(pharmaceutistsTable.getSelectionModel().getSelectedItem().getId());
            String name=nameField1.getText();
            String username=usernameField1.getText();
            String password=passwordField1.getText();
            pharmaceutist.setName(name);
            pharmaceutist.setUsername(username);
            pharmaceutist.setPassword(password);
            service.updatePharmaceutist(pharmaceutist);
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pharmaceutist updated", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onAddPersonnelButton()
    {
        try {
            String name=nameField2.getText();
            String username=usernameField2.getText();
            String password=passwordField2.getText();
            Sectie sectie= Sectie.valueOf(sectionField2.getText());
            service.addPersonnel(new MedicalPersonnel(name,username,password,sectie));
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medical personnel added", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onDeletePersonnelButton()
    {
        try {
            MedicalPersonnel personnel = personnelsTable.getSelectionModel().getSelectedItem();
            service.removePersonnel(personnel.getId());
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medical personnel deleted", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onUpdatePersonnelButton()
    {
        try {
            MedicalPersonnel personnel=service.getPersonnel(personnelsTable.getSelectionModel().getSelectedItem().getId());
            String name=nameField2.getText();
            String username=usernameField2.getText();
            String password=passwordField2.getText();
            Sectie sectie= Sectie.valueOf(sectionField2.getText());
            personnel.setName(name);
            personnel.setUsername(username);
            personnel.setPassword(password);
            personnel.setSectie(sectie);
            service.updatePersonnel(personnel);
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medical personnel updated", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onAddMedicationButton()
    {
        try {
            String name=nameField3.getText();
            String details=detailsField3.getText();
            int stock=Integer.parseInt(stockField3.getText());
            service.addMedication(new Medication(name,details,stock));
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medication added", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onDeleteMedicationButton()
    {
        try {
            Medication medication = medicationsTable.getSelectionModel().getSelectedItem();
            service.removeMedication(medication.getId());
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medication deleted", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onUpdateMedicationButton()
    {
        try {
            Medication medication=service.getMedication(medicationsTable.getSelectionModel().getSelectedItem().getId());
            String name=nameField3.getText();
            String details=detailsField3.getText();
            int stock=Integer.parseInt(stockField3.getText());
            medication.setName(name);
            medication.setDetails(details);
            medication.setStock(stock);
            service.updateMedication(medication);
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medication updated", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    public void logout() {
        try {
            service.logoutAdmin(loggedAdmin);
            loginStage.show();
        } catch (MyException e) {
            System.out.println("Logout error " + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void onLogoutButtonAction() {
        logout();
        Stage stage = (Stage) this.usernameField1.getScene().getWindow();
        stage.close();
    }
    public void onRefresh() {
        init_lists();
        initialize();
    }
    public void showOrders() {
//        Platform.runLater(() -> {
//            try {
//                showAvailableBooks();
//                showBorrowedBooks();
//            } catch (MyException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }
    public void showMedications() {
//        Platform.runLater(() -> {
//            try {
//                showAvailableBooks();
//                showBorrowedBooks();
//            } catch (MyException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }
}