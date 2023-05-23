package proiect.domain;

import java.io.Serializable;

public class SpectacolDTO {
    private String artistName;
    private String time;
    private String place;
    private int availableSeats;

    public SpectacolDTO(Spectacol spectacol) {
        //super(id);
        this.artistName = spectacol.getArtistName();
        this.time=spectacol.getTime();
        this.place = spectacol.getPlace();
        this.availableSeats = spectacol.getAvailableSeats();
    }
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
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
}
