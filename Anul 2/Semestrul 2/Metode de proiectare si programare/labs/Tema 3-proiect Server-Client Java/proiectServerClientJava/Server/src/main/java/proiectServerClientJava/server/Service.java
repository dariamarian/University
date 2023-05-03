package proiectServerClientJava.server;

import proiectServerClientJava.domain.Angajat;
import proiectServerClientJava.domain.Bilet;
import proiectServerClientJava.domain.Spectacol;
import proiectServerClientJava.domain.validators.ValidationException;
import proiectServerClientJava.domain.validators.Validator;
import proiectServerClientJava.persistence.repository.jdbc.RepoAngajat;
import proiectServerClientJava.persistence.repository.jdbc.RepoBilet;
import proiectServerClientJava.persistence.repository.jdbc.RepoSpectacol;
import proiectServerClientJava.service.IObserver;
import proiectServerClientJava.service.IService;
import proiectServerClientJava.service.MyException;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Service implements IService{
    private final Validator<Angajat> angajatValidator;
    private final Validator<Spectacol> spectacolValidator;
    private final Validator<Bilet> biletValidator;
    private final RepoAngajat angajatRepo;
    private final RepoSpectacol spectacolRepo;
    private final RepoBilet biletRepo;
    private Map<Long, IObserver> loggedAngajati;


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
        loggedAngajati =new ConcurrentHashMap<>();
    }

    @Override
    public Angajat getAngajatByUsername(String username){
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
    public void addBilet(String cumparatorName, long idSpectacol, int nrSeats) throws MyException, ValidationException {
        if(getSpectacol(idSpectacol).getAvailableSeats()>=nrSeats)
        {
            Bilet toAdd = new Bilet(cumparatorName,idSpectacol,nrSeats);
            Spectacol spectacol=getSpectacol(idSpectacol);
            biletValidator.validate(toAdd);
            biletRepo.add(toAdd);
            spectacolRepo.update(spectacol,nrSeats);
            for (IObserver loggedUser: loggedAngajati.values()) {
                loggedUser.biletAdded(toAdd);
            }
        }
        else
        {
         throw new MyException("Nu exista atatea locuri disponibile.");
        }
    }

    public synchronized Angajat login(Angajat employee, IObserver client) throws Exception {
        Angajat employeeToLogin=angajatRepo.authenticateAngajat(employee.getUsername(),employee.getPassword());
        if (employeeToLogin!=null){
            if (loggedAngajati.get(employeeToLogin.getId())!=null)
                throw new Exception("Angajat already logged in.");
            loggedAngajati.put(employeeToLogin.getId(),client);
        }
        else
            throw new Exception("Authentication failed.");
        return employeeToLogin;
    }
    public synchronized void logout(Angajat employee, IObserver client) throws Exception {
        IObserver loggedEmployee= loggedAngajati.remove(employee.getId());
        if (loggedEmployee==null)
            throw new Exception("Employee "+String.valueOf(employee.getId())+" is not logged in.");
    }
}
