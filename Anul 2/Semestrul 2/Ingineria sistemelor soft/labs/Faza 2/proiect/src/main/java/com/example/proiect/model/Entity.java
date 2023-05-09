package com.example.proiect.model;

import java.io.Serializable;

public interface Entity<ID>{
    ID getId();

    void setId(ID id);

}