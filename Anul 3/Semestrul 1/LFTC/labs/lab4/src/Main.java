import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
Structura fisierului de intrare (EBNF):
   fisier = 'Stari: ', '\n', lista_stari, '\n', '\n', 'Alfabet: ', '\n', lista_simboluri, '\n', '\n',
   'Tranzitii: ', '\n', lista_tranzitii, '\n', '\n', 'Stare initiala: ', '\n', stare_initiala, '\n', '\n',
   'Stari finale: ', '\n', lista_stari_finale

lista_stari = stare, {'\n', stare}
stare = 'q', CONST
CONST = '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'

lista_simboluri = simbol, {'\n', simbol}
simbol = '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' | 'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g' | 'h' | 'i' | 'j' | 'k' | 'l'
        | 'm' | 'n' | 'o' | 'p' | 'q' | 'r' | 's' | 't' | 'u' | 'v' | 'w' | 'x' | 'y' | 'z'

lista_tranzitii = tranzitie, {'\n', tranzitie}
tranzitie = stare, ' ', simbol, ' ', stare

stare_initiala = stare

lista_stari_finale = stare, {' ', stare}
*/

public class Main {

    private static Set<String> multimeStari = new HashSet<>();
    private static Set<String> alfabetIntrare = new HashSet<>();
    private static Map<String, Map<String, Set<String>>> tranzitii = new HashMap<>();
    private static String stareInitiala = "";
    private static Set<String> stariFinale = new HashSet<>();
    private static AF af;

    private static void findLongestPrefix(String sequence) {
        if (af.isNondeterministic()) {
            System.out.println("Automatul finit nu este determinist.");
            return;
        }
        String prefix = af.findLongestPrefix(sequence);
        if (Objects.equals(prefix, "") && stariFinale.contains(stareInitiala)) {
            prefix = "epsilon";
        }
        else if (Objects.equals(prefix, "") && !stariFinale.contains(stareInitiala))
            prefix = "null";
        System.out.println("Cel mai lung prefix acceptat: " + prefix);
    }

    private static void checkSequence(String sequence) {
        if (af.isNondeterministic()) {
            System.out.println("Automatul finit nu este determinist.");
            return;
        }
        if (af.verifySequence(sequence))
            System.out.println("Secventa e acceptata de automat.");
        else
            System.out.println("Secventa nu e acceptata de automat.");
    }

