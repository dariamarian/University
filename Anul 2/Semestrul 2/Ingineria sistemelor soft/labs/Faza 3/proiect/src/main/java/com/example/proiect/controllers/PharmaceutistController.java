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

import java.util.List;
import java.util.Objects;

public class PharmaceutistController implements IObserver {
    Service service;
    private final Duration refreshInterval = Duration.seconds(10);
    private Pharmaceutist loggedPharmaceutist =null;
    @FXML
    public Button LogOutButton;
    ObservableList<Order> ordersList = FXCollections.observableArrayList();
    ObservableList<Order> acceptedOrdersList = FXCollections.observableArrayList();
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    TableColumn<Order,String> medicationColumn1;
    @FXML
    TableColumn<Order,Integer> quantityColumn1;
    @FXML
    TableColumn<Order,String> commentsColumn1;
    @FXML
    TableColumn<Order,String> timeColumn1;
    @FXML
    TableColumn<Order,Status> statusColumn1;

    @FXML
    private TableView<Order> acceptedOrdersTable;
    @FXML
    TableColumn<Order,String> medicationColumn2;
    @FXML
    TableColumn<Order,Integer> quantityColumn2;
    @FXML
    TableColumn<Order,String> commentsColumn2;
    @FXML
    TableColumn<Order,String> timeColumn2;
    @FXML
    TableColumn<Order,Status> statusColumn2;

    @FXML
    public Button takeOrderButton;
    @FXML
    public Button finishOrderButton;
    private Stage loginStage;

    public void SetService(Service service) {
        this.service = service;
    }
    public void SetPharmaceutist(Pharmaceutist pharmaceutist)
    {
        this.loggedPharmaceutist = pharmaceutist;
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
        List<Order> list = (List<Order>) service.getAllOrders();
        list = list.stream().filter(order -> order.getStatus() == Status.Received && order.getPharmaceutist() ==null).toList();
        ordersList.setAll(list);

        List<Order> list2 = (List<Order>) service.getAllOrders();
        list2 = list2.stream().filter(order ->order.getStatus() == Status.Preparing && order.getPharmaceutist()!=null  && Objects.equals(order.getPharmaceutist().getId(), loggedPharmaceutist.getId())).toList();
        acceptedOrdersList.setAll(list2);
    }

    public void initialize()
    {
        medicationColumn1.setCellValueFactory(new PropertyValueFactory<Order,String>("medication"));
        quantityColumn1.setCellValueFactory(new PropertyValueFactory<Order,Integer>("quantity"));
        timeColumn1.setCellValueFactory(new PropertyValueFactory<Order,String>("time_taken"));
        commentsColumn1.setCellValueFactory(new PropertyValueFactory<Order,String>("comments"));
        statusColumn1.setCellValueFactory(new PropertyValueFactory<Order,Status>("status"));
        ordersTable.setItems(ordersList);

        medicationColumn2.setCellValueFactory(new PropertyValueFactory<Order,String>("medication"));
        quantityColumn2.setCellValueFactory(new PropertyValueFactory<Order,Integer>("quantity"));
        timeColumn2.setCellValueFactory(new PropertyValueFactory<Order,String>("time_taken"));
        commentsColumn2.setCellValueFactory(new PropertyValueFactory<Order,String>("comments"));
        statusColumn2.setCellValueFactory(new PropertyValueFactory<Order,Status>("status"));
        acceptedOrdersTable.setItems(acceptedOrdersList);

    }

    @FXML
    public void onTakeOrderButtonAction()
    {
        try {
            Order order=service.getOrder(ordersTable.getSelectionModel().getSelectedItem().getId());
            service.takeOrder(order,loggedPharmaceutist);
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order accepted", ButtonType.OK);
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
    public void onFinishOrderButtonAction()
    {
        try {
            Order order=service.getOrder(acceptedOrdersTable.getSelectionModel().getSelectedItem().getId());
            service.finishOrder(order);
            onRefresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order finished", ButtonType.OK);
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
            service.logoutPharmaceutist(loggedPharmaceutist);
            loginStage.show();
        } catch (MyException e) {
            System.out.println("Logout error " + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void onLogoutButtonAction() {
        logout();
        Stage stage = (Stage) this.takeOrderButton.getScene().getWindow();
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