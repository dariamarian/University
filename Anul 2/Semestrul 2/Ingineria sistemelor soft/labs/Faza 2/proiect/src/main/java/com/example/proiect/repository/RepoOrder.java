package com.example.proiect.repository;

import com.example.proiect.exceptions.RepoException;
import com.example.proiect.model.Order;
import com.example.proiect.model.Order;
import com.example.proiect.model.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoOrder implements IRepoOrder {
    private final SessionFactory sessionFactory;
    public RepoOrder(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Order entity) throws RepoException {

    }

    @Override
    public void remove(Integer integer) throws RepoException {

    }

    @Override
    public Order findElement(Integer integer) throws RepoException {
        return null;
    }

    @Override
    public Iterable<Order> getAll() {
        return null;
    }
}
