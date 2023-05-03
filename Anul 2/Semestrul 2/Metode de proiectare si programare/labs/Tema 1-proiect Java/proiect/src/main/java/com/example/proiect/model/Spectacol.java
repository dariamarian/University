package com.example.proiect.model;

import com.example.proiect.utils.Constants;

import java.time.LocalDateTime;

public class Spectacol extends Entity<Long> {
    private String artistName;
    private LocalDateTime date;
    private String place;
    private int availableSeats;
    private int soldSeats;

    public Spectacol(String artistName,String place, int availableSeats, int soldSeats) {
        this.artistName = artistName;
        //this.date = date;
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
    public String getDateString(){
        return date.format(Constants.FORMATTER_MESSAGE);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = LocalDateTime.parse(date,Constants.FORMATTER_MESSAGE);
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