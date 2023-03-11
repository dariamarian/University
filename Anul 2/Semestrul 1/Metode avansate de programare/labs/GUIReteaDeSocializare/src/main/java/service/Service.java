package service;

import domain.Message;
import domain.Prietenie;
import domain.Utilizator;
import exceptions.NetworkException;
import exceptions.RepoException;
import exceptions.ValidationException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public interface Service {
    /**
     * adauga o entitate
     *
     * @param firstName - prenumele entitatii
     * @param lastName  - numele entitatii
     * @throws ValidationException daca entitatea nu e valida
     * @throws RepoException       daca entitatea exista deja
     */
    void add(String firstName, String lastName, String email, String password) throws ValidationException, RepoException;

    /**
     * sterge o entitate
     *
     * @param id - id-ul entitatii care trebuie stearsa
     * @throws RepoException daca entitatea nu exista
     */
    Utilizator remove(long id) throws RepoException, NetworkException;

    /**
     * @param id - id-ul entitatii care trebuie gasita
     * @return utilizatorul cu acel id
     * @throws RepoException daca entitatea nu exista
     */
    Utilizator getUser(Long id) throws RepoException;
    Utilizator getUserByName(String name) throws RepoException;
    Utilizator getUserByEmail(String email) throws RepoException;
    Prietenie getFriendshipByUserId(Long id1, Long id2) throws RepoException;

    /**
     * adauga o prietenie
     *
     * @param id1 - id-ul primei entitati
     * @param id2 - id-ul celei de a doua entitati
     * @throws NetworkException    daca sunt deja prieteni
     * @throws ValidationException daca prietenia nu e valida
     */
    void addFriendship(long id1, long id2) throws NetworkException, ValidationException, RepoException, SQLException;

    /**
     * sterge o prietenie
     *
     * @param id - id-ul prieteniei
     * @throws NetworkException    daca nu sunt prieteni
     * @throws ValidationException daca prietenia nu e valida
     */
    void removeFriendship(long id) throws NetworkException, ValidationException, RepoException;

    /**
     * returneaza numarul de comunitati
     *
     * @return numarul de comunitati
     */
    int numberOfCommunities();

    /**
     * returneaza cea mai sociabila comunitate
     *
     * @return cea mai sociabila comunitate
     */
    Vector<Utilizator> mostSociableCommunity();

    /**
     * @return lista de entitati
     */
    Iterable<Utilizator> getAllUsers();
    HashMap<Long, String>  getFriendRequests(long id);
    HashMap<Long, String>  getSentFriendRequests(long id);

    Iterable<Prietenie> getAllFriendships();
    HashMap<Long, String> getAllFriendshipsForUser(long ID);

    int numberOfUsers();

    int numberOfFriendships();

    List<Message> getMessagesForUser(long id_user);
    List<Message> getMessagesBetweenUsers(long id_user1, long id_user2);
    void addMessage(long id_sender, long id_receiver, String message) throws RepoException;
}

