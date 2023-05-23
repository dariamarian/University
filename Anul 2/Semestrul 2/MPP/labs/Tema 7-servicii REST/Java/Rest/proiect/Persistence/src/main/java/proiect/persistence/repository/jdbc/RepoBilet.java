package proiect.persistence.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import proiect.domain.Bilet;
import proiect.persistence.repository.IRepoBilet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoBilet implements IRepoBilet {
    private static final Logger logger= LogManager.getLogger();
    private final JDBCUtils jdbcUtils;

    public RepoBilet(Properties properties) {
        logger.info("Initializing RepoBilet with properties: {} ",properties);
        jdbcUtils=new JDBCUtils(properties);
    }

    @Override
    public Bilet add(Bilet entity) {
        logger.traceEntry("Saving bilet {}", entity);
        String query = "INSERT INTO Bilete(cumparator_name,id_spectacol,nr_seats) VALUES(?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, entity.getCumparatorName());
            statement.setInt(2, entity.getIdSpectacol());
            statement.setInt(3, entity.getNrSeats());

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
    public Bilet remove(Integer aLong) {
        logger.traceEntry("Deleting bilet {}", aLong);
        String query = "DELETE FROM Bilete WHERE id_bilet = ?";

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
    public Bilet findElement(Integer aLong) {
        logger.traceEntry();
        String query = "SELECT * FROM Bilete WHERE id_bilet=?";
        Bilet bilet = null;
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id=resultSet.getInt("id_bilet");
                String name = resultSet.getString("cumparator_name");
                int id_spectacol = resultSet.getInt("id_spectacol");
                int nr_seats = resultSet.getInt("nr_seats");
                bilet=new Bilet(id,name,id_spectacol,nr_seats);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return bilet;
    }

    @Override
    public Iterable<Bilet> getAll() {
        logger.traceEntry();
        List<Bilet> bilete = new ArrayList<>();

        String query = "SELECT * from Bilete";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                int id=resultSet.getInt("id_bilet");
                String name = resultSet.getString("cumparator_name");
                int id_spectacol = resultSet.getInt("id_spectacol");
                int nr_seats = resultSet.getInt("nr_seats");

                Bilet bilet=new Bilet(id,name,id_spectacol,nr_seats);
                bilete.add(bilet);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return bilete;
    }
}
