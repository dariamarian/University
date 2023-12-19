public class Concurent {

    private String name;

    private double score;

    public Concurent(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name + " " + String.format("%.2f", score);
    }

    @Override
    public boolean equals(Object obj) {
        Concurent other = (Concurent) obj;
        return name.equals(other.name);
    }
}
