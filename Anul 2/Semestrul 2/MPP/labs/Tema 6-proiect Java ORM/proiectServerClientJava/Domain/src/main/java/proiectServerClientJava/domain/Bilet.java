package proiectServerClientJava.domain;

import java.io.Serializable;

public class Bilet extends Entity<Long> implements Serializable {
    private String cumparatorName;
    private Long idSpectacol;
    private int nrSeats;

    public Bilet(Long id, String name, Long id_spectacol, int nrSeats)
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
