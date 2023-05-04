package proiectServerClientJava.persistence.repository.jdbc;

import proiectServerClientJava.persistence.repository.IRepoSpectacol;
import proiectServerClientJava.domain.Spectacol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RepoSpectacol implements IRepoSpectacol {
    private static final Logger logger= LogManager.getLogger();
    private final JDBCUtils jdbcUtils;

    public RepoSpectacol(Properties properties) {
        logger.info("Initializing RepoSpectacol with properties: {} ",properties);
        jdbcUtils=new JDBCUtils(properties);
    }

    @Override
    public Spectacol add(Spectacol entity) {
        logger.traceEntry("Saving spectacol {}", entity);
        String query = "INSERT INTO Spectacole(artist_name,date,place,available_seats,sold_seats) VALUES(?,?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, entity.getArtistName());
            statement.setString(2, entity.getDateString());
            statement.setString(3, entity.getPlace());
            statement.setInt(4, entity.getAvailableSeats());
            statement.setInt(5, entity.getSoldSeats());

            int result=statement.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit();
        return entity;
    }

    public void update(Spectacol spectacol, int nrSeats) {
        logger.traceEntry("Updating spectacol {}", spectacol.getId());
        String query = "UPDATE Spectacole SET available_seats=?, sold_seats=? WHERE id_spectacol = ?";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, spectacol.getAvailableSeats()-nrSeats);
            statement.setInt(2, spectacol.getSoldSeats()+nrSeats);
            statement.setLong(3,spectacol.getId());
            int result=statement.executeUpdate();
            logger.trace("Updated {} instance", result);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit();
    }

    @Override
    public Spectacol remove(Long aLong) {
        logger.traceEntry("Deleting spectacol {}", aLong);
        String query = "DELETE FROM Spectacole WHERE id_spectacol = ?";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, aLong);
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
    public Spectacol findElement(Long aLong) {
        logger.traceEntry();
        String query = "SELECT * FROM Spectacole WHERE id_spectacol=(?)";
        Spectacol spectacol = null;
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long idSpectacol = resultSet.getLong("id_spectacol");
                String artist_name = resultSet.getString("artist_name");
                String date = resultSet.getString("date");
                String place = resultSet.getString("place");
                int available = resultSet.getInt("available_seats");
                int sold = resultSet.getInt("sold_seats");
                spectacol=new Spectacol(artist_name,place,available,sold);
                spectacol.setId(idSpectacol);
                spectacol.setDate(date);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return spectacol;
    }

    @Override
    public Iterable<Spectacol> getAll() {
        logger.traceEntry();
        List<Spectacol> spectacole = new ArrayList<>();

        String query = "SELECT * from Spectacole";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Long idSpectacol = resultSet.getLong("id_spectacol");
                String artist_name = resultSet.getString("artist_name");
                String date = resultSet.getString("date");
                String place = resultSet.getString("place");
                int available = resultSet.getInt("available_seats");
                int sold = resultSet.getInt("sold_seats");

                Spectacol spectacol=new Spectacol(artist_name,place,available,sold);
                spectacol.setId(idSpectacol);
                spectacol.setDate(date);
                spectacole.add(spectacol);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return spectacole;
    }
    
}
