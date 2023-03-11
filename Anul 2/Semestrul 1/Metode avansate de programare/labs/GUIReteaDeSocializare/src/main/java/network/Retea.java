package network;

import domain.Prietenie;
import domain.Utilizator;
import exceptions.NetworkException;

import java.util.Vector;

public interface Retea {
    /**
     * adauga o entitate
     *
     * @param entity - entitatea care trebuie adaugata
     */
    void add(Utilizator entity);

    /**
     * sterge o entitate
     *
     * @param entity - entitatea care trebuie stearsa
     */
    void remove(Utilizator entity);

    /**
     * creeaza o prietenie intre doua entitati
     *
     * @param prietenie relatia de prietenie dintre 2 utilizatori
     * @throws NetworkException daca nu pot deveni prieteni
     */
    void addFriendship(Prietenie prietenie) throws NetworkException;

    /**
     * sterge prietenia dintre doua entitati
     *
     * @param prietenie relatia de prietenie dintre 2 utilizatori
     * @throws NetworkException daca nu sunt prieteni
     */
    void removeFriendship(Prietenie prietenie) throws NetworkException;

    /**
     * @return numarul de comunitati din retea
     */
    int getNumberOfCommunities();

    /**
     * @return cea mai sociabila comunitate
     */
    Vector<Long> getMostSociableCommunity();
}
