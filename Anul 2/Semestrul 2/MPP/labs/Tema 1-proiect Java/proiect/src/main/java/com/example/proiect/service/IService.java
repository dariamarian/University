package com.example.proiect.service;

import com.example.proiect.exceptions.RepoException;
import com.example.proiect.exceptions.ValidationException;
import com.example.proiect.model.Angajat;
import com.example.proiect.model.Bilet;
import com.example.proiect.model.Spectacol;

import java.time.LocalTime;

public interface IService {
    void addAngajat(String name, String username, String password) throws ValidationException;
    Angajat removeAngajat(long id);
    Angajat getAngajat(Long id);
    Angajat getAngajatByUsername(String username) throws RepoException;
    Iterable<Angajat> getAllAngajati();

    void addSpectacol(String artistName, String date, String place, int availableSeats, int soldSeats) throws ValidationException;
    Spectacol removeSpectacol(long id);
    Spectacol getSpectacol(Long id);
    Spectacol getSpectacolByAll(String artistName, LocalTime time, String place);
    Iterable<Spectacol> getAllSpectacole();

    void addBilet(String cumparatorName, long idSpectacol, int nrSeats) throws ValidationException, RepoException;
    Bilet removeBilet(long id);
    Bilet getBilet(Long id);
    Iterable<Bilet> getAllBilete();

}
