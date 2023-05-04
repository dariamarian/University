package proiectServerClientJava.persistence.repository;

public interface IRepository<T, ID> {
    T add(T entity);
    T remove(ID id);
    T findElement(ID id);
    Iterable<T> getAll();
}

