package com.example.proiect.controllers;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.*;
import com.example.proiect.service.IObserver;
import com.example.proiect.service.Service;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class MedicalPersonnelController implements IObserver {
    Service service;
    private final Duration refreshInterval = Duration.seconds(10);
    private MedicalPersonnel loggedPersonnel =null;
    @FXML
    public Button LogOutButton;

    ObservableList<Medication> medicationsList = FXCollections.observableArrayList();
    ObservableList<Order> ordersList = FXCollections.observableArrayList();
    @FXML
    private TableView<Medication> medicationsTable;
    @FXML
    TableColumn<Medication,String> nameColumn;
    @FXML
    TableColumn<Medication,String> detailsColumn;
    @FXML
    TableColumn<Medication,Integer> stockColumn;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    TableColumn<Order,String> medicationColumn;
    @FXML
    TableColumn<Order,Integer> quantityColumn;
    @FXML
    TableColumn<Order,String> commentsColumn;
    @FXML
    TableColumn<Order,String> timeColumn;
    @FXML
    TableColumn<Order, Status> statusColumn;
    @FXML
    public TextField quantityField;
    @FXML
    public TextField commentsField;
    @FXML
    public Button placeOrderButton;
    private Stage loginStage;


    public void SetService(Service service) {
        this.service = service;
    }
    public void SetPersonnel(MedicalPersonnel personnel)
    {
        this.loggedPersonnel = personnel;
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
    public void init() {
        init_lists();
        initialize();
        startAutoRefresh();
    }
    public void init_lists()
    {
        List<Medication> medications = (List<Medication>) service.getAllMedications();
        medicationsList.setAll(medications);

        List<Order> orders = (List<Order>) service.getAllOrders();
        orders = orders.stream().filter(order ->Objects.equals(order.getMedicalPersonnel().getId(), loggedPersonnel.getId())).toList();
        ordersList.setAll(orders);
    }

    public void initialize()
    {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Medication,String>("name"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<Medication,String>("details"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<Medication,Integer>("stock"));
        medicationsTable.setItems(medicationsList);

        medicationColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("medication"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Order,Integer>("quantity"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("time_taken"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("comments"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Order,Status>("status"));
        ordersTable.setItems(ordersList);
    }

    @FXML
    public void onPlaceOrderButtonAction()
    {
        try {
            Medication medication=service.getMedication(medicationsTable.getSelectionModel().getSelectedItem().getId());
            int quantity = Integer.parseInt(quantityField.getText());
            String comments = commentsField.getText();
            service.addOrder(new Order(loggedPersonnel.getId(), medication.getId(), quantity, LocalTime.now().toString(), comments, Status.Received));
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order placed", ButtonType.OK);
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
            service.logoutPersonnel(loggedPersonnel);
            loginStage.show();
        } catch (MyException e) {
            System.out.println("Logout error " + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void onLogoutButtonAction() {
        logout();
        Stage stage = (Stage) this.quantityField.getScene().getWindow();
        stage.close();
    }
    public void onRefresh() {
        init_lists();
        initialize();
    }
    public void showOrders() {
        Platform.runLater(() -> {
            init_lists();
        });
    }public void showMedications() {
        Platform.runLater(() -> {
            init_lists();
        });
    }
}