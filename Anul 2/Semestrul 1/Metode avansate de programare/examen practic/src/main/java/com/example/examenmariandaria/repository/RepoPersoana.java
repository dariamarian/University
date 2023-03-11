package com.example.examenmariandaria.repository;

import com.example.examenmariandaria.domain.Orase;
import com.example.examenmariandaria.domain.Persoana;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepoPersoana implements Repository<Persoana,Long>{
    private final JDBCUtils jdbcUtils = new JDBCUtils();

    public RepoPersoana() {
    }

    @Override
    public Persoana add(Persoana entity) {
        String query = "INSERT INTO persoana(persoanaid,nume,prenume,username,parola,oras,strada,numarstrada,telefon) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getNume());
            statement.setString(3, entity.getPrenume());
            statement.setString(4, entity.getUsername());
            statement.setString(5, entity.getParola());
            statement.setString(6, entity.getOras().toString());
            statement.setString(7, entity.getStrada());
            statement.setString(8, entity.getNumarStrada());
            statement.setString(9, entity.getTelefon());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Persoana remove(Long aLong) {
        return null;
    }

    @Override
    public Persoana findElement(Long aLong) {
        String query = "SELECT * FROM persoana WHERE persoanaid=?";
        Persoana persoana = null;
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                Orase oras= Orase.valueOf(resultSet.getString("oras"));
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numarstrada");
                String telefon = resultSet.getString("telefon");
                persoana=new Persoana(aLong,nume,prenume,username,parola,oras,strada,numarStrada,telefon);
                persoana.setId(aLong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persoana;
    }

    @Override
    public Iterable<Persoana> getAll() {
        List<Persoana> persoane = new ArrayList<>();

        String query = "SELECT * from persoana";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long idpersoana = resultSet.getLong("persoanaid");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                Orase oras= Orase.valueOf(resultSet.getString("oras"));
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numarstrada");
                String telefon = resultSet.getString("telefon");
                Persoana persoana=new Persoana(idpersoana,nume,prenume,username,parola,oras,strada,numarStrada,telefon);

                persoane.add(persoana);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persoane;
    }
}
