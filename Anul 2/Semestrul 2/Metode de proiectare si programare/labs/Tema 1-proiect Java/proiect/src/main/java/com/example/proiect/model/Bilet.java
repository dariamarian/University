package com.example.proiect.model;

public class Bilet extends Entity<Long>{
    private String cumparatorName;
    private Long idSpectacol;
    private int nrSeats;

    public Bilet(String name, Long id_spectacol, int nrSeats)
    {
        this.cumparatorName=name;
        this.idSpectacol=id_spectacol;
        this.nrSeats=nrSeats;
    }

    public String getCumparatorName() {
        return cumparatorName;
    }

    public void setCumparatorName(String cumparatorName) {
        this.cumparatorName = cumparatorName;
    }

    public Long getIdSpectacol() {
        return idSpectacol;
    }

    public void setIdSpectacol(Long id_spectacol) {
        this.idSpectacol = id_spectacol;
    }

    public int getNrSeats() {
        return nrSeats;
    }

    public void setNrSeats(int nrSeats) {
        this.nrSeats = nrSeats;
    }
}
