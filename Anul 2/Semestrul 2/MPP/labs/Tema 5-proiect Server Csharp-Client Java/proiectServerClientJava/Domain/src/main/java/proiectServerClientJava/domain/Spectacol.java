package proiectServerClientJava.domain;

import java.io.Serializable;

public class Spectacol extends Entity<Long> implements Serializable {
    private String artistName;
    private String date;
    private String time;
    private String place;
    private int availableSeats;
    private int soldSeats;

    public Spectacol(Long id,String artistName, String date, String time, String place, int availableSeats, int soldSeats) {
        super(id);
        this.artistName = artistName;
        this.date = date;
        this.time = time;
        this.place = place;
        this.availableSeats = availableSeats;
        this.soldSeats = soldSeats;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getSoldSeats() {
        return soldSeats;
    }

    public void setSoldSeats(int soldSeats) {
        this.soldSeats = soldSeats;
    }
}