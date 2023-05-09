package com.example.proiect.service;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.Admin;
import com.example.proiect.model.MedicalPersonnel;
import com.example.proiect.model.Pharmaceutist;
import com.example.proiect.repository.*;
import org.hibernate.SessionFactory;

public class Service{
    private static Service instance;
    private final RepoAdmin adminRepo;
    private final RepoPharmaceutist pharmaceutistRepo;
    private final RepoMedication medicationRepo;
    private final RepoMedicalPersonnel personnelRepo;
    private final RepoOrder orderRepo;


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

    public Iterable<Admin> getAllAdmini() {
        return adminRepo.getAll();
    }
    public Iterable<Pharmaceutist> getAllPharmaceutists() {
        return pharmaceutistRepo.getAll();
    }
    public Iterable<MedicalPersonnel> getAllPersonnels() {
        return personnelRepo.getAll();
    }

    public Object loginAdmin(Admin admin) throws Exception {
        Object admin2 = null;
        try{
            admin2 = adminRepo.authenticateAdmin(admin.getUsername(), admin.getPassword());
        } catch(MyException ex){
            throw ex;
        }
        return admin2;
    }
    public Object loginPharmaceutist(Pharmaceutist pharmaceutist) throws Exception {
        Object pharmaceutist2 = null;
        try{
            pharmaceutist2 = pharmaceutistRepo.authenticatePharmaceutist(pharmaceutist.getUsername(), pharmaceutist.getPassword());
        } catch(MyException ex){
            throw ex;
        }
        return pharmaceutist2;
    }
    public Object loginPersonnel(MedicalPersonnel personnel) throws Exception {
        Object personnel2 = null;
        try{
            personnel2 = personnelRepo.authenticateMedicalPersonnel(personnel.getUsername(), personnel.getPassword());
        } catch(MyException ex){
            throw ex;
        }
        return personnel2;
    }
}
