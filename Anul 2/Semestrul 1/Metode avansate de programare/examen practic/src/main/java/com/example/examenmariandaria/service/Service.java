package com.example.examenmariandaria.service;

import com.example.examenmariandaria.domain.Entity;
import com.example.examenmariandaria.domain.Nevoie;
import com.example.examenmariandaria.domain.Orase;
import com.example.examenmariandaria.domain.Persoana;
import com.example.examenmariandaria.repository.RepoException;
import com.example.examenmariandaria.repository.RepoNevoie;
import com.example.examenmariandaria.repository.RepoPersoana;
import com.example.examenmariandaria.utils.events.EntityChangeEvent;
import com.example.examenmariandaria.utils.observers.Observer;
import com.example.examenmariandaria.utils.observers.Observable;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Service implements Observable<EntityChangeEvent> {
    private final List<Observer<EntityChangeEvent>> observers = new ArrayList<>();

    private static Service service = null;
    private final RepoNevoie repoNevoie;
    private final RepoPersoana repoPersoana;
    private Persoana persoana=null;

    public Service(RepoPersoana repoPersoana, RepoNevoie repoNevoie) {
        this.repoPersoana = repoPersoana;
        this.repoNevoie = repoNevoie;
    }

    public synchronized static Service getInstance( RepoPersoana repoPersoana,RepoNevoie repoNevoie) {
        if (service == null)
            service = new Service(repoPersoana,repoNevoie);
        return service;
    }

    public Persoana addPersoana(String nume, String prenume, String username, String parola, Orase oras, String strada, String numarstrada, String telefon)
    {
        Long id = getId(getAllPersoane());
        Persoana persoana=new Persoana(id,nume,prenume,username,parola,oras,strada,numarstrada,telefon);
        return repoPersoana.add(persoana);
    }
    public Nevoie addNevoie(String titlu, String descriere, LocalDateTime deadline,Long id_nevoie)
    {
        Long id = getId(getAllNevoi());
        Long omSalvator=Long.valueOf(0);
        String status="Caut erou!";
        Nevoie nevoie=new Nevoie(id,titlu,descriere,deadline,id_nevoie,omSalvator,status);
        return repoNevoie.add(nevoie);
    }
    public Iterable<Persoana> getAllPersoane() {
        return repoPersoana.getAll();
    }
    public void acceptNevoie(Nevoie nevoie, long id) throws RepoException, SQLException {

        repoNevoie.acceptNevoieInDB(nevoie,id);
    }

    public Iterable<Nevoie> getAllNevoi() {
        return repoNevoie.getAll();
    }

    public Persoana getPersoanaByUsername(String username) throws RepoException {
        Iterable<Persoana> persoane = getAllPersoane();
        for (Persoana persoana : persoane) {
            String username2 = persoana.getUsername();
            if (Objects.equals(username, username2))
                return persoana;
        }
        return null;
    }

    public Persoana findPersoana(Long id){
        return repoPersoana.findElement(id);
    }
    public Nevoie findNevoie(Long id){
        return repoNevoie.findElement(id);
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

    public Persoana getPersoana() {
        return persoana;
    }

    public void setPersoana(Persoana persoana) {
        this.persoana = persoana;
    }

    @Override
    public void addObserver(Observer<EntityChangeEvent> e) {

    }

    @Override
    public void removeObserver(Observer<EntityChangeEvent> e) {

    }

    @Override
    public void notifyObservers(EntityChangeEvent t) {

    }
}
