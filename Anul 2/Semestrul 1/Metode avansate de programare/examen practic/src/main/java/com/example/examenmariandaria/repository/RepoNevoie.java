package com.example.examenmariandaria.repository;

import com.example.examenmariandaria.domain.Nevoie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepoNevoie implements Repository<Nevoie,Long>{
    private final JDBCUtils jdbcUtils = new JDBCUtils();
    public RepoNevoie() {
    }

    @Override
    public Nevoie add(Nevoie entity) {
        String query = "INSERT INTO nevoie(nevoieid,titlu,descriere,deadline,om_in_nevoie,om_salvator,status) VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getTitlu());
            statement.setString(3, entity.getDescriere());
            statement.setString(4, entity.getDeadline().toString());
            statement.setLong(5, entity.getOmInNevoie());
            statement.setLong(6, entity.getOmSalvator());
            statement.setString(7, entity.getStatus());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Nevoie remove(Long aLong) {
        return null;
    }

    @Override
    public Nevoie findElement(Long aLong) {
        String query = "SELECT * FROM nevoie WHERE nevoieid=?";
        Nevoie nevoie = null;
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)

        ) {
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String titlu = resultSet.getString("titlu");
                String descriere  = resultSet.getString("descriere");

                LocalDateTime deadlineDateTime=LocalDateTime.parse(resultSet.getString("deadline"));


                Long omInNevoie = resultSet.getLong("om_in_nevoie");
                Long omSalvator = resultSet.getLong("om_salvator");
                String status  = resultSet.getString("status");

                nevoie=new Nevoie(aLong,titlu,descriere,deadlineDateTime,omInNevoie,omSalvator,status);
                nevoie.setId(aLong);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nevoie;
    }

    @Override
    public Iterable<Nevoie> getAll() {
        List<Nevoie> nevoi = new ArrayList<>();

        String query = "SELECT * from nevoie";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long idnevoie = resultSet.getLong("nevoieid");

                String titlu = resultSet.getString("titlu");
                String descriere  = resultSet.getString("descriere");

                LocalDateTime deadlineDateTime=LocalDateTime.parse(resultSet.getString("deadline"));

                Long omInNevoie = resultSet.getLong("om_in_nevoie");
                Long omSalvator = resultSet.getLong("om_salvator");
                String status  = resultSet.getString("status");

                Nevoie nevoie=new Nevoie(idnevoie,titlu,descriere,deadlineDateTime,omInNevoie,omSalvator,status);
                nevoi.add(nevoie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nevoi;
    }

    public void acceptNevoieInDB(Nevoie n1, Long idSalvator) throws RepoException, SQLException {
        String query = "UPDATE nevoie SET status=?, om_salvator=? WHERE nevoieid=?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        )
        {
            n1.acceptNevoie();
            statement.setString(1, n1.getStatus());
            statement.setLong(2,idSalvator);
            statement.setLong(3,n1.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
