package repository.DataBase;

import domain.Utilizator;
import exceptions.RepoException;
import repository.UpdatableRepository;
import utils.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class UtilizatorDBRepo extends AbstractDBRepo<Utilizator> implements UpdatableRepository<Utilizator> {

    public UtilizatorDBRepo(String url, String userName, String password) {
        super(url, userName, password);
        loadData();
    }

    @Override
    protected List<Utilizator> extractEntity(ResultSet resultSet) throws SQLException {
        List<Utilizator> users=new ArrayList<>();
        while(resultSet.next()){
        Long id = resultSet.getLong("id_user");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        Utilizator user = new Utilizator(firstName, lastName,email,password);
        user.setId(id);
        users.add(user);
        }
        return users;
    }

    @Override
    protected PreparedStatement ps_getAll(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM users");
    }

    public Utilizator findUserByEmail(String email) throws RepoException{
        Predicate<Utilizator> matchEmail = user -> user.getEmail().equals(email);

        Optional<Utilizator> optionalUser=super.getAll().stream()
                .filter(matchEmail)
                .findFirst();
        if (optionalUser.isPresent())
            return optionalUser.get();
        throw new RepoException(Constants.REPO_NO_ELEMENT_FOUND);
    }

    @Override
    protected void storeEntity(Utilizator entity,Connection connection) throws SQLException,RepoException{

        boolean foundUserWithSameEmail=false;
        try{
            findUserByEmail(entity.getEmail());
            foundUserWithSameEmail=true;
        }
        catch (RepoException ex){
            String sql="INSERT INTO users (first_name,last_name,email,password,id_user) VALUES (?,?,?,?,?)";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,entity.getFirstName());
            ps.setString(2,entity.getLastName());
            ps.setString(3,entity.getEmail());
            ps.setString(4,entity.getPassword());
            ps.setLong(5,entity.getId());
            ps.executeUpdate();
        }
        if (foundUserWithSameEmail)
            throw new RepoException(Constants.REPO_ALREADY_EXISTS);

    }


    protected void updateEntity(Utilizator entity, Connection connection) throws SQLException {
        String sql="UPDATE users SET first_name=?, last_name=?, email=?, password=? WHERE id_user=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setString(1,entity.getFirstName());
        ps.setString(2,entity.getLastName());
        ps.setString(3,entity.getEmail());
        ps.setString(4,entity.getPassword());
        ps.setLong(4,entity.getId());
        ps.executeUpdate();
    }

    @Override
    protected void deleteEntity(Utilizator entity, Connection connection) throws SQLException {
        String sql="DELETE FROM users WHERE id_user=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setLong(1,entity.getId());
        ps.executeUpdate();

    }
    @Override
    public void update(Utilizator obj) throws RepoException {
        loadData();
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            Utilizator entity = findElement(obj.getId());
            updateEntity(obj, connection);
            entity.set(obj);

            connection.close();

        } catch (SQLException ex) {
            throw new RepoException(Constants.REPO_DATABASE_ERROR);
        }
    }
}