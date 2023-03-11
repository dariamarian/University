package repository;

import domain.Entity;
import exceptions.RepoException;

public interface Repository<T extends Entity<Long>> {
    /**
     * adauga o entitate
     *
     * @param  - entitatea care trebuie adaugata
     * @throws RepoException daca entitatea exista deja
     */
    void add(T obj) throws RepoException;

    /**
     * sterge o entitate
     *
     * @param id - id-ul entitatii care trebuie stearsa
     * @return entitatea care a fost stearsa
     * @throws RepoException daca entitatea nu exista
     */
    T remove(Long id) throws RepoException;

    T remove(T entity) throws Exception;
    void loadData();
    void storeData();

    /**
     * gaseste o entitate dupa id
     *
     * @param id - id-ul entitatii care trebuie gasita
     * @return entitatea cu acel id
     * @throws RepoException daca entitatea nu exista
     */
    T findElement(long id) throws RepoException;

    /**
     * returneaza toate entitatile
     *
     * @return lista cu entitati
     */
    Iterable<T> getAll();

    int size();
}

