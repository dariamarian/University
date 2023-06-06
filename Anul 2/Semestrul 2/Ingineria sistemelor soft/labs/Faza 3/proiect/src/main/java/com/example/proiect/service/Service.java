package com.example.proiect.service;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.*;
import com.example.proiect.repository.*;
import org.hibernate.SessionFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Service{
    private static Service instance;
    private final RepoAdmin adminRepo;
    private final RepoPharmaceutist pharmaceutistRepo;
    private final RepoMedication medicationRepo;
    private final RepoMedicalPersonnel personnelRepo;
    private final RepoOrder orderRepo;
    private final Map<Map<Integer, String>, IObserver> iObservers;

    public Service(RepoAdmin adminRepo,
                   RepoPharmaceutist pharmaceutistRepo,
                   RepoMedicalPersonnel personnelRepo,
                   RepoMedication medicationRepo,
                   RepoOrder orderRepo)
    {
        this.adminRepo=adminRepo;
        this.pharmaceutistRepo=pharmaceutistRepo;
        this.personnelRepo=personnelRepo;
        this.medicationRepo=medicationRepo;
        this.orderRepo=orderRepo;
        iObservers = new ConcurrentHashMap<>();
    }
    public static Service getInstance(SessionFactory sessionFactory){
        if(instance == null){
            instance = new Service(new RepoAdmin(sessionFactory),
                    new RepoPharmaceutist(sessionFactory),
                    new RepoMedicalPersonnel(sessionFactory),
                    new RepoMedication(sessionFactory),
                    new RepoOrder(sessionFactory));
        }
        return instance;
    }

    public synchronized Iterable<Admin> getAllAdmini() {
        return adminRepo.getAll();
    }
    public synchronized Iterable<Pharmaceutist> getAllPharmaceutists() {
        return pharmaceutistRepo.getAll();
    }
    public synchronized Iterable<MedicalPersonnel> getAllPersonnels() {
        return personnelRepo.getAll();
    }
    public synchronized Iterable<Medication> getAllMedications() {
        return medicationRepo.getAll();
    }
    public synchronized Iterable<Order> getAllOrders() {
        return orderRepo.getAll();
    }

    public synchronized Admin loginAdmin(Admin admin, IObserver adminObserver) throws Exception {
        Admin admin2;
        try{
            admin2 = (Admin) adminRepo.authenticateAdmin(admin.getUsername(), admin.getPassword());
        } catch(MyException ex){
            throw ex;
        }
        if (admin2!=null){
            Map<Integer, String> key = Map.of(admin2.getId(), "admin");
            if(iObservers.get(key)!=null)
                throw new MyException("Admin already logged in.");
            iObservers.put(key, adminObserver);
        }
        else
            throw new MyException("Authentication failed.");
        return admin2;
    }
    public synchronized Pharmaceutist loginPharmaceutist(Pharmaceutist pharmaceutist, IObserver pharmaceutistObserver) throws Exception {
        Pharmaceutist pharmaceutist2;
        try{
            pharmaceutist2 = (Pharmaceutist) pharmaceutistRepo.authenticatePharmaceutist(pharmaceutist.getUsername(), pharmaceutist.getPassword());
        } catch(MyException ex){
            throw ex;
        }
        if (pharmaceutist2!=null){
            Map<Integer, String> key = Map.of(pharmaceutist2.getId(), "pharmaceutist");
            if(iObservers.get(key)!=null)
                throw new MyException("Pharmaceutist already logged in.");
            iObservers.put(key, pharmaceutistObserver);
        }
        else
            throw new MyException("Authentication failed.");
        return pharmaceutist2;
    }
    public synchronized MedicalPersonnel loginPersonnel(MedicalPersonnel personnel, IObserver personnelObserver) throws Exception {
        MedicalPersonnel personnel2;
        try{
            personnel2 = (MedicalPersonnel) personnelRepo.authenticateMedicalPersonnel(personnel.getUsername(), personnel.getPassword());
        } catch(MyException ex){
            throw ex;
        }
        if (personnel2!=null){
            Map<Integer, String> key = Map.of(personnel2.getId(), "personnel");
            if(iObservers.get(key)!=null)
                throw new MyException("Personnel already logged in.");
            iObservers.put(key, personnelObserver);
        }
        else
            throw new MyException("Authentication failed.");
        return personnel2;
    }

    public synchronized void logoutAdmin(Admin admin) throws MyException {
        Map<Integer, String> key = Map.of(admin.getId(), "admin");
        IObserver localClient= iObservers.remove(key);
        if (localClient==null)
            throw new MyException("Admin "+admin+" is not logged in.");
    }

    public synchronized void logoutPharmaceutist(Pharmaceutist pharmaceutist) throws MyException {
        Map<Integer, String> key = Map.of(pharmaceutist.getId(), "pharmaceutist");
        IObserver localClient= iObservers.remove(key);
        if (localClient==null)
            throw new MyException("Pharmaceutist "+pharmaceutist+" is not logged in.");
    }
    public synchronized void logoutPersonnel(MedicalPersonnel personnel) throws MyException {
        Map<Integer, String> key = Map.of(personnel.getId(), "personnel");
        IObserver localClient= iObservers.remove(key);
        if (localClient==null)
            throw new MyException("Personnel "+personnel+" is not logged in.");
    }

    public Admin findAdminByUsername(String username)
    {
        Iterable<Admin> admini = getAllAdmini();
        for (Admin admin : admini) {
            String username2 = admin.getUsername();
            if (Objects.equals(username, username2))
                return admin;
        }
        return null;
    }

    public Pharmaceutist findPharmaceutistByUsername(String username)
    {
        Iterable<Pharmaceutist> pharmaceutists = getAllPharmaceutists();
        for (Pharmaceutist pharmaceutist : pharmaceutists) {
            String username2 = pharmaceutist.getUsername();
            if (Objects.equals(username, username2))
                return pharmaceutist;
        }
        return null;
    }

    public MedicalPersonnel findPersonnelByUsername(String username)
    {
        Iterable<MedicalPersonnel> personnels = getAllPersonnels();
        for (MedicalPersonnel personnel : personnels) {
            String username2 = personnel.getUsername();
            if (Objects.equals(username, username2))
                return personnel;
        }
        return null;
    }

    public Pharmaceutist getPharmaceutist(Integer id){
        return pharmaceutistRepo.findElement(id);
    }

    public synchronized void addPharmaceutist(Pharmaceutist pharmaceutist) {
        pharmaceutistRepo.add(pharmaceutist);
    }

    public synchronized void removePharmaceutist(Integer id)
    {
        pharmaceutistRepo.remove(id);
    }

    public synchronized void updatePharmaceutist(Pharmaceutist pharmaceutist)
    {
        pharmaceutistRepo.update(pharmaceutist);
    }

    public MedicalPersonnel getPersonnel(Integer id){
        return personnelRepo.findElement(id);
    }

    public synchronized void addPersonnel(MedicalPersonnel personnel) {
        personnelRepo.add(personnel);
    }

    public synchronized void removePersonnel(Integer id)
    {
        personnelRepo.remove(id);
    }

    public synchronized void updatePersonnel(MedicalPersonnel personnel)
    {
        personnelRepo.update(personnel);
    }
    public Medication getMedication(Integer id){
        return medicationRepo.findElement(id);
    }
    public Order getOrder(Integer id){
        return orderRepo.findElement(id);
    }

    public synchronized void addMedication(Medication medication) {
        medicationRepo.add(medication);
        for (IObserver iObserver: iObservers.values()) {
            iObserver.showMedications();
        }
    }

    public synchronized void removeMedication(Integer id)
    {
        medicationRepo.remove(id);
        for (IObserver iObserver: iObservers.values()) {
            iObserver.showMedications();
        }
    }

    public synchronized void updateMedication(Medication medication)
    {
        medicationRepo.update(medication);
        for (IObserver iObserver: iObservers.values()) {
            iObserver.showMedications();
        }
    }

    public synchronized void updateOrder(Order order)
    {
        orderRepo.update(order);
        for (IObserver iObserver: iObservers.values()) {
            iObserver.showOrders();
        }
    }

    public synchronized void addOrder(Order order)
    {
        orderRepo.add(order);
        for (IObserver iObserver: iObservers.values()) {
            iObserver.showOrders();
        }
    }

    public synchronized void takeOrder(Order order, Pharmaceutist pharmaceutist)
    {
        order.setPharmaceutist(pharmaceutist);
        order.setStatus(Status.Preparing);
        updateOrder(order);
        for (IObserver iObserver: iObservers.values()) {
            iObserver.showOrders();
        }
    }
    public synchronized void finishOrder(Order order)
    {
        order.setStatus(Status.Finished);
        updateOrder(order);
        for (IObserver iObserver: iObservers.values()) {
            iObserver.showOrders();
        }
    }
}
