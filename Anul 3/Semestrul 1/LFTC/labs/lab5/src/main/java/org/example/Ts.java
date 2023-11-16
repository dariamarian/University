package org.example;

import java.util.Arrays;

public class Ts {
    private final int SIZE = 1000;
    private final String[] hashTable = new String[SIZE];

    public Integer addAtom(String element) {
        int hashed = 0;
        while (hashed < SIZE && hashTable[hashed] != null && !hashTable[hashed].isEmpty() && element.compareTo(hashTable[hashed]) > 0) {
            if (element.equals(hashTable[hashed])) {
                // Element already exists, return its index
                return hashed;
            }
            ++hashed;
        }

        if (hashed == SIZE) {
            return null;
        }

        // Shift elements to make room for the new one
        for (int i = SIZE - 1; i > hashed; i--) {
            hashTable[i] = hashTable[i - 1];
        }

        hashTable[hashed] = element;

        return hashed;
    }

    public Integer findAtom(String element) {
        for (int i = 0; i < SIZE; i++) {
            if (hashTable[i] != null && hashTable[i].equals(element)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(hashTable);
    }

    public String printMaxNotNull() {
        StringBuilder result = new StringBuilder();
        String pattern = "%s %d\n";
        for (int index = 0; index < SIZE && hashTable[index] != null; ++index) {
            result.append(String.format(pattern, hashTable[index], index));
        }

        return result.toString();
    }
}
