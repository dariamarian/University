package service;

import domain.Entity;
import domain.Message;
import domain.Prietenie;
import domain.Utilizator;
import domain.validators.Validator;
import exceptions.NetworkException;
import exceptions.RepoException;
import exceptions.ValidationException;
import network.Retea;
import repository.DataBase.MessageDBRepo;
import repository.DataBase.PrietenieDBRepo;
import repository.DataBase.UtilizatorDBRepo;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserFriendshipService implements Service {
    private final Validator<Utilizator> userValidator;
    private final Validator<Prietenie> friendshipValidator;
    private final UtilizatorDBRepo userRepo;
    private final PrietenieDBRepo friendshipRepo;
    private final Retea network;

    private final MessageDBRepo messageRepo;

    public UserFriendshipService(Validator<Utilizator> validatorU,
                                 Validator<Prietenie> validatorP,
                                 UtilizatorDBRepo userRepo,
                                 PrietenieDBRepo friendshipRepo,
                                 MessageDBRepo messageRepo,
                                 Retea network) {
        this.userValidator = validatorU;
        this.friendshipValidator = validatorP;
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
        this.messageRepo = messageRepo;
        this.network = network;
        Iterable<Utilizator> users = userRepo.getAll();
        for (Utilizator user : users) {
            network.add(user);
        }

        Iterable<Prietenie> friendships = friendshipRepo.getAll();
        for (Prietenie friendship : friendships) {
            try {
                network.addFriendship(friendship);
            } catch (NetworkException e) {
                e.printStackTrace();
            }
        }
    }

    private Long getId(Iterable<? extends Entity<Long>> entities) {
        Set<Long> distinct = new TreeSet<>();
        long id = 1L;

        for (Entity<Long> entity : entities) {
            distinct.add(entity.getId());
        }

        while (true) {
            if (!distinct.contains(id)) {
                return id;
            }
            id = id + 1;
        }
    }


    @Override
    public void add(String firstName, String lastName, String email, String password) throws ValidationException, RepoException {
        Long id = getId(userRepo.getAll());
        Utilizator toAdd = new Utilizator(firstName, lastName, email, password);
        toAdd.setId(id);
        userValidator.validate(toAdd);
        network.add(toAdd);
        userRepo.add(toAdd);
    }

    @Override
    public Utilizator remove(long id) throws RepoException, NetworkException {
        Utilizator toDelete = userRepo.findElement(id);
        Vector<Prietenie> userFriendships = friendshipRepo.findUserFriends(id);
        for (Prietenie friendship : userFriendships) {
            network.removeFriendship(friendship);
            friendshipRepo.remove(friendship.getId());
        }
        network.remove(toDelete);
        return userRepo.remove(id);
    }

    @Override
    public Utilizator getUser(Long id) throws RepoException {
        return userRepo.findElement(id);
    }

    @Override
    public Utilizator getUserByName(String name) throws RepoException {
        Iterable<Utilizator> users = getAllUsers();
        for (Utilizator user : users) {
            String name2 = user.getFirstName() + " " + user.getLastName();
            if (Objects.equals(name, name2))
                return user;
        }
        return null;
    }

    @Override
    public Utilizator getUserByEmail(String email) throws RepoException {
        Iterable<Utilizator> users = getAllUsers();
        for (Utilizator user : users) {
            String email2 = user.getEmail();
            if (Objects.equals(email, email2))
                return user;
        }
        return null;
    }

    @Override
    public Prietenie getFriendshipByUserId(Long id1, Long id2) throws RepoException {
        Iterable<Prietenie> prietenii = getAllFriendships();
        for (Prietenie friendship : prietenii)
            if (friendship.getIdUser1() == id1 && friendship.getIdUser2() == id2 ||
                    friendship.getIdUser1() == id2 && friendship.getIdUser2() == id1)
                return friendship;
        return null;
    }

    public Prietenie existsPendingFriendship(Prietenie f1) {
        List<Prietenie> friendships =
                StreamSupport.stream(friendshipRepo.getAll().spliterator(), false)
                        .collect(Collectors.toList());
        Optional<Prietenie> optionalFriendship = friendships.stream()
                .filter(friendship -> friendship.getIdUser1() == f1.getIdUser2() && friendship.getIdUser2() == f1.getIdUser1())
                .findFirst();

        boolean isPresent = optionalFriendship.isPresent();
        if (isPresent)
            return optionalFriendship.get();
        else return null;
    }

    public Prietenie existsSentFriendRequest(Prietenie f1) {
        List<Prietenie> friendships =
                StreamSupport.stream(friendshipRepo.getAll().spliterator(), false)
                        .collect(Collectors.toList());
        Optional<Prietenie> optionalFriendship = friendships.stream()
                .filter(friendship -> friendship.getIdUser1() == f1.getIdUser1() && friendship.getIdUser2() == f1.getIdUser2())
                .findFirst();
        boolean isPresent = optionalFriendship.isPresent();
        if (isPresent)
            return optionalFriendship.get();
        else return null;
    }

    @Override
    public void addFriendship(long id1, long id2) throws NetworkException, ValidationException, RepoException, SQLException {
        userRepo.findElement(id1);
        userRepo.findElement(id2);
        Prietenie friendship = new Prietenie(id1, id2);

        friendshipValidator.validate(friendship);

        Long id = getId(friendshipRepo.getAll());
        Prietenie friendship1 = existsPendingFriendship(friendship);
        Prietenie friendship2 = existsSentFriendRequest(friendship);
        if (friendship1 == null && friendship2 == null) {
            friendship.setId(id);
            network.addFriendship(friendship);
            friendshipRepo.add(friendship);
        } else if (friendship2 != null)
            throw new RepoException("Ati trimis deja cererea de prietenie!");
        else if (friendship1 != null)
            friendshipRepo.acceptFriendshipInDB(friendship1);
    }

    @Override
    public void removeFriendship(long id) throws NetworkException, ValidationException, RepoException {
        Prietenie friendship = friendshipRepo.findElement(id);
        network.removeFriendship(friendship);
        friendshipRepo.remove(id);
    }

    @Override
    public int numberOfCommunities() {
        return network.getNumberOfCommunities();
    }

    @Override
    public Vector<Utilizator> mostSociableCommunity() {
        Vector<Long> communityIds = network.getMostSociableCommunity();
        Vector<Utilizator> community = new Vector<>();
        for (long id : communityIds) {
            try {
                community.add(userRepo.findElement(id));
            } catch (RepoException e) {
                System.out.println(e.getMessage());
            }
        }
        return community;
    }

    @Override
    public Iterable<Utilizator> getAllUsers() {
        return userRepo.getAll();
    }

    @Override
    public Iterable<Prietenie> getAllFriendships() {
        return friendshipRepo.getAll();
    }

    @Override
    public HashMap<Long, String> getAllFriendshipsForUser(long id) {
        HashMap<Long, String> users = new HashMap<>();
        Iterable<Prietenie> friendships = friendshipRepo.getAll();
        for (Prietenie friendship : friendships)
            if (friendship.getStatus().equals("ACCEPTED")) {
                if (friendship.getIdUser1() == id)
                    users.put(friendship.getIdUser2(), friendship.getFriendsFrom());
                else if (friendship.getIdUser2() == id)
                    users.put(friendship.getIdUser1(), friendship.getFriendsFrom());
            }
        return users;
    }

    @Override
    public HashMap<Long, String> getFriendRequests(long id) {
        HashMap<Long, String> friendRequests = new HashMap<>();
        Iterable<Prietenie> friendships = friendshipRepo.getAll();
        for (Prietenie friendship : friendships)
            if (friendship.getStatus().equals("PENDING")) {
                if (friendship.getIdUser2() == id)
                    friendRequests.put(friendship.getIdUser1(), friendship.getFriendsFrom());
            }
        return friendRequests;
    }

    @Override
    public HashMap<Long, String> getSentFriendRequests(long id) {
        HashMap<Long, String> sentFriendRequests = new HashMap<>();
        Iterable<Prietenie> friendships = friendshipRepo.getAll();
        for (Prietenie friendship : friendships)
            if (friendship.getStatus().equals("PENDING")) {
                if (friendship.getIdUser1() == id)
                    sentFriendRequests.put(friendship.getIdUser2(), friendship.getFriendsFrom());
            }
        return sentFriendRequests;
    }

    @Override
    public int numberOfUsers() {
        return userRepo.size();
    }

    @Override
    public int numberOfFriendships() {
        return friendshipRepo.size();
    }

    @Override
    public List<Message> getMessagesForUser(long id_user) {
        List<Message> messages1 =
                StreamSupport.stream(messageRepo.getAll().spliterator(), false)
                        .collect(Collectors.toList());
        List<Message> messages = messages1.stream()
                .filter(message -> message.getIdReceiver() == id_user || message.getIdSender() == id_user)
                .collect(Collectors.toList());

        Comparator<Message> comparator = Comparator.comparing(Message::getTimeSent);
        messages.sort(comparator);

        return messages;
    }

    @Override
    public void addMessage(long id_sender, long id_receiver, String message) throws RepoException {

        Message newMessage = new Message(id_sender, id_receiver, message);
        Long id = getId(messageRepo.getAll());
        newMessage.setId(id);
        messageRepo.add(newMessage);
    }

    @Override
    public List<Message> getMessagesBetweenUsers(long id_user1, long id_user2) {
        return getMessagesForUser(id_user1).stream()
                .filter(message -> message.getIdSender() == id_user2 || message.getIdReceiver() == id_user2)
                .collect(Collectors.toList());
    }
}