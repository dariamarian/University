package com.example.examenmariandaria.controllers;

import com.example.examenmariandaria.Main;
import com.example.examenmariandaria.domain.Nevoie;
import com.example.examenmariandaria.domain.Persoana;
import com.example.examenmariandaria.repository.RepoException;
import com.example.examenmariandaria.repository.RepoNevoie;
import com.example.examenmariandaria.repository.RepoPersoana;
import com.example.examenmariandaria.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController {
    RepoPersoana repo1 = new RepoPersoana();
    RepoNevoie repo2 = new RepoNevoie();
    Service service = Service.getInstance(repo1, repo2);
    private Persoana loggedPersoana=null;
    @FXML
    public Label loggedPersoanaName;
    @FXML
    public Button LogOutButton;
    ObservableList<Nevoie> nevoiList = FXCollections.observableArrayList();
    ObservableList<Nevoie> nevoiList1 = FXCollections.observableArrayList();
    @FXML
    public TableView<Nevoie> nevoiTableView;
    @FXML
    TableColumn<Nevoie, String> titluColumn;
    @FXML
    TableColumn<Nevoie, String> descriereColumn;
    @FXML
    TableColumn<Nevoie, LocalDateTime> deadlineColumn;
    @FXML
    TableColumn<Nevoie, Long> ominnevoieColumn;
    @FXML
    TableColumn<Nevoie, Long> omsalvatorColumn;
    @FXML
    TableColumn<Nevoie, String> statusColumn;
    @FXML
    public TableView<Nevoie> nevoiTableView1;
    @FXML
    TableColumn<Nevoie, String> titluColumn1;
    @FXML
    TableColumn<Nevoie, String> descriereColumn1;
    @FXML
    TableColumn<Nevoie, LocalDateTime> deadlineColumn1;
    @FXML
    TableColumn<Nevoie, Long> ominnevoieColumn1;
    @FXML
    TableColumn<Nevoie, Long> omsalvatorColumn1;
    @FXML
    TableColumn<Nevoie, String> statusColumn1;
    @FXML
    public Button acceptNevoieButton;
    @FXML
    public TextField titluTextField;
    @FXML
    public TextField descriereTextField;
    @FXML
    public DatePicker deadlinePicker;
    @FXML
    public Button adaugaNevoieButton;
    @FXML
    public Button refreshButton;
    public void SetService(Service service, Persoana persoana) {
        this.loggedPersoana = persoana;
        loggedPersoanaName.setText("Hello " + loggedPersoana.getNume()+" "+loggedPersoana.getPrenume());
        init_lists();
        initialize();
    }
    @FXML
    public void init_lists() {
        Iterable<Nevoie> list =  service.getAllNevoi();
        List<Nevoie> nevoi = StreamSupport.stream(list.spliterator(), false)
                .filter(nevoie -> nevoie.getOmInNevoie() != loggedPersoana.getId() && service.findPersoana(nevoie.getOmInNevoie()).getOras()  == loggedPersoana.getOras())
                .map(n -> new Nevoie(n.getId(),n.getTitlu(),n.getDescriere(),n.getDeadline(),n.getOmInNevoie(),n.getOmSalvator(),n.getStatus()))
                .collect(Collectors.toList());
        nevoiList.setAll(nevoi);

        Iterable<Nevoie> list1 =  service.getAllNevoi();
        List<Nevoie> nevoi1 = StreamSupport.stream(list1.spliterator(), false)
                .filter(nevoie -> nevoie.getOmSalvator() == loggedPersoana.getId())
                .map(n -> new Nevoie(n.getId(),n.getTitlu(),n.getDescriere(),n.getDeadline(),n.getOmInNevoie(),n.getOmSalvator(),n.getStatus()))
                .collect(Collectors.toList());
        nevoiList1.setAll(nevoi1);
    }
    @FXML
    public void initialize() {
        titluColumn.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        ominnevoieColumn.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
        omsalvatorColumn.setCellValueFactory(new PropertyValueFactory<>("omSalvator"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        nevoiTableView.setItems(nevoiList);

        titluColumn1.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriereColumn1.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadlineColumn1.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        ominnevoieColumn1.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
        omsalvatorColumn1.setCellValueFactory(new PropertyValueFactory<>("omSalvator"));
        statusColumn1.setCellValueFactory(new PropertyValueFactory<>("status"));
        nevoiTableView1.setItems(nevoiList1);
    }
    @FXML
    public void onLogoutButtonAction() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LogInSignUp.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 220, 500);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        onRefresh();
        LogInSignupController controller=loader.getController();
        controller.SetService(service);
        Stage currentStage= (Stage) LogOutButton.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("LogIn");
        currentStage.close();
        newStage.show();
    }

    @FXML
    public void onAcceptButton() {
        try {
            Nevoie nevoieToAccept = nevoiTableView.getSelectionModel().getSelectedItem();
            if(nevoieToAccept.getStatus().equals("Erou gasit!"))
                throw new RepoException("Nevoia are deja un salvator");
            else
            {
                service.acceptNevoie(nevoieToAccept,loggedPersoana.getId());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nevoie atribuita!", ButtonType.OK);
                alert.show();
                onRefresh();
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onRefresh(){
        init_lists();
        initialize();
    }

    private String checkErrorsForNeed(String titlu, String descriere) {
        String errors = "";
        if (titlu.isEmpty()) {
            errors += "Title can't be empty.\n";
        }
        if (descriere.isEmpty()) {
            errors += "Description can't be empty.\n";
        }

        return errors;
    }
    @FXML
    public void onAdaugaNevoieButtonAction() {
        String titlu = titluTextField.getText();
        String descriere = descriereTextField.getText();
        LocalDateTime deadlineDate=deadlinePicker.getValue().atStartOfDay();
        try {
            if (deadlinePicker.getValue() == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Deadline can't be empty.\n", ButtonType.OK);
                alert.show();
                return;
            }
            String errors = checkErrorsForNeed(titlu,descriere);
            if (!errors.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, errors, ButtonType.OK);
                alert.show();
                return;
            }
            service.addNevoie(titlu,descriere,deadlineDate,loggedPersoana.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nevoie adaugata cu success!", ButtonType.OK);
            alert.show();
            onRefresh();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
}