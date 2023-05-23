package proiect.persistence.repository;

import proiect.domain.Artist;

public interface IRepoArtist extends IRepository<Artist,Integer>{
    Integer getMaxId();
    Artist update(Artist entity);
}
