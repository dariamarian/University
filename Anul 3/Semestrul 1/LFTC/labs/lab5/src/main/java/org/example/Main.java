package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    private static Map<String, Integer> atoms;
    private static final List<FipElement> fip = new ArrayList<>();
    private static final Ts ts = new Ts();

    private static FiniteStateMachine readFromFile(String file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            Set<String> states = Arrays.stream(bufferedReader.readLine().split(" ")).collect(Collectors.toSet());
            String initialState = bufferedReader.readLine();
            Set<String> finalStates = Arrays.stream(bufferedReader.readLine().split(" ")).collect(Collectors.toSet());
            Set<String> alphabet = Arrays.stream(bufferedReader.readLine().split(" ")).collect(Collectors.toSet());

            List<Transition> transitions = new ArrayList<>();
            String transition = bufferedReader.readLine();
            while (transition != null) {
                String[] elements = transition.split(" ");
                transitions.add(new Transition(elements[0], elements[2], elements[1]));

                transition = bufferedReader.readLine();
            }

            return new FiniteStateMachine(states, alphabet, transitions, initialState, finalStates);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        FiniteStateMachine afId = readFromFile("src/main/resources/machines/identifiers.txt");
        FiniteStateMachine afInt = readFromFile("src/main/resources/machines/integers.txt");
        FiniteStateMachine afReal = readFromFile("src/main/resources/machines/real-numbers.txt");

        File program = new File("src/main/resources/program.txt");
        Scanner reader = new Scanner(program);

        initAtomsMap();

        for (int lineNumber = 1; reader.hasNextLine(); ++lineNumber) {
            String line = reader.nextLine();
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            boolean error = false;
            boolean finished = false;
            while (!finished) {
                String element = afReal.getOffset(line);

                FipElement fipElement;

                if (!element.isEmpty()) {
                    Integer pos = ts.findAtom(element);
                    fipElement = new FipElement(element, atoms.get("CONST"), (pos == null) ? ts.addAtom(element) : pos);
                    fip.add(fipElement);
                } else {
                    element = afInt.getOffset(line);
                    if (!element.isEmpty()) {
                        Integer pos = ts.findAtom(element);
                        fipElement = new FipElement(element, atoms.get("CONST"), (pos == null) ? ts.addAtom(element) : pos);
                        fip.add(fipElement);
                    } else {
                        element = afId.getOffset(line);
                        if (!element.isEmpty()) {
                            if (atoms.get(element) != null) {
                                fipElement = new FipElement(element, atoms.get(element), -1);
                                fip.add(fipElement);
                            } else {
                                Integer pos = ts.findAtom(element);
                                if (element.length() > 7) { // daca identificatorul are lungimea >= 8
                                    System.out.println("An error occurred on line: " + lineNumber);
                                    error = true;
                                    break;
                                }
                                fipElement = new FipElement(element, atoms.get("ID"), (pos == null) ? ts.addAtom(element) : pos);
                                fip.add(fipElement);
                            }
                        } else {
                            System.out.println("An error occurred on line: " + lineNumber);
                            error = true;
                        }
                    }
                }

                if (error) {
                    break;
                }

                line = line.substring(element.length());
                line = line.trim();

                finished = line.isEmpty();
            }

            if (error) {
                break;
            }
        }

        System.out.println("\nForma interna a progamului");
        System.out.println("Atom lexical | Cod atom | Cod TS");
        for (FipElement fipElement : fip) {
            Integer pos = ts.findAtom(fipElement.getAtom());
            if (pos != null) {
                fipElement.setCodInTs(pos);
            }
        }
        AtomicInteger index = new AtomicInteger();
        String pattern = "%s %d %d\n";
        fip.forEach((fipElement -> {
            System.out.printf(pattern, fipElement.getAtom(), fipElement.getCodAtom(), fipElement.getCodInTs());
            index.addAndGet(1);
        }));

        System.out.println("\nTabela de simboluri");
        System.out.println("Simbol | Cod TS");
        System.out.println(ts.printMaxNotNull());

        reader.close();
    }

    private static void initAtomsMap() {
        atoms = new HashMap<>();
        atoms.put("ID", 0);
        atoms.put("CONST", 1);
        atoms.put("int", 2);
        atoms.put("float", 3);
        atoms.put("double", 4);
        atoms.put("struct", 5);
        atoms.put("+", 6);
        atoms.put("-", 7);
        atoms.put("*", 8);
        atoms.put("/", 9);
        atoms.put(">", 10);
        atoms.put("<>", 11);
        atoms.put("!=", 12);
        atoms.put("==", 13);
        atoms.put("<=", 14);
        atoms.put(">=", 15);
        atoms.put("=", 16);
        atoms.put("if", 17);
        atoms.put("else", 18);
        atoms.put("while", 19);
        atoms.put("for", 20);
        atoms.put("cin", 21);
        atoms.put("cout", 22);
        atoms.put("return", 23);
        atoms.put(";", 24);
        atoms.put(",", 25);
        atoms.put("(", 26);
        atoms.put(")", 27);
        atoms.put("{", 28);
        atoms.put("}", 29);
        atoms.put(">>", 30);
        atoms.put("<<", 31);
        atoms.put("main", 32);
        atoms.put("unknown", 33);
    }
}