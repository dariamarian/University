package com.example.proiect.repository.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils{
    private static SessionFactory sessionFactory;

    public static SessionFactory initialize(){
        StandardServiceRegistry ssr = null;
        try{
            ssr = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            sessionFactory = new MetadataSources(ssr).buildMetadata().buildSessionFactory();
        }catch(Exception e){
            System.err.println("Exceptie: " + e.getCause());
            if(ssr != null){
                StandardServiceRegistryBuilder.destroy(ssr);
            }
        }
        return sessionFactory;
    }

    public static void close(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
    }

}