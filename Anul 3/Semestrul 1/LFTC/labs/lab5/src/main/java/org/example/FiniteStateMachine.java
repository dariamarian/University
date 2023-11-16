package org.example;

import java.util.*;

public class FiniteStateMachine {
    private Set<String> states;
    private Set<String> alphabet;
    private List<Transition> transitions;
    private String initialState;
    private Set<String> finalStates;

    private final Set<String> letters = new HashSet<>();
    private final Set<String> digits = new HashSet<>();
    private final Set<String> octalDigits = new HashSet<>();
    private final Set<String> hexDigits = new HashSet<>();
    private final Set<String> binaryDigits = new HashSet<>();
    private final Set<String> nonZeroDigits = new HashSet<>();

    public FiniteStateMachine(Set<String> states, Set<String> alphabet, List<Transition> transitions, String initialState, Set<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.initialState = initialState;
        this.finalStates = finalStates;

        Collections.addAll(letters, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "X", "Y", "Z");
        Collections.addAll(digits, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        Collections.addAll(nonZeroDigits, "1", "2", "3", "4", "5", "6", "7", "8", "9");
        Collections.addAll(binaryDigits, "0", "1");
        Collections.addAll(octalDigits, "0", "1", "2", "3", "4", "5", "6", "7");
        Collections.addAll(hexDigits, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f");
    }

    public String getOffset(String sequence) {
        String result = "";
        StringBuilder temporary = new StringBuilder();

        String actualState = initialState;
        for (int index = 0; index < sequence.length(); ++index) {
            String finalActualState = actualState;
            List<Transition> validTransitions = transitions.stream().filter(transition -> transition.getInitialState().equals(finalActualState)).toList();

            int finalIndex = index;
            List<Transition> validTransition = new ArrayList<>(validTransitions.stream().filter(transition -> Objects.equals(transition.getValue(), String.valueOf(sequence.charAt(finalIndex)))).toList());

            if (validTransition.size() != 1) {
                boolean globalValid = false;
                for (Transition transition : validTransitions) {
                    if (transition.getValue().length() == 1) continue;

                    boolean valid = false;
                    switch (transition.getValue()) {
                        case "dd" -> {
                            valid = digits.stream().anyMatch(digit -> digit.charAt(0) == (sequence.charAt(finalIndex)));
                        }
                        case "d*" -> {
                            valid = nonZeroDigits.stream().anyMatch(digit -> digit.charAt(0) == (sequence.charAt(finalIndex)));
                        }
                        case "letter" -> {
                            valid = letters.stream().anyMatch(digit -> digit.charAt(0) == (sequence.charAt(finalIndex)));
                        }
                        default -> {
                            System.out.println("ERROR! Invalid state machine");
                            System.exit(0);
                        }
                    }

                    if (valid && !globalValid) {
                        validTransition.add(transition);
                        globalValid = true;
                    }
                }

                if (!globalValid) {
                    return result;
                }
            }
            temporary.append(sequence.charAt(index));

            actualState = validTransition.get(0).getFinalState();

            String finalActualState1 = actualState;
            if (finalStates.stream().anyMatch(state -> state.equals(finalActualState1))) {
                result = temporary.toString();
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "FiniteStateMachine{" +
                "states=" + states +
                ", alphabet=" + alphabet +
                ", transitions=" + transitions +
                ", initialState='" + initialState + '\'' +
                ", finalStates=" + finalStates +
                ", letters=" + letters +
                ", digits=" + digits +
                '}';
    }
}