    private static void printElementsOfAF() {
        Scanner scanner = new Scanner(System.in);
        String cmd = "";
        while (true) {
            System.out.println("1. Multimea starilor.\n2. Alfabetul de intrare.\n3. Tranzitiile.\n4. Starile finale.\n0. Exit.\nComanda:");
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1" -> {
                    af.printMultimeStari();
                }
                case "2" -> {
                    af.printAlfabetIntrare();
                }
                case "3" -> {
                    af.printTranzitii();
                }
                case "4" -> {
                    af.printStariFinale();
                }
                case "0" -> {
                    return;
                }
                default -> {
                    System.out.println("Comanda invalida!");
                }
            }
        }
    }

    private static void menu(String cmd) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------------");
        System.out.println("|   MENIU   |");
        System.out.println("-------------");
        while (true) {
            System.out.println("1. Afisarea elementelor automatului finit.\n2. Verifica daca o secventa e acceptata de automatul finit.\n3. Determina cel mai lung prefix dintr-o secventa data care este acceptata de automat.\n0. Exit.");
            System.out.println("Comanda:");
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1" -> {
                    printElementsOfAF();
                }
                case "2" -> {
                    System.out.println("Introduceti secventa:");
                    String sequence = scanner.nextLine();
                    checkSequence(sequence);
                }
                case "3" -> {
                    System.out.println("Introduceti secventa:");
                    String sequence = scanner.nextLine();
                    findLongestPrefix(sequence);
                }
                case "0" -> {
                    return;
                }
                default -> {
                    System.out.println("Comanda invalida!");
                }
            }
        }
    }

    private static void start(String cmd) throws Exception {
        System.out.println("Alegeti metoda de citire:\n\t1. Din fisier.\n\t2. De la tastatura.\n\t0. Exit.\nComanda:");
        Scanner scanner = new Scanner(System.in);
        cmd = scanner.nextLine();
        switch (cmd) {
            case "1" -> {
                readFromFile();
            }
            case "2" -> {
                readFromKeyboard();
            }
            case "0" -> {
            }
            default -> {
                System.out.println("Comanda invalida!");
                start(cmd);
            }
        }
    }

    private static void readFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\daria\\Github\\University\\Anul 3\\Semestrul 1\\LFTC\\labs\\lab4\\src\\input5.txt"))) {
            String line;
            String section = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                if (line.equals("Stari:")) {
                    section = "Stari";
                } else if (line.equals("Alfabet:")) {
                    section = "Alfabet";
                } else if (line.equals("Tranzitii:")) {
                    section = "Tranzitii";
                } else if (line.equals("Stare initiala:")) {
                    section = "Stare initiala";
                } else if (line.equals("Stari finale:")) {
                    section = "Stari finale";
                } else {
                    switch (section) {
                        case "Stari":
                            multimeStari.add(line);
                            break;
                        case "Alfabet":
                            alfabetIntrare.add(line);
                            break;
                        case "Tranzitii":
                            String[] data = line.split(" ");
                            if (data.length != 3) {
                                System.out.println("Linie de tranzitie invalida in fisier: " + line);
                                return;
                            }
                            if (!multimeStari.contains(data[0])) {
                                System.out.println("Stare curenta invalida: " + data[0]);
                                return;
                            }
                            if (!alfabetIntrare.contains(data[1])) {
                                System.out.println("Simbol de intrare invalid: " + data[2]);
                                return;
                            }
                            if (!multimeStari.contains(data[2])) {
                                System.out.println("Stare urmatoare invalida: " + data[1]);
                                return;
                            }
                            // Update the tranzitii map
                            tranzitii
                                    .computeIfAbsent(data[0], k -> new HashMap<>())
                                    .computeIfAbsent(data[1], k -> new HashSet<>())
                                    .add(data[2]);
                            break;
                        case "Stare initiala":
                            stareInitiala = line;
                            break;
                        case "Stari finale":
                            String[] finalStatesInput = line.split(" ");
                            for (String finalState : finalStatesInput) {
                                if (!multimeStari.contains(finalState)) {
                                    System.out.println("Una dintre starile finale nu se afla printre starile existente: " + finalState);
                                    return;
                                }
                            }
                            Collections.addAll(stariFinale, finalStatesInput);
                            break;
                        default:
                            System.out.println("Eticheta de sectiune invalida in fisier: " + section);
                            return;
                    }
                }
            }

            af = new AF(multimeStari, alfabetIntrare, tranzitii, stareInitiala, stariFinale);
            System.out.println("Datele au fost citite cu succes!");
        } catch (IOException e) {
            System.out.println("Eroare la citirea din fisier:");
            e.printStackTrace();
        }
    }

    private static void readFromKeyboard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti numarul de stari:");
        int numStates = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numStates; i++) {
            System.out.println("Starea " + (i + 1) + ":");
            multimeStari.add(scanner.nextLine());
        }

        System.out.println("Introduceti numarul de simboluri din alfabet:");
        int numAlphabetSymbols = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numAlphabetSymbols; i++) {
            System.out.println("Simbolul din alfabet " + (i + 1) + ":");
            alfabetIntrare.add(scanner.nextLine());
        }

        while (true) {
            System.out.println("Introduceti tranzitiile (format: stareCurenta simbolIntrare stareUrmatoare):");
            System.out.println("Pentru a opri introducerea, lasati o linie goala.");
            String tranzitie = scanner.nextLine();
            if (tranzitie.isEmpty()) {
                break;
            }
            String[] data = tranzitie.split(" ");
            if (data.length != 3) {
                System.out.println("Linie de tranzitie invalida: " + tranzitie);
                return;
            }
            if (!multimeStari.contains(data[0])) {
                System.out.println("Starea curenta invalida: " + data[0]);
                return;
            }
            if (!alfabetIntrare.contains(data[1])) {
                System.out.println("Simbolul de intrare invalid: " + data[1]);
                return;
            }
            if (!multimeStari.contains(data[2])) {
                System.out.println("Stare urmatoare invalida: " + data[2]);
                return;
            }

            // Update the tranzitii map
            tranzitii
                    .computeIfAbsent(data[0], k -> new HashMap<>())
                    .computeIfAbsent(data[1], k -> new HashSet<>())
                    .add(data[2]);
        }

        System.out.println("Introduceti starea initiala:");
        stareInitiala = scanner.nextLine();
        if (!multimeStari.contains(stareInitiala)) {
            System.out.println("Starea initiala nu se afla printre starile existente!");
            return;
        }

        System.out.println("Introduceti starile finale (separate prin spatiu):");
        String[] finalStatesInput = scanner.nextLine().split(" ");
        for (String finalState : finalStatesInput) {
            if (!multimeStari.contains(finalState)) {
                System.out.println("Una dintre starile finale nu se afla printre starile existente!");
                return;
            }
        }
        Collections.addAll(stariFinale, finalStatesInput);

        af = new AF(multimeStari, alfabetIntrare, tranzitii, stareInitiala, stariFinale);
        System.out.println("Datele au fost citite cu succes!");
    }


    public static void main(String[] args) {
        String cmd = "";
        try {
            start(cmd);
            menu(cmd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
