package proiect.service;


import proiect.domain.Bilet;

public interface IObserver {
     void biletAdded(Bilet bilet) throws MyException;
}