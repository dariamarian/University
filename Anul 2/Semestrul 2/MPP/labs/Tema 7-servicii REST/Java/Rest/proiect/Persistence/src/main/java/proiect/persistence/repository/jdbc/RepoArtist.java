package proiect.persistence.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import proiect.domain.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Repository
public class RepoArtist implements proiect.persistence.repository.IRepoArtist {
    private static final Logger logger= LogManager.getLogger();
    private final JDBCUtils jdbcUtils;

    public RepoArtist(Properties properties) {
        logger.info("Initializing RepoAngajat with properties: {} ",properties);
        jdbcUtils=new JDBCUtils(properties);
    }

    @Override
    public Artist add(Artist entity) {
        logger.traceEntry("Saving artist {}", entity);
        String query = "INSERT INTO Artisti(first_name,last_name) VALUES(?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());

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
    public Artist remove(Integer aLong) {
        logger.traceEntry("Deleting artist {}", aLong);
        String query = "DELETE FROM Artisti WHERE id_artist = ?";

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
    public Artist findElement(Integer aLong) {
        logger.traceEntry();
        String query = "SELECT * FROM Artisti WHERE id_artist=?";
        Artist artist = null;
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_artist");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                artist=new Artist(id,firstName, lastName);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return artist;
    }

    @Override
    public List<Artist> getAll() {
        logger.traceEntry();
        List<Artist> artisti = new ArrayList<>();

        String query = "SELECT * from Artisti";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id_artist");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                Artist artist=new Artist(id,firstName,lastName);
                artisti.add(artist);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return artisti;
    }
    @Override
    public Artist update(Artist artist) {
        logger.traceEntry("updating artist");
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement ps = connection.prepareStatement("" +
                "update Artisti set first_name = ?, last_name = ?" +
                " where id_artist = ?")) {
            ps.setString(1, artist.getFirstName());
            ps.setString(2, artist.getLastName());
            ps.setInt(3, artist.getId());
            int result = ps.executeUpdate();
            logger.trace("updated {} instance", result);
        } catch (SQLException exception) {
            logger.error("ERROR for update in ArtistDBRepository: " + exception);
        }
        logger.traceExit("updated successfully");
        return findElement(artist.getId());
    }
    @Override
    public Integer getMaxId() {
        logger.traceEntry("getting max id");
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement ps = connection.prepareStatement("select max(id_artist) as max_id from Artisti")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int maxId = resultSet.getInt("max_id");
                    logger.traceExit(maxId);
                    return maxId;
                }
            }
        } catch (SQLException exception) {
            logger.error("ERROR for getMaxId in ArtistDBRepository: " + exception);
            return null;
        }
        return null;
    }
}
