package proiectServerClientJava.persistence.repository.jdbc;

import proiectServerClientJava.persistence.repository.IRepoAngajat;
import proiectServerClientJava.domain.Angajat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoAngajat implements IRepoAngajat {
    private static final Logger logger= LogManager.getLogger();
    private final JDBCUtils jdbcUtils;

    public RepoAngajat(Properties properties) {
        logger.info("Initializing RepoAngajat with properties: {} ",properties);
        jdbcUtils=new JDBCUtils(properties);
    }

    @Override
    public Angajat add(Angajat entity) {
        logger.traceEntry("Saving angajat {}", entity);
        String query = "INSERT INTO Angajati(name,username,password) VALUES(?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getPassword());

            int result=statement.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit();
        return entity;
    }

    @Override
    public Angajat remove(Integer aLong) {
        logger.traceEntry("Deleting angajat {}", aLong);
        String query = "DELETE FROM Angajati WHERE id_angajat = ?";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, aLong);
            int result=statement.executeUpdate();
            logger.trace("Deleted {} instance", result);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit();
        return findElement(aLong);
    }

    @Override
    public Angajat findElement(Integer aLong) {
        logger.traceEntry();
        String query = "SELECT * FROM Angajati WHERE id_angajat=?";
        Angajat angajat = null;
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id_angajat");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String pass = resultSet.getString("password");
                angajat=new Angajat(id, name,username,pass);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return angajat;
    }

    @Override
    public List<Angajat> getAll() {
        logger.traceEntry();
        List<Angajat> angajati = new ArrayList<>();

        String query = "SELECT * from Angajati";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id_angajat");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String pass = resultSet.getString("password");

                Angajat angajat=new Angajat(id,name,username,pass);
                angajati.add(angajat);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return angajati;
    }

    public Angajat authenticateAngajat(String username, String password) {
        logger.traceEntry();
        String query = "SELECT * FROM Angajati WHERE username=? and password=?";
        Angajat angajat = null;
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id=resultSet.getInt("id_angajat");
                String name = resultSet.getString("name");
                String usernm = resultSet.getString("username");
                String pass = resultSet.getString("password");
                angajat = new Angajat(id,name, usernm, pass);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return angajat;
    }
}
