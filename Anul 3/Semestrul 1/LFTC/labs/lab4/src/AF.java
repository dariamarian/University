import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AF {
    private Set<String> multimeStari;
    private Set<String> alfabetIntrare;
    private Map<String, Map<String, Set<String>>> tranzitii;
    private String stareInitiala;
    private Set<String> stariFinale;

    public AF(Set<String> multimeStari, Set<String> alfabetIntrare, Map<String, Map<String, Set<String>>> tranzitii, String stareInitiala, Set<String> stariFinale) {
        this.multimeStari = multimeStari;
        this.alfabetIntrare = alfabetIntrare;
        this.tranzitii = tranzitii;
        this.stareInitiala = stareInitiala;
        this.stariFinale = stariFinale;
    }

    public boolean isNondeterministic() {
        Set<String> checkedTransitions = new HashSet<>();

        for (String currentState : tranzitii.keySet()) {
            Map<String, Set<String>> transitions = tranzitii.get(currentState);
            for (String inputSymbol : transitions.keySet()) {
                Set<String> nextStates = transitions.get(inputSymbol);

                if (nextStates.size() > 1) {
                    return true;
                }

                String nextState = nextStates.iterator().next();
                String transitionPair = currentState + " " + inputSymbol + " " + nextState;

                if (checkedTransitions.contains(transitionPair)) {
                    return true;
                }

                checkedTransitions.add(transitionPair);
            }
        }
        return false;
    }


    public boolean verifySequence(String sequence) {
        String currentState = stareInitiala;

        for (int i = 0; i < sequence.length(); i++) {
            String inputSymbol = String.valueOf(sequence.charAt(i));

            if (!alfabetIntrare.contains(inputSymbol) || !tranzitii.get(currentState).containsKey(inputSymbol)) {
                return false;
            }

            String nextState = tranzitii.get(currentState).get(inputSymbol).iterator().next();
            currentState = nextState;
        }

        return stariFinale.contains(currentState);
    }


    public String findLongestPrefix(String sequence) {
        String prefix = "";
        String currentState = stareInitiala;
        String longestAcceptedPrefix = "";

        for (int i = 0; i < sequence.length(); i++) {
            String inputSymbol = String.valueOf(sequence.charAt(i));
            if (!alfabetIntrare.contains(inputSymbol) || !tranzitii.get(currentState).containsKey(inputSymbol)) {
                break;
            }

            currentState = tranzitii.get(currentState).get(inputSymbol).iterator().next();
            prefix += inputSymbol;

            if (stariFinale.contains(currentState)) {
                longestAcceptedPrefix = prefix;
            }
        }

        return longestAcceptedPrefix;
    }


    public void printMultimeStari() {
        System.out.print("Multimea starilor: { ");
        for (String element : multimeStari) {
            System.out.print(element + " ");
        }
        System.out.println("}.");
    }

    public void printAlfabetIntrare() {
        System.out.print("Alfabetul de intrare: { ");
        for (String element : alfabetIntrare) {
            System.out.print(element + " ");
        }
        System.out.println("}.");
    }

    public void printTranzitii() {
        System.out.println("Tranzitiile:");
        for (String currentState : multimeStari) {
            for (String symbol : alfabetIntrare) {
                Set<String> nextStates = tranzitii.get(currentState).get(symbol);
                if (nextStates != null) {
                    for (String nextState : nextStates) {
                        System.out.println(currentState + " -> " + symbol + " -> " + nextState);
                    }
                }
            }
        }
    }

    public void printStariFinale() {
        System.out.print("Starile finale: { ");
        for (String element : stariFinale) {
            System.out.print(element + " ");
        }
        System.out.println("}.");
    }
}
