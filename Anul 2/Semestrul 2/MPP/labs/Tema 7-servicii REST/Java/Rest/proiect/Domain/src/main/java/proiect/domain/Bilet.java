package proiect.domain;

import java.io.Serializable;

public class Bilet extends Entity<Integer> implements Serializable {
    private String cumparatorName;
    private int idSpectacol;
    private int nrSeats;

    public Bilet(Integer id, String name, int id_spectacol, int nrSeats)
    {
        super(id);
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

    public int getIdSpectacol() {
        return idSpectacol;
    }

    public void setIdSpectacol(int id_spectacol) {
        this.idSpectacol = id_spectacol;
    }

    public int getNrSeats() {
        return nrSeats;
    }

    public void setNrSeats(int nrSeats) {
        this.nrSeats = nrSeats;
    }
}
