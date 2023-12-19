import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MyLinkedList {

    List<Concurent> concurents = new LinkedList<>();

    List<Concurent> eliminatedConcurents = new LinkedList<>();

    public synchronized List<Concurent> getConcurents() {
        return concurents;
    }

    private void removeConcurent(Concurent concurent) {
        concurents.removeIf(existingConcurent -> existingConcurent.equals(concurent));
    }

    private void updateExistingConcurent(Concurent concurent, int index) {
        // daca punctaj_n este pozitiv se aduna punctajul punctaj_n la punctajul existent
        Concurent existingConcurent = concurents.get(index);
        existingConcurent.setScore(existingConcurent.getScore() + concurent.getScore());
        concurents.sort(
                Comparator.comparingDouble(Concurent::getScore).reversed().thenComparing(Concurent::getName)
        );
    }

    private void addConcurent(Concurent concurent) {
        concurents.add(concurent);
        concurents.sort(
                Comparator.comparingDouble(Concurent::getScore).reversed().thenComparing(Concurent::getName)
        );
    }

    public synchronized void insert(Concurent concurent) {
        if (eliminatedConcurents.contains(concurent)) {
            return;
        }

        if (concurent.getScore() < 0) {
            // daca punctaj_n = -1 atunci se sterge nodul gasit din lista
            eliminatedConcurents.add(concurent);
            removeConcurent(concurent);
            return;
        }

        int index = concurents.indexOf(concurent);
        if (index == -1) {
            // daca nu exista atunci se adauga un nou nod cu valoarea (ID_n, punctaj_n)
            addConcurent(concurent);
        } else {
            updateExistingConcurent(concurent, index);
        }
    }
}
