package com.example.examenmariandaria.domain;

import java.time.LocalDateTime;

public class Nevoie extends Entity<Long>{
    private String titlu;
    private String descriere;
    private LocalDateTime deadline;
    private Long omInNevoie;
    private Long omSalvator;
    private String status;

    public Nevoie(Long id_nevoie, String titlu, String descriere, LocalDateTime deadline, Long omInNevoie, Long omSalvator, String status)
    {
        super.setId(id_nevoie);
        this.titlu=titlu;
        this.descriere=descriere;
        this.deadline=deadline;
        this.omInNevoie =omInNevoie;
        this.omSalvator=omSalvator;
        this.status=status;
    }
    public void acceptNevoie() {
        this.status = "Erou gasit!";
    }
    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getOmInNevoie() {
        return omInNevoie;
    }

    public void setomInNevoie(Long omInNevoie) {
        this.omInNevoie = omInNevoie;
    }

    public Long getOmSalvator() {
            return omSalvator;
    }

    public void setomSalvator(Long omSalvator) {
        this.omSalvator = omSalvator;
    }

    public String getStatus() {

        if (status==null)
            status = "Caut erou!";
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
