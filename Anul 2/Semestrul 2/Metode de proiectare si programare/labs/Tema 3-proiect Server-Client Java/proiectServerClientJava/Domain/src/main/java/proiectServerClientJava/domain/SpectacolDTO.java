package proiectServerClientJava.domain;

import java.time.LocalTime;

public class SpectacolDTO{
    private String artistName;
    private LocalTime time;
    private String place;
    private int availableSeats;

    public SpectacolDTO(Spectacol spectacol) {
        this.artistName = spectacol.getArtistName();
        this.time=spectacol.getDate().toLocalTime();
        this.place = spectacol.getPlace();
        this.availableSeats = spectacol.getAvailableSeats();
    }
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
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
