package com.example.proiect.repository;

import com.example.proiect.model.Medication;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RepoMedication implements IRepoMedication {
    private final SessionFactory sessionFactory;
    public RepoMedication(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Medication medication) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(medication);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                String hql = "from Medication where id=:id";
                Medication medication = session.createQuery(hql, Medication.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(medication);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }
    @Override
    public void update(Medication medication) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.update(medication);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }
    @Override
    public Medication findElement(Integer id) {
        try(Session session=sessionFactory.openSession()){
            Transaction transaction=null;
            try {
                transaction=session.beginTransaction();
                Medication medication=session.createQuery("from Medication where id=:id",Medication.class)
                        .setParameter("id",id)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                return medication;
            }
            catch (RuntimeException ex){
                if(transaction!=null)
                    transaction.rollback();
            }

        }
        return null;
    }

    @Override
    public Iterable<Medication> getAll() {
        try(Session session=sessionFactory.openSession()){
            Transaction transaction=null;
            try{
                transaction=session.beginTransaction();
                List<Medication> medications=session.createQuery("from Medication",Medication.class)
                        .list();
                transaction.commit();
                return medications;
            }
            catch (RuntimeException ex){
                if(transaction!=null)
                    transaction.rollback();
            }
        }
        return null;
    }
}
