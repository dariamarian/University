package com.example.examenmariandaria.repository;

public interface Repository<T, ID> {

    T add(T entity) throws RepoException;
    T remove(ID id) throws RepoException;
    T findElement(ID id) throws RepoException;
    Iterable<T> getAll();
}

