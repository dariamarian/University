package ppd.client.myclient.util;


import ppd.client.myclient.entity.Concurent;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class IOHandler {

    public static List<String> generateFileNames() {
        List<String> files = new LinkedList<>();
        String format = "RezultateC%d_P%d";
        for (int i = 1; i <= Constants.NUMBER_OF_THREADS; ++i) {
            for (int j = 1; j <= 10; ++j) {
                files.add(String.format(format, i, j));
            }
        }
        return files;
    }

    public static List<Concurent> readConcurenti(List<String> files) {
        List<Concurent> concurenti = new LinkedList<>();
        for (String file : files) {
            String country = file.split("_")[0].substring(9);
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input/" + file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(",");
                    concurenti.add(new Concurent(tokens[0], Double.parseDouble(tokens[1]), getCountry(country)));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return concurenti;
    }

    private static String getCountry(String country) {
        return switch(country) {
            case "C1" -> "Romania";
            case "C2" -> "Franta";
            case "C3" -> "Elvetia";
            case "C4" -> "Italia";
            case "C5" -> "Spania";
            default -> throw new RuntimeException("Invalid country!");
        };
    }
}
