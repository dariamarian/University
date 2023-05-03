package com.example.proiect.controllers;

import com.example.proiect.Main;
import com.example.proiect.exceptions.RepoException;
import com.example.proiect.model.Angajat;
import com.example.proiect.model.Bilet;
import com.example.proiect.model.Spectacol;
import com.example.proiect.model.SpectacolDTO;
import com.example.proiect.service.IService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    IService service;
    private Angajat loggedAngajat=null;
    ObservableList<Spectacol> spectacoleList = FXCollections.observableArrayList();
    ObservableList<SpectacolDTO> spectacoleCautateList = FXCollections.observableArrayList();
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
    TableColumn<Spectacol,String> placeColumn;
    @FXML
    TableColumn<Spectacol,Integer> availableSeatsColumn;
    @FXML
    TableColumn<Spectacol,Integer> soldSeatsColumn;
    @FXML
    private TableView<SpectacolDTO> spectacoleCautateTable;
    @FXML
    TableColumn<SpectacolDTO,String> artistNameColumn2;
    @FXML
    TableColumn<SpectacolDTO,String> timeColumn;
    @FXML
    TableColumn<SpectacolDTO,String> placeColumn2;
    @FXML
    TableColumn<SpectacolDTO,Integer> availableSeatsColumn2;
    @FXML
    public TextField cumparatorNameTextField;
    @FXML
    public TextField nrSeatsTextField;
    @FXML
    public Button cumparaBiletButton;
    @FXML
    public Button refreshButton;

    public void SetService(IService service, Angajat angajat) throws RepoException {
        this.service = service;
        this.loggedAngajat = angajat;
        init_lists();
        initialize();
    }

    @FXML
    public void initialize() {
        artistNameColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("artistName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("date"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,String>("place"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,Integer>("availableSeats"));
        soldSeatsColumn.setCellValueFactory(new PropertyValueFactory<Spectacol,Integer>("soldSeats"));
        spectacoleTable.setItems(spectacoleList);

        artistNameColumn2.setCellValueFactory(new PropertyValueFactory<SpectacolDTO,String>("artistName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<SpectacolDTO,String>("time"));
        placeColumn2.setCellValueFactory(new PropertyValueFactory<SpectacolDTO,String>("place"));
        availableSeatsColumn2.setCellValueFactory(new PropertyValueFactory<SpectacolDTO,Integer>("availableSeats"));
        spectacoleCautateTable.setItems(spectacoleCautateList);
    }

    @FXML
    public void init_lists() throws RepoException {
        Iterable<Spectacol> spectacole = service.getAllSpectacole();
        List<Spectacol> spectacoleTemp = new ArrayList<>();
        for (Spectacol spectacol : spectacole) {
            spectacoleTemp.add(spectacol);
        }
        spectacoleList.setAll(spectacoleTemp);

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
            protected void updateItem(SpectacolDTO item, boolean empty) {
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
    public void onSearchSpectacol() {
        Iterable<Spectacol> spectacole = service.getAllSpectacole();
        List<SpectacolDTO> spectacoleTemp = new ArrayList<>();
        for (Spectacol spectacol : spectacole) {
            LocalDate date = spectacol.getDate().toLocalDate();
            if (date.compareTo(spectacolDatePicker.getValue())==0)
            {
                SpectacolDTO spectacolDTO=new SpectacolDTO(spectacol);
                spectacoleTemp.add(spectacolDTO);
            }
        }
        spectacoleCautateList.setAll(spectacoleTemp);
        spectacoleCautateTable.setItems(spectacoleCautateList);
    }

    @FXML
    public void onCumparaBiletButton() {
        try {
            SpectacolDTO spectacolDTO = spectacoleCautateTable.getSelectionModel().getSelectedItem();
            String cumparatorName=cumparatorNameTextField.getText();
            int nrSeats=Integer.parseInt(nrSeatsTextField.getText());
            Spectacol spectacol=service.getSpectacolByAll(spectacolDTO.getArtistName(),spectacolDTO.getTime(),spectacolDTO.getPlace());
            service.addBilet(cumparatorName,spectacol.getId(),nrSeats);
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
    public void onRefresh() throws RepoException {
        init_lists();
        if (spectacolDatePicker.getValue()!=null)
            onSearchSpectacol();
    }
}