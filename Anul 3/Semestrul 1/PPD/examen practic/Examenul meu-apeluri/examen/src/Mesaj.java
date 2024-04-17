public class Mesaj {
    int id_agent;
    int id_apel;
    int dificultate;
    public Mesaj(int id_agent, int id_apel, int dificultate) {
        this.id_agent = id_agent;
        this.id_apel = id_apel;
        this.dificultate = dificultate;
        String rez = toString();
        System.out.println(rez);
    }

    @Override
    public String toString() {
        return "Mesaj{" +
                "id_agent=" + id_agent +
                ", id_apel=" + id_apel +
                ", dificultate=" + dificultate +
                '}';
    }

    public int getId_agent() {
        return id_agent;
    }

    public int getId_apel() {
        return id_apel;
    }

    public int getDificultate() {
        return dificultate;
    }
}
