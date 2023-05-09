package com.example.proiect.repository;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.Admin;
import com.example.proiect.model.MedicalPersonnel;
import com.example.proiect.model.Sectie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoMedicalPersonnel implements IRepoMedicalPersonnel {
    private final SessionFactory sessionFactory;
    public RepoMedicalPersonnel(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(MedicalPersonnel entity) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Integer id) {
    }

    @Override
    public MedicalPersonnel findElement(Integer id) {
        return null;
    }

    @Override
    public Iterable<MedicalPersonnel> getAll() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Iterable<MedicalPersonnel> personnels = session.createQuery("from MedicalPersonnel", MedicalPersonnel.class).list();
            session.getTransaction().commit();
            return personnels;
        }
    }
    public Object authenticateMedicalPersonnel(String username, String password) throws MyException{
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Object personnel = session.createQuery("from MedicalPersonnel where username = :username and password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if(personnel == null){
                throw new MyException("Username sau parola gresita!");
            }
            return personnel;
        }
    }
}
