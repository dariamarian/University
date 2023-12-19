package org.example.domain;

import java.util.Objects;

public class Concurent {
    private final String id;
    private int score;
    private final String country;

    public Concurent(String id, int score, String country) {
        this.id = id;
        this.score = score;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Concurent that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country);
    }
}
