package com.example.proiect.repository;

import com.example.proiect.exceptions.RepoException;
import com.example.proiect.model.Medication;
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

public class RepoMedication implements IRepoMedication {
    private final SessionFactory sessionFactory;
    public RepoMedication(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Medication entity) throws RepoException {

    }

    @Override
    public void remove(Integer integer) throws RepoException {

    }

    @Override
    public Medication findElement(Integer integer) throws RepoException {
        return null;
    }

    @Override
    public Iterable<Medication> getAll() {
        return null;
    }
}
