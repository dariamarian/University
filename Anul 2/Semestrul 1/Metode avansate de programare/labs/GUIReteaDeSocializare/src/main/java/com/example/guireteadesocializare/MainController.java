package com.example.guireteadesocializare;

import domain.*;
import exceptions.RepoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController {
    Service service;
    private Utilizator loggedUser=null;
    ObservableList<Utilizator> usersList = FXCollections.observableArrayList();
    ObservableList<UtilizatorDTOPrieten> friendshipList = FXCollections.observableArrayList();
    ObservableList<Utilizator> friendRequestsList = FXCollections.observableArrayList();
    ObservableList<Utilizator> sentFriendRequestsList = FXCollections.observableArrayList();
    @FXML
    private TableView<Utilizator> usersTable;
    @FXML
    private TableView<UtilizatorDTOPrieten> friendsTable;
    @FXML
    public TableView<Utilizator> friendRequestsView;
    @FXML
    public TableView<Utilizator> sentFriendRequestsView;

    @FXML
    TableColumn<Utilizator,String> firstNameColumn;
    @FXML
    TableColumn<Utilizator,String>lastNameColumn;
    @FXML
    TableColumn<Utilizator,String>emailColumn;
    @FXML
    public TableColumn<UtilizatorDTOPrieten, String> friendNameColumn;
    @FXML
    public TableColumn<UtilizatorDTOPrieten, String> friendsSinceColumn;
    @FXML
    TableColumn<Utilizator,String>firstNameColumn2;
    @FXML
    TableColumn<Utilizator,String>lastNameColumn2;
    @FXML
    TableColumn<Utilizator,String>firstNameColumn3;
    @FXML
    TableColumn<Utilizator,String>lastNameColumn3;
    @FXML
    public Label loggedUserName;
    @FXML
    public Label loggedUserEmail;
    @FXML
    public Button LogOutButton;
    @FXML
    public TextField searchUserTextField;
    @FXML
    public Button addFriendshipButton;
    @FXML
    public Button removeFriendButton;
    @FXML
    public Button showFriendRequestsButton;
    @FXML
    public Button showSentFriendRequestsButton;
    @FXML
    public AnchorPane friendRequestsPane;
    @FXML
    public AnchorPane sentFriendRequestsPane;
    @FXML
    public Button acceptFriendButton;
    @FXML
    public Button rejectFriendButton;
    @FXML
    public Button cancelRequestButton;

    ObservableList<MessageDTO> messagesList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<MessageDTO, String> messageToUserColumn;

    @FXML
    private TableColumn<MessageDTO, String> messageFromUserColumn;

    @FXML
    private TableView<MessageDTO> chatTable;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button sendMessageButton;
    @FXML
    private AnchorPane chatPane;

    @FXML
    private Button refreshButton;

    @FXML
    public Button showMessages;

    public void SetService(Service service, Utilizator user) throws RepoException {
        this.service = service;
        this.loggedUser = user;

        friendRequestsPane.setVisible(visibleFriendrequestsPane);
        sentFriendRequestsPane.setVisible(visibleSentfriendrequestsPane);
        chatPane.setVisible(visibleChatPane);
        loggedUserName.setText(loggedUser.getFirstName() + " " + loggedUser.getLastName());
        loggedUserEmail.setText(loggedUser.getEmail());
        init_lists();
        refreshFriendsTable();
        initialize();
    }

    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("email"));
        usersTable.setItems(usersList);


        friendNameColumn.setCellValueFactory(new PropertyValueFactory<UtilizatorDTOPrieten, String>("name_user"));
        friendsSinceColumn.setCellValueFactory(new PropertyValueFactory<UtilizatorDTOPrieten, String>("friendsSince"));
        friendsTable.setItems(friendshipList);

        firstNameColumn2.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        lastNameColumn2.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        friendRequestsView.setItems(friendRequestsList);

        firstNameColumn3.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        lastNameColumn3.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        sentFriendRequestsView.setItems(sentFriendRequestsList);

        messageToUserColumn.setCellValueFactory(new PropertyValueFactory<>("toMessage"));
        messageFromUserColumn.setCellValueFactory(new PropertyValueFactory<>("fromMessage"));

        chatTable.setItems(messagesList);

        searchUserTextField.textProperty().addListener(o -> onSearchUserTextField());
    }
    private void refreshMessage() throws RepoException {
        long fromUserID;
        if(friendsTable.getSelectionModel().getSelectedItem() != null)
            fromUserID= friendsTable.getSelectionModel().getSelectedItem().getUID();
        else
            return;
        List<Message> messageList = service.getMessagesBetweenUsers(loggedUser.getId(), fromUserID);


        List<MessageDTO> messages = new ArrayList<>();
        for (Message message : messageList) {
            MessageDTO messageDTO;
            if (Objects.equals(message.getIdSender(), loggedUser.getId())) {
                messageDTO = new MessageDTO("", message.getMessageText());
            } else {
                messageDTO = new MessageDTO(message.getMessageText(), "");
            }
            messages.add(messageDTO);
        }
        messagesList.setAll(messages);
    }
    @FXML
    public void onUserFromUserMessageListClick() throws RepoException {
        friendRequestsPane.setVisible(false);
        sentFriendRequestsPane.setVisible(false);
        chatPane.setVisible(true);
        showFriendRequestsButton.setText("Show friend requests");
        showSentFriendRequestsButton.setText("Show sent friend requests");
        showMessages.setText("Hide messages");
        refreshMessage();
    }

    @FXML
    public void onSendMessageButton(){
        String messageText = messageTextField.getText();
        try{
            //long id_recipientUser = usersMessageList.getSelectionModel().getSelectedItem().getUID();
            long id_recipientUser = friendsTable.getSelectionModel().getSelectedItem().getUID();
            service.addMessage(loggedUser.getId(),id_recipientUser,messageText);
        }
        catch (RepoException ex){
            Alert alert=new Alert(Alert.AlertType.ERROR, ex.getMessage(),ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void init_lists() throws RepoException {
        Iterable<Utilizator> list =  service.getAllUsers();
        List<Utilizator> users = StreamSupport.stream(list.spliterator(), false)
                .filter(user -> user.getId() != loggedUser.getId())
                .map(u -> new Utilizator(u.getFirstName(), u.getLastName(),u.getEmail(),u.getPassword()))
                .collect(Collectors.toList());
        usersList.setAll(users);
    }
    private void refreshFriendsTable() throws RepoException {
        if (loggedUser != null && service.getAllFriendshipsForUser(loggedUser.getId()) != null) {
            HashMap<Long, String> friendsOfUser = service.getAllFriendshipsForUser(loggedUser.getId());

            List<UtilizatorDTOPrieten> friendsTemp = new ArrayList<>();

            for (Long idd : friendsOfUser.keySet()) {
                Utilizator user=service.getUser(idd);
                UtilizatorDTOPrieten friend = new UtilizatorDTOPrieten(user, friendsOfUser.get(idd));
                friendsTemp.add(friend);
            }
            friendshipList.setAll(friendsTemp);

            HashMap<Long, String> friendReq = service.getFriendRequests(loggedUser.getId());
            List<Utilizator> friendReqTemp = new ArrayList<>();
            for (Long id : friendReq.keySet()) {
                Utilizator user=service.getUser(id);
                friendReqTemp.add(user);
            }
            friendRequestsList.setAll(friendReqTemp);

            HashMap<Long, String> sentFriendReq = service.getSentFriendRequests(loggedUser.getId());
            List<Utilizator> sentFriendReqTemp = new ArrayList<>();
            for (Long id : sentFriendReq.keySet()) {
                Utilizator user=service.getUser(id);
                sentFriendReqTemp.add(user);
            }
            sentFriendRequestsList.setAll(sentFriendReqTemp);
        }
        else {
            friendshipList.setAll(); friendRequestsList.setAll(); sentFriendRequestsList.setAll();
        }
    }
    @FXML
    public void onAddFriendButton() {
        try {
            Utilizator userToAdd = usersTable.getSelectionModel().getSelectedItem();
            Utilizator user2=service.getUserByName(userToAdd.getFirstName()+" "+userToAdd.getLastName());
            service.addFriendship(loggedUser.getId(), user2.getId());
            refreshFriendsTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sent friend request", ButtonType.OK);
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
    protected void onDeleteFriendButton() {
        try {
            UtilizatorDTOPrieten user = friendsTable.getSelectionModel().getSelectedItem();
            Utilizator user_cautat=service.getUserByName(user.getName_user());
            Prietenie prietenie_cautata=service.getFriendshipByUserId(user_cautat.getId(), loggedUser.getId());
            service.removeFriendship(prietenie_cautata.getId());
            refreshFriendsTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Friend removed", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No friend selected!", ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    @FXML
    public void onAcceptButton() {
        try {
            Utilizator userToAccept = friendRequestsView.getSelectionModel().getSelectedItem();
            service.addFriendship(loggedUser.getId(),userToAccept.getId());
            refreshFriendsTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Accepted friend request", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No friend selected!", ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    @FXML
    public void onRejectFriendrequestButton() {
        try {
            long userID = friendRequestsView.getSelectionModel().getSelectedItem().getId();
            Prietenie prietenie_cautata=service.getFriendshipByUserId(userID,loggedUser.getId());
            service.removeFriendship(prietenie_cautata.getId());
            refreshFriendsTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Rejected friend request", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No friend selected!", ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void onCancelFriendRequestButton() {
        try {
            long userID = sentFriendRequestsView.getSelectionModel().getSelectedItem().getId();
            Prietenie prietenie_cautata=service.getFriendshipByUserId(userID,loggedUser.getId());
            service.removeFriendship(prietenie_cautata.getId());
            refreshFriendsTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Canceled friend request", ButtonType.OK);
            alert.show();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No friend selected!", ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }
    private boolean visibleFriendrequestsPane = false;
    private boolean visibleSentfriendrequestsPane = false;
    private boolean visibleChatPane = false;
    @FXML
    public void onShowFriendrequestsButton() {
        chatPane.setVisible(false);
        sentFriendRequestsPane.setVisible(false);
        if (visibleFriendrequestsPane == false) {
            visibleFriendrequestsPane = true;
            showFriendRequestsButton.setText("Hide friend requests");

        } else {
            visibleFriendrequestsPane = false;
            showFriendRequestsButton.setText("Show friend requests");
        }
        showMessages.setText("Show messages");
        showSentFriendRequestsButton.setText("Show sent friend requests");
        friendRequestsPane.setVisible(visibleFriendrequestsPane);
    }
    @FXML
    public void onShowSentFriendRequestsButton() {
        chatPane.setVisible(false);
        friendRequestsPane.setVisible(false);
        if (visibleSentfriendrequestsPane == false) {
            visibleSentfriendrequestsPane = true;
            showSentFriendRequestsButton.setText("Hide sent friend requests");

        } else {
            visibleSentfriendrequestsPane = false;
            showSentFriendRequestsButton.setText("Show sent friend requests");
        }
        showMessages.setText("Show messages");
        showFriendRequestsButton.setText("Show friend requests");
        sentFriendRequestsPane.setVisible(visibleSentfriendrequestsPane);
    }

    @FXML
    public void onShowMessagesButton() {
        friendRequestsPane.setVisible(false);
        sentFriendRequestsPane.setVisible(false);
        if (visibleChatPane == false) {
            visibleChatPane = true;
            showMessages.setText("Hide messages");

        } else {
            visibleChatPane = false;
            showMessages.setText("Show messages");
        }
        showFriendRequestsButton.setText("Show friend requests");
        showSentFriendRequestsButton.setText("Show sent friend requests");
        chatPane.setVisible(visibleChatPane);
    }
    @FXML
    public void onSearchUserTextField() {
        Iterable<Utilizator> users = service.getAllUsers();
        List<Utilizator> usersTemp = new ArrayList<>();
        for (Utilizator user : users) {
            String name = user.getFirstName() + " " + user.getLastName();
            if (name.startsWith(searchUserTextField.getText()) && user.getId() != loggedUser.getId())
                usersTemp.add(user);
        }
        usersList.setAll(usersTemp);
        usersTable.setItems(usersList);
    }
    @FXML
    public void onLogoutButtonAction(){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LogInSignUp.fxml"));
        Scene scene;
        try{
            scene = new Scene(loader.load(), 210, 500);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return;
        }
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
    public void onRefresh() throws RepoException {
        init_lists();
        refreshFriendsTable();
        refreshMessage();
    }
}
