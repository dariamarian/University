package com.example.proiect.model;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "MedicalPersonnel")
public class MedicalPersonnel implements com.example.proiect.model.Entity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "sectie")
    private Sectie sectie;
    public MedicalPersonnel() {
    }
    public MedicalPersonnel(String name, String username, String password, Sectie sectie) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.sectie=sectie;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Sectie getSectie() {
        return sectie;
    }

    public void setSectie(Sectie sectie) {
        this.sectie = sectie;
    }
}
