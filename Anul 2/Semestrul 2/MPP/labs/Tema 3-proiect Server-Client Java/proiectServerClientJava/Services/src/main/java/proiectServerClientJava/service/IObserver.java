package proiectServerClientJava.service;


import proiectServerClientJava.domain.Angajat;
import proiectServerClientJava.domain.Bilet;

public interface IObserver {
     void biletAdded(Bilet bilet) throws MyException;
}