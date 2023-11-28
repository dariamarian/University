package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GrammarReader {
    private static String startSymbol = null;

    public static void main(String[] args) {
        Set<String> nonterminals = new HashSet<>();
        Set<String> terminals = new HashSet<>();
        Set<ProductionRule> productionRules = new HashSet<>();

        readGrammar("src/main/resources/grammar.txt", nonterminals, terminals, productionRules);

        System.out.println("Simbol de start: " + startSymbol);
        System.out.println("Neterminale: " + nonterminals);
        System.out.println("Terminale: " + terminals);
        System.out.println("Reguli de productie:");
        for (ProductionRule rule : productionRules) {
            System.out.println(rule.nonterminal + " -> " + rule.rule);
        }
    }

    private static void readGrammar(String filename, Set<String> nonterminals, Set<String> terminals,
                                    Set<ProductionRule> productionRules) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s*->\\s*");
                if (parts.length == 2) {
                    String nonterminal = parts[0].trim();
                    String rule = parts[1].trim();

                    if (startSymbol == null) {
                        startSymbol = nonterminal;
                        nonterminals.add(startSymbol);
                    }

                    nonterminals.add(nonterminal);

                    productionRules.add(new ProductionRule(nonterminal, rule));
                }
            }

            for (ProductionRule rule : productionRules) {
                for (char c : rule.rule.toCharArray()) {
                    String symbol = String.valueOf(c);
                    if (!(c >= 'A' && c <= 'Z') && !symbol.equals("ε") && !symbol.equals(" ")) {
                        if (!nonterminals.contains(symbol)) {
                            terminals.add(symbol);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
