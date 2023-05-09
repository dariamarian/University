package com.example.proiect.repository;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.Admin;
import com.example.proiect.model.Pharmaceutist;
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

public class RepoPharmaceutist implements IRepoPharmaceutist {
    private final SessionFactory sessionFactory;
    public RepoPharmaceutist(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Pharmaceutist entity) {
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
    public Pharmaceutist findElement(Integer id) {
        return null;
    }

    @Override
    public Iterable<Pharmaceutist> getAll() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Iterable<Pharmaceutist> pharmaceutists = session.createQuery("from Pharmaceutist", Pharmaceutist.class).list();
            session.getTransaction().commit();
            return pharmaceutists;
        }
    }

    public Object authenticatePharmaceutist(String username, String password) throws MyException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Object pharmaceutist = session.createQuery("from Pharmaceutist where username = :username and password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if (pharmaceutist == null) {
                throw new MyException("Username sau parola gresita!");
            }
            return pharmaceutist;
        }
    }
}
