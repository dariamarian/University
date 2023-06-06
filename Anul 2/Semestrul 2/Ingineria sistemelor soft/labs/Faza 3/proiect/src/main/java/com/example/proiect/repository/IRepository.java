package com.example.proiect.repository;

import com.example.proiect.exceptions.RepoException;

public interface IRepository<T, ID> {
    void add(T entity) throws RepoException;
    void remove(ID id) throws RepoException;
    void update(T entity) throws RepoException;
    T findElement(ID id) throws RepoException;
    Iterable<T> getAll();
}

