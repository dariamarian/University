package com.example.proiect.repository;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.Pharmaceutist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class RepoPharmaceutist implements IRepoPharmaceutist {
    private final SessionFactory sessionFactory;
    public RepoPharmaceutist(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Pharmaceutist pharmaceutist) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(pharmaceutist);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                String hql = "from Pharmaceutist where id=:id";
                Pharmaceutist pharmaceutist = session.createQuery(hql, Pharmaceutist.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(pharmaceutist);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Pharmaceutist pharmaceutist) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.update(pharmaceutist);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Pharmaceutist findElement(Integer id) {
        try(Session session=sessionFactory.openSession()){
            Transaction transaction=null;
            try {
                transaction=session.beginTransaction();
                Pharmaceutist pharmaceutist=session.createQuery("from Pharmaceutist where id=:id",Pharmaceutist.class)
                        .setParameter("id",id)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                return pharmaceutist;
            }
            catch (RuntimeException ex){
                if(transaction!=null)
                    transaction.rollback();
            }

        }
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

    @Override
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
