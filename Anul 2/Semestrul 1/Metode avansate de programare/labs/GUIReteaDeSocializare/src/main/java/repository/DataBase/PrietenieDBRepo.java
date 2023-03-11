package repository.DataBase;

import domain.Prietenie;
import domain.Utilizator;
import exceptions.RepoException;
import utils.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PrietenieDBRepo extends AbstractDBRepo<Prietenie> {
    private UtilizatorDBRepo repoUser;
    public PrietenieDBRepo(String url, String userName, String password, UtilizatorDBRepo repoUser) {
        super(url, userName, password);
        this.repoUser=repoUser;
        loadData();
    }

    public void acceptFriendshipInDB(Prietenie f1) throws RepoException, SQLException {
        try {
            Connection connection = DriverManager.getConnection(super.url, super.userName, super.password);
            String sql = "UPDATE friendships SET status=?,friends_from=? WHERE id_friendship=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            f1.acceptFriend();
            ps.setString(1, f1.getStatus());
            ps.setString(2, f1.getFriendsFrom());
            ps.setLong(3, f1.getId());
            ps.executeUpdate();
            connection.close();
            super.loadData();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RepoException(Constants.REPO_DATABASE_ERROR);
        }
    }
    public void removeAllFriendshipsByUser(Utilizator user) {
        List<Prietenie> friendships =
                StreamSupport.stream(super.getAll().spliterator(), false)
                        .collect(Collectors.toList());
        try {
            Connection connection = DriverManager.getConnection(super.url, super.userName, super.password);

            for (int i = 0; i < friendships.size(); i++) {
                if (friendships.get(i).getIdUser1() == user.getId() ||
                        friendships.get(i).getIdUser2() == user.getId()) {
                    remove(friendships.get(i).getId());
                    friendships.remove(i);
                    i--;
                }
            }
            connection.close();

        } catch (SQLException | RepoException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    protected List<Prietenie> extractEntity(ResultSet resultSet) throws SQLException {
        List <Prietenie> prietenii=new ArrayList<>();
        while(resultSet.next()) {
            Long idFriendship = resultSet.getLong("id_friendship");
            Long idUser1 = resultSet.getLong("id_user1");
            Long idUser2 = resultSet.getLong("id_user2");
            String friendsFrom = resultSet.getString("friends_from");
            String status = resultSet.getString("status");
            try {
                Prietenie friendship = new Prietenie(idUser1, idUser2);
                friendship.setId(idFriendship);
                friendship.setFriendsFrom(friendsFrom);
                friendship.setStatus(status);
                prietenii.add(friendship);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return prietenii;
    }

    @Override
    protected PreparedStatement ps_getAll(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM friendships");
    }

    @Override
    protected void storeEntity(Prietenie entity, Connection connection) throws SQLException {
        String sql = "INSERT INTO friendships (id_friendship,id_user1,id_user2,friends_from,status) VALUES (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, entity.getId());
        ps.setLong(2, entity.getIdUser1());
        ps.setLong(3, entity.getIdUser2());
        ps.setString(4, entity.getFriendsFrom());
        ps.setString(5, entity.getStatus());
        ps.executeUpdate();
    }
    @Override
    protected void deleteEntity(Prietenie entity, Connection connection) throws SQLException {
        String sql = "DELETE FROM friendships WHERE id_friendship=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, entity.getId());
        ps.executeUpdate();
    }
    public Vector<Prietenie> findUserFriends(Long id) {
        Iterable<Prietenie> friendships = super.getAll();
        Vector<Prietenie> result = new Vector<>();

        for (Prietenie friendship : friendships) {
            if (Objects.equals(friendship.getIdUser1(), id) || Objects.equals(friendship.getIdUser2(), id)) {
                result.add(friendship);
            }
        }

        return result;
    }
}