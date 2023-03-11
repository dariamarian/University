package repository;

import domain.Entity;
import exceptions.RepoException;

public interface UpdatableRepository<E extends Entity<Long>> {
    void update(E obj) throws RepoException;
}