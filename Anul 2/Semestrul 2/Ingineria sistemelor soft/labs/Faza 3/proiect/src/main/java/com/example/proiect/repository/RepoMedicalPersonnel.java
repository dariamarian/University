package com.example.proiect.repository;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.MedicalPersonnel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class RepoMedicalPersonnel implements IRepoMedicalPersonnel {
    private final SessionFactory sessionFactory;
    public RepoMedicalPersonnel(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(MedicalPersonnel personnel) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(personnel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                String hql = "from MedicalPersonnel where id=:id";
                MedicalPersonnel personnel = session.createQuery(hql, MedicalPersonnel.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(personnel);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(MedicalPersonnel personnel) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.update(personnel);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public MedicalPersonnel findElement(Integer id) {
        try(Session session=sessionFactory.openSession()){
            Transaction transaction=null;
            try {
                transaction=session.beginTransaction();
                MedicalPersonnel personnel=session.createQuery("from MedicalPersonnel where id=:id",MedicalPersonnel.class)
                        .setParameter("id",id)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                return personnel;
            }
            catch (RuntimeException ex){
                if(transaction!=null)
                    transaction.rollback();
            }

        }
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
    @Override
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
