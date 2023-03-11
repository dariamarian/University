package repository.DataBase;

import domain.Entity;
import exceptions.RepoException;
import repository.Repository;
import utils.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDBRepo<T extends Entity<Long>> implements Repository<T> {
    private List<T> entities;
    protected String url;
    protected String userName;
    protected String password;
    private String sqlCommand;

    public AbstractDBRepo(String url, String userName, String password) {
        entities = new LinkedList<>();
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    protected abstract List<T> extractEntity(ResultSet set) throws SQLException;

    protected abstract PreparedStatement ps_getAll(Connection connection) throws SQLException;

    public void loadData() {
        entities.clear();
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = ps_getAll(connection);
             ResultSet resultSet = ps.executeQuery();

             List<T> list = extractEntity(resultSet);
             connection.close();
             list.forEach(entity -> entities.add(entity));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeData() {
    }
    protected abstract void storeEntity(T entity, Connection connection) throws SQLException, RepoException;
    protected abstract void deleteEntity(T entity, Connection connection) throws SQLException;

    @Override
    public void add(T obj) throws RepoException {
        loadData();
        Optional<T> optionalE = entities.stream().filter(entity -> entity.equals(obj)).findFirst();
        if (optionalE.isEmpty()) {
            try {
                Connection connection = DriverManager.getConnection(url, userName, password);
                storeEntity(obj, connection);
                entities.add(obj);
                connection.close();

            } catch (SQLException ex) {
                ex.getStackTrace();
                throw new RepoException(Constants.REPO_DATABASE_ERROR);
            }

        } else throw new RepoException(Constants.REPO_ALREADY_EXISTS);
    }

    private T find(Long id) {
        Optional<T> optionalEntities = entities.stream()
                .filter(entity -> entity.getId() == id)
                .findFirst();
        if (optionalEntities.isPresent())
            return optionalEntities.get();
        else return null;
    }

    @Override
    public T remove(Long id) throws RepoException {
        loadData();
        T entity = find(id);
        if (entity != null) {
            try {
                Connection connection = DriverManager.getConnection(url, userName, password);
                deleteEntity(entity, connection);
                entities.remove(entity);
                connection.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RepoException(Constants.REPO_DATABASE_ERROR);
            }
            return entity;
        } else throw new RepoException(Constants.REPO_REMOVE_ENTITY);
    }
    @Override
    public T remove(T entity) throws Exception {

        loadData();
        Optional<T> optionalE = entities.stream().filter(_entity -> _entity.equals(entity)).findFirst();
        if (!optionalE.isPresent())
            throw new RepoException(Constants.REPO_REMOVE_ENTITY);

        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            deleteEntity(optionalE.get(), connection);
            entities.remove(optionalE.get());
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepoException(Constants.REPO_DATABASE_ERROR);
        }

        return optionalE.get();

    }

    @Override
    public T findElement(long id) throws RepoException {
        loadData();
        T entity = find(id);
        if (entity == null)
            throw new RepoException(Constants.REPO_NO_ELEMENT_FOUND);
        return entity;
    }

    @Override
    public List<T> getAll() {
        loadData();
        return entities;
    }

    @Override
    public int size() {
        return entities.size();
    }
}
