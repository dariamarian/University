package com.example.proiect.repository;

import com.example.proiect.exceptions.RepoException;
import com.example.proiect.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RepoOrder implements IRepoOrder {
    private final SessionFactory sessionFactory;
    public RepoOrder(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Order order) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        }
    }

    @Override
    public void remove(Integer id) throws RepoException {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                String hql = "from Order where id=:id";
                Order order = session.createQuery(hql, Order.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(order);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Order order) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.update(order);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Order findElement(Integer id) {
        try(Session session=sessionFactory.openSession()){
            Transaction transaction=null;
            try {
                transaction=session.beginTransaction();
                Order order=session.createQuery("from Order where id=:id",Order.class)
                        .setParameter("id",id)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                return order;
            }
            catch (RuntimeException ex){
                if(transaction!=null)
                    transaction.rollback();
            }

        }
        return null;
    }

    @Override
    public Iterable<Order> getAll() {
        try(Session session=sessionFactory.openSession()){
            Transaction transaction=null;
            try{
                transaction=session.beginTransaction();
                List<Order> orders=session.createQuery("from Order",Order.class)
                        .list();
                transaction.commit();
                return orders;
            }
            catch (RuntimeException ex){
                if(transaction!=null)
                    transaction.rollback();
            }
        }
        return null;
    }
}
