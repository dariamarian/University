package angajat.controller;

import javafx.application.Platform;
import proiectServerClientJava.domain.Angajat;
import proiectServerClientJava.domain.Bilet;
import proiectServerClientJava.domain.Spectacol;
import proiectServerClientJava.domain.SpectacolDTO;
import proiectServerClientJava.service.IObserver;
import proiectServerClientJava.service.IService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proiectServerClientJava.service.MyException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainController implements IObserver {
    IService service;
    private Angajat loggedAngajat=null;
    ObservableList<Spectacol> spectacoleList = FXCollections.observableArrayList();
    ObservableList<Spectacol> spectacoleCautateList = FXCollections.observableArrayList();
    @FXML
    public Button LogOutButton;
    @FXML
    private DatePicker spectacolDatePicker;
    @FXML
    private TableView<Spectacol> spectacoleTable;
    @FXML
    TableColumn<Spectacol,String> artistNameColumn;
    @FXML
    TableColumn<Spectacol,String> dateColumn;
    @FXML
    TableColumn<Spectacol,String> timeColumn;
    @FXML
    TableColumn<Spectacol,String> placeColumn;
    @FXML
    TableColumn<Spectacol,Integer> availableSeatsColumn;
    @FXML
    TableColumn<Spectacol,Integer> soldSeatsColumn;
    @FXML
    private TableView<Spectacol> spectacoleCautateTable;
    @FXML
    TableColumn<Spectacol,String> artistNameColumn2;
    @FXML
    TableColumn<Spectacol,String> timeColumn2;
    @FXML
    TableColumn<Spectacol,String> placeColumn2;
    @FXML
    TableColumn<Spectacol,Integer> availableSeatsColumn2;
    @FXML
    public TextField cumparatorNameTextField;
    @FXML
    public TextField nrSeatsTextField;
    @FXML
    public Button cumparaBiletButton;
    @FXML
    public Button refreshButton;
    private Stage loginStage;
    private String getFormattedDateFromDatePicker(DatePicker datePicker) {
        //Get the selected date
        LocalDate selectedDate = datePicker.getValue();
        //Create DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //Convert LocalDate to formatted String
        return selectedDate.format(formatter);
    }
    public MainController() {
        System.out.println("MainController created");
    }

    @FXML
    public void setEmployee(Angajat employee) {
        this.loggedAngajat = employee;
    }

    @FXML
    public void setService(IService service) {
        this.service = service;
    }

    @FXML
    public void setStage(Stage stage) {
        this.loginStage = stage;
    }
    public void init() throws MyException {
        try {
            init_lists();
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

    private void showAllSpectacole() throws MyException {
        List<Spectacol> shows = (List<Spectacol>) this.service.getAllSpectacole();
        spectacoleList = FXCollections.observableArrayList(shows);
        spectacoleTable.setItems(spectacoleList);
        spectacoleTable.refresh();

        if (spectacolDatePicker.getValue()!=null)
            onSearchSpectacol();
    }
    @FXML
    public void initialize() {
        artistNameColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("artistName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("time"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("place"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,Integer>("availableSeats"));
        soldSeatsColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,Integer>("soldSeats"));
        spectacoleTable.setItems(spectacoleList);

        artistNameColumn2.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("artistName"));
        timeColumn2.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("time"));
        placeColumn2.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("place"));
        availableSeatsColumn2.setCellValueFactory(new PropertyValueFactory<Spectacol,Integer>("availableSeats"));
        spectacoleCautateTable.setItems(spectacoleCautateList);
    }

    @FXML
    public void init_lists() throws MyException {
        showAllSpectacole();

        spectacoleTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Spectacol item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null)
                    setStyle("");
                else if (item.getAvailableSeats() != 0)
                    setStyle("");
                else if (item.getAvailableSeats() == 0)
                    setStyle("-fx-background-color: #F8584B;");
                else
                    setStyle("");
            }
        });
        spectacoleCautateTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Spectacol item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null)
                    setStyle("");
                else if (item.getAvailableSeats() != 0)
                    setStyle("");
                else if (item.getAvailableSeats() == 0)
                    setStyle("-fx-background-color: #F8584B;");
                else
                    setStyle("");
            }
        });
    }

    @FXML
    public void onSearchSpectacol() throws MyException {
        if(spectacolDatePicker.getValue()!=null) {
            Iterable<Spectacol> spectacole = service.getAllSpectacole();
            List<Spectacol> spectacoleTemp = new ArrayList<>();
            for (Spectacol spectacol : spectacole) {
                String date = spectacol.getDate();
                if (date.compareTo(getFormattedDateFromDatePicker(spectacolDatePicker)) == 0) {
                    spectacoleTemp.add(spectacol);
                }
            }
            spectacoleCautateList.setAll(spectacoleTemp);
            spectacoleCautateTable.setItems(spectacoleCautateList);
            spectacoleCautateTable.refresh();
        }
    }

    @FXML
    public void onCumparaBiletButton() {
        try {
            Spectacol spectacol = spectacoleCautateTable.getSelectionModel().getSelectedItem();
            String cumparatorName=cumparatorNameTextField.getText();
            int nrSeats=Integer.parseInt(nrSeatsTextField.getText());
            service.addBilet(new Bilet(1L,cumparatorName,spectacol.getId(),nrSeats));
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bilet cumparat", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, npe.getMessage(), ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    public void onRefresh() throws MyException {
        init_lists();
        if (spectacolDatePicker.getValue()!=null)
            onSearchSpectacol();
    }
    public void logout() {
        try {
            service.logout(loggedAngajat, this);
            loginStage.show();
        } catch (MyException e) {
            System.out.println("Logout error " + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void logoutAction() {
        logout();
        Stage stage = (Stage) this.spectacoleTable.getScene().getWindow();
        stage.close();
    }
    public void biletAdded(Bilet ticket) {
        Platform.runLater(() -> {
            try {
                showAllSpectacole();
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });
    }
}