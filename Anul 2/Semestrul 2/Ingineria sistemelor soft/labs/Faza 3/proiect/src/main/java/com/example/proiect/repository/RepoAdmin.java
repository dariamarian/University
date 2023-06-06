package com.example.proiect.repository;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class RepoAdmin implements IRepoAdmin {
    private final SessionFactory sessionFactory;
    public RepoAdmin(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Admin admin) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(admin);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                String hql = "from Admin where id=:id";
                Admin admin = session.createQuery(hql, Admin.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(admin);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Admin admin) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.update(admin);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Admin findElement(Integer id) {
        try(Session session=sessionFactory.openSession()){
            Transaction transaction=null;
            try {
                transaction=session.beginTransaction();
                Admin admin=session.createQuery("from Admin where id=:id",Admin.class)
                        .setParameter("id",id)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                return admin;
            }
            catch (RuntimeException ex){
                if(transaction!=null)
                    transaction.rollback();
            }

        }
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
    @Override
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
