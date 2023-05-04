package com.example.proiect.service;

import com.example.proiect.exceptions.RepoException;
import com.example.proiect.exceptions.ValidationException;
import com.example.proiect.model.Angajat;
import com.example.proiect.model.Bilet;
import com.example.proiect.model.Entity;
import com.example.proiect.model.Spectacol;
import com.example.proiect.model.validators.Validator;
import com.example.proiect.repository.RepoAngajat;
import com.example.proiect.repository.RepoBilet;
import com.example.proiect.repository.RepoSpectacol;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Service implements IService{
    private final Validator<Angajat> angajatValidator;
    private final Validator<Spectacol> spectacolValidator;
    private final Validator<Bilet> biletValidator;
    private final RepoAngajat angajatRepo;
    private final RepoSpectacol spectacolRepo;
    private final RepoBilet biletRepo;

    public Service(Validator<Angajat> validatorA,
                   Validator<Spectacol> validatorS,
                   Validator<Bilet> validatorB,
                   RepoAngajat angajatRepo,
                   RepoSpectacol spectacolRepo,
                   RepoBilet biletRepo)
    {
        this.angajatValidator=validatorA;
        this.spectacolValidator=validatorS;
        this.biletValidator=validatorB;
        this.angajatRepo=angajatRepo;
        this.spectacolRepo=spectacolRepo;
        this.biletRepo=biletRepo;
    }
    @Override
    public void addAngajat(String name, String username, String password) throws ValidationException{
        Angajat toAdd = new Angajat(name, username, password);
        angajatValidator.validate(toAdd);
        angajatRepo.add(toAdd);
    }

    @Override
    public Angajat removeAngajat(long id){
        return angajatRepo.remove(id);
    }

    @Override
    public Angajat getAngajat(Long id){
        return angajatRepo.findElement(id);
    }

    @Override
    public Angajat getAngajatByUsername(String username) throws RepoException {
        Iterable<Angajat> angajati = getAllAngajati();
        for (Angajat angajat : angajati) {
            String username2 = angajat.getUsername();
            if (Objects.equals(username, username2))
                return angajat;
        }
        return null;
    }

    @Override
    public Iterable<Angajat> getAllAngajati() {
        return angajatRepo.getAll();
    }

    @Override
    public void addSpectacol(String artistName, String date, String place, int availableSeats, int soldSeats) throws ValidationException{
        Spectacol toAdd = new Spectacol(artistName,place,availableSeats,soldSeats);
        toAdd.setDate(date);
        spectacolValidator.validate(toAdd);
        spectacolRepo.add(toAdd);
    }

    @Override
    public Spectacol removeSpectacol(long id) {
        return spectacolRepo.remove(id);
    }

    @Override
    public Spectacol getSpectacol(Long id){
        return spectacolRepo.findElement(id);
    }
    @Override
    public Spectacol getSpectacolByAll(String artistName, LocalTime time, String place){
        Iterable<Spectacol> spectacole = getAllSpectacole();
        for (Spectacol spectacol : spectacole) {
            String artistName2 = spectacol.getArtistName();
            LocalTime time2=spectacol.getDate().toLocalTime();
            String place2=spectacol.getPlace();
            if (Objects.equals(artistName, artistName2) && Objects.equals(time,time2)&&Objects.equals(place,place2))
                return spectacol;
        }
        return null;
    }
    @Override
    public Iterable<Spectacol> getAllSpectacole() {
        return spectacolRepo.getAll();
    }

    @Override
    public void addBilet(String cumparatorName, long idSpectacol, int nrSeats) throws ValidationException, RepoException {
        if(getSpectacol(idSpectacol).getAvailableSeats()>=nrSeats)
        {
            Bilet toAdd = new Bilet(cumparatorName,idSpectacol,nrSeats);
            Spectacol spectacol=getSpectacol(idSpectacol);
            biletValidator.validate(toAdd);
            biletRepo.add(toAdd);
            spectacolRepo.update(spectacol,nrSeats);
        }
        else
        {
         throw new RepoException("Nu exista atatea locuri disponibile.");
        }
    }

    @Override
    public Bilet removeBilet(long id) {
        return biletRepo.remove(id);
    }

    @Override
    public Bilet getBilet(Long id){
        return biletRepo.findElement(id);
    }

    @Override
    public Iterable<Bilet> getAllBilete() {
        return biletRepo.getAll();
    }
}
