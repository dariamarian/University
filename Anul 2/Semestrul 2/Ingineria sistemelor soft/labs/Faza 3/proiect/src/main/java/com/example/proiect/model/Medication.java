package com.example.proiect.model;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "Medications")
public class Medication implements com.example.proiect.model.Entity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "details")
    private String details;
    @Column(name = "stock")
    private Integer stock;
    public Medication(String name, String details, Integer stock) {
        this.name = name;
        this.details=details;
        this.stock=stock;
    }
    public Medication() {

    }
    @Override
    public Integer getId(){
        return id;
    }

    @Override
    public void setId(Integer id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return name;
    }

}
