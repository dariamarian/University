package com.example.proiect.repository;

import com.example.proiect.exceptions.RepoException;

public interface IRepository<T, ID> {
    T add(T entity) throws RepoException;
    T remove(ID id) throws RepoException;
    T findElement(ID id) throws RepoException;
    Iterable<T> getAll();
}

