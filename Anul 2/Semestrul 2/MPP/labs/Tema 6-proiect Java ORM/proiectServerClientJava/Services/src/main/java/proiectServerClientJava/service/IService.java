package proiectServerClientJava.service;

import proiectServerClientJava.domain.Angajat;
import proiectServerClientJava.domain.Bilet;
import proiectServerClientJava.domain.Spectacol;
import proiectServerClientJava.domain.validators.ValidationException;

import java.util.List;

public interface IService {
    Spectacol getSpectacol(Long id) throws MyException;
    Iterable<Spectacol> getAllSpectacole() throws MyException;
    void addBilet(Bilet bilet) throws MyException, ValidationException;
    Angajat login(Angajat employee, IObserver client) throws Exception;
    void logout(Angajat employee, IObserver client) throws Exception;
}
