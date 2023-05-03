package proiectServerClientJava.service;

import proiectServerClientJava.domain.Angajat;
import proiectServerClientJava.domain.Bilet;
import proiectServerClientJava.domain.Spectacol;
import proiectServerClientJava.domain.validators.ValidationException;

import java.time.LocalTime;

public interface IService {
    Angajat getAngajatByUsername(String username) throws MyException;
    Iterable<Angajat> getAllAngajati() throws MyException;
    Spectacol getSpectacol(Long id) throws MyException;
    Spectacol getSpectacolByAll(String artistName, LocalTime time, String place) throws MyException;
    Iterable<Spectacol> getAllSpectacole() throws MyException;
    void addBilet(String cumparatorName, long idSpectacol, int nrSeats) throws MyException, ValidationException;
    Angajat login(Angajat employee, IObserver client) throws Exception;
    void logout(Angajat employee, IObserver client) throws Exception;
}
