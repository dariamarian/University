package proiect.service;

import proiect.domain.Angajat;
import proiect.domain.Bilet;
import proiect.domain.Spectacol;
import proiect.domain.validators.ValidationException;

public interface IService {
    Spectacol getSpectacol(int id) throws MyException;
    Iterable<Spectacol> getAllSpectacole() throws MyException;
    void addBilet(Bilet bilet) throws MyException, ValidationException;
    Angajat login(Angajat employee, IObserver client) throws Exception;
    void logout(Angajat employee, IObserver client) throws Exception;
}
