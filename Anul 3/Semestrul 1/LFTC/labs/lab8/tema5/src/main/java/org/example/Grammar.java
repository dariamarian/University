package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Grammar {
    private char startSymbol;
    private Map<Character, List<Pair<String, String>>> rules = new HashMap<>();

    public void addRule(char nonTerminal, String production) {
        // forms indicativ_rp as being nonTerminal + number of productions for nonTerminal
        String indicativ_rp = nonTerminal + String.valueOf(rules.getOrDefault(nonTerminal, Collections.emptyList()).size() + 1);
        rules.computeIfAbsent(nonTerminal, k -> new ArrayList<>()).add(new Pair<>(production, indicativ_rp));
    }

    public void setStartSymbol(char startSymbol) {
        this.startSymbol = startSymbol;
    }

    public boolean parse(String input) {
        Stack<String> alphaStack = new Stack<>();
        Stack<Character> betaStack = new Stack<>();
        int i = 1;
        char s = 'q';
        char t = 't';
        char e = 'e';
        char q = 'q';
        char r = 'r';
        char S = startSymbol;

        // Add the start symbol to the beta stack
        betaStack.push(S);

        while ((s != t) && (s != e)) {
            if (s == 'q') {
                if (betaStack.isEmpty()) {
                    if (i == input.length() + 1) {
                        s = t;
                    } else {
                        s = r;
                    }
                } else {
                    if (Character.isUpperCase(betaStack.peek())) {
                        char A = betaStack.pop();
                        // Get the indicativ_rp for the corresponding production
                        String indicativ_rp = rules.get(A).get(0).second;
                        // Add indicativ_rp to the alpha stack
                        alphaStack.push(indicativ_rp);
                        for (int j = rules.get(A).get(0).first.length() - 1; j >= 0; j--) {
                            betaStack.push(rules.get(A).get(0).first.charAt(j));
                        }
                    } else if (i <= input.length() && betaStack.peek() == input.charAt(i - 1)) {
                        i++;
                        char betaTop = betaStack.pop();
                        alphaStack.push(String.valueOf(betaTop));
                    } else {
                        s = r;
                    }
                }
            } else {
                if (s == 'r') {
                    if (!alphaStack.isEmpty() && !Character.isUpperCase(alphaStack.peek().charAt(0))) {
                        i--;
                        // Pop indicativ_rp from the alpha stack
                        String nonterminal = alphaStack.pop();
                        // Push the corresponding production into the beta stack
                        betaStack.push(nonterminal.charAt(0));
                    } else if (!alphaStack.isEmpty()){
                        // If there is a production starting with indicativ_rp
                        // Pop indicativ_rp from the alpha stack
                        String indicativ_rp = alphaStack.pop();
                        String number = indicativ_rp.substring(1);
                        int index = Integer.parseInt(number);

                        String production = rules.get(indicativ_rp.charAt(0)).get(index - 1).first;

                        List<Character> symbols = new ArrayList<>();
                        int pos = 0;
                        boolean exists = true;
                        while (!betaStack.isEmpty() && pos < production.length()) {
                            if (betaStack.peek() == production.charAt(pos)) {
                                symbols.add(betaStack.pop());
                                pos++;
                            } else {
                                for (int j = symbols.size() - 1; j >= 0; j--) {
                                    betaStack.push(symbols.get(j));
                                }
                                exists = false;
                                break;
                            }
                        }

                        if (exists && index < rules.get(indicativ_rp.charAt(0)).size()) {
                            s = q;
                            alphaStack.pop();
                            String indicativ_urm = rules.get(indicativ_rp.charAt(0)).get(index).second;
                            alphaStack.push(indicativ_urm);
                            for (int k = rules.get(indicativ_rp.charAt(0)).get(index).first.length() - 1; k >= 0; k--) {
                                betaStack.push(rules.get(indicativ_rp.charAt(0)).get(index).first.charAt(k));
                            }
                        } else {
                            if (i == 1 && indicativ_rp.charAt(0) == S) {
                                s = e;
                            } else {
                                alphaStack.pop();
                                betaStack.push(indicativ_rp.charAt(0));
                            }
                        }
                    }
                    else {
                        s = e;
                    }
                }
            }
        }
        if (s == t)
            System.out.println(constructie_sir_prod(alphaStack));
        return s == t;
    }

    private String constructie_sir_prod(Stack<String> alphaStack) {
        StringBuilder sir_prod = new StringBuilder();

        while (!alphaStack.isEmpty()) {
            String alphaTop = alphaStack.pop();
            if (alphaTop.length() > 1 && Character.isUpperCase(alphaTop.charAt(0))) {
                sir_prod.insert(0, alphaTop + " ");
            }
        }

        return sir_prod.toString();
    }

    public static void main(String[] args) {
        Grammar grammar = new Grammar();

        boolean isStartSymbolSet = true;
        try (Scanner grammarFile = new Scanner(new File("E:\\DARIA\\Daria\\University\\Anul 3\\Semestrul 1\\LFTC\\labs\\lab8\\tema5\\src\\main\\java\\org\\example\\gramatica.txt"))) {
            while (grammarFile.hasNext()) {
                char nonTerminal = grammarFile.next().charAt(0);
                String production = grammarFile.next();
                if (isStartSymbolSet) {
                    grammar.setStartSymbol(nonTerminal);
                    isStartSymbolSet = false;
                }
                grammar.addRule(nonTerminal, production);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nu s-a putut deschide fișierul de gramatică.");
            return;
        }

        try (Scanner inputFile = new Scanner(new File("E:\\DARIA\\Daria\\University\\Anul 3\\Semestrul 1\\LFTC\\labs\\lab8\\tema5\\src\\main\\java\\org\\example\\input.txt"))) {
            if (inputFile.hasNext()) {
                String inputSequence = inputFile.next();
                if (grammar.parse(inputSequence)) {
                    System.out.println("Secvența de intrare este acceptată.");
                } else {
                    System.out.println("Secvența de intrare nu este acceptată.");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nu s-a putut deschide fișierul de intrare.");
        }
    }
}

class Pair<F, S> {
    public F first;
    public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
