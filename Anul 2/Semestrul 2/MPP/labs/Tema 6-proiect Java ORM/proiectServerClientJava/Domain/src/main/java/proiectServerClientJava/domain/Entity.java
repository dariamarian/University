package proiectServerClientJava.domain;

import java.io.Serializable;
import java.util.Objects;

public abstract class Entity<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 7331115341259248461L;
    private ID id;

    public Entity() {
    }
    public Entity(ID id) {
        this.id = id;
    }

    /**
     * Method that returns the id of the entity
     * @return ID
     */
    public ID getId() {
        return id;
    }

    public void setId(ID id) {this.id=id;}
}