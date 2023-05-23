package proiectServerClientJava.server;

import proiect.domain.Angajat;
import proiect.domain.Bilet;
import proiect.domain.Spectacol;
import proiect.domain.validators.ValidationException;
import proiect.domain.validators.Validator;
import proiect.persistence.repository.jdbc.RepoAngajat;
import proiect.persistence.repository.jdbc.RepoBilet;
import proiect.persistence.repository.jdbc.RepoSpectacol;
import proiect.service.IObserver;
import proiect.service.IService;
import proiect.service.MyException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Service implements IService{
    private final Validator<Angajat> angajatValidator;
    private final Validator<Spectacol> spectacolValidator;
    private final Validator<Bilet> biletValidator;
    private final RepoAngajat angajatRepo;
    private final RepoSpectacol spectacolRepo;
    private final RepoBilet biletRepo;
    private Map<Integer, IObserver> loggedAngajati;


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
    public Spectacol getSpectacol(int id){
        return spectacolRepo.findElement(id);
    }

    @Override
    public List<Spectacol> getAllSpectacole() {
        return spectacolRepo.getAll();
    }

    @Override
    public void addBilet(Bilet bilet) throws MyException, ValidationException {
        if(getSpectacol(bilet.getIdSpectacol()).getAvailableSeats()>=bilet.getNrSeats())
        {
            Spectacol spectacol=getSpectacol(bilet.getIdSpectacol());
            biletValidator.validate(bilet);
            biletRepo.add(bilet);
            spectacolRepo.update(spectacol,bilet.getNrSeats());
            for (IObserver loggedUser: loggedAngajati.values()) {
                loggedUser.biletAdded(bilet);
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
