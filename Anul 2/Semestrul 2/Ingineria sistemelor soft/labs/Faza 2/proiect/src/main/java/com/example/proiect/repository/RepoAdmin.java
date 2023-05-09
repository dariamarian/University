package com.example.proiect.repository;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoAdmin implements IRepoAdmin {
    private final SessionFactory sessionFactory;
    public RepoAdmin(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Admin entity) {
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
    public Admin findElement(Integer id) {
        return null;
    }

    @Override
    public Iterable<Admin> getAll() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Iterable<Admin> admins = session.createQuery("from Admin", Admin.class).list();
            session.getTransaction().commit();
            return admins;
        }
    }
    public Object authenticateAdmin(String username, String password) throws MyException{
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Object admin = session.createQuery("from Admin where username = :username and password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if (admin == null) {
                throw new MyException("Username sau parola gresita!");
            }
            return admin;
        }
    }
}
