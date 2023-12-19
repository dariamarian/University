import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class FileGenerator {

    public static List<String> countries = new ArrayList<>();

    public static List<String> Concurents = new ArrayList<>();

    public static List<String> getCountries(){
        if(countries.isEmpty())
            generateCountries();
        return countries;
    }

    private static void generateCountries() {
        countries.add("Romania");
        countries.add("Austria");
        countries.add("Italia");
        countries.add("Franta");
        countries.add("Spania");
    }

    private static void generateNames() {
       for(int i = 0; i < 80; i++)
           Concurents.add("Concurent" + i);
    }

    public static void generateInputFiles() {
        generateCountries();
        generateNames();
        for(String country : countries) {
            for(int i = 0; i < 10; i++){
                createFile(country, i);
            }
        }
    }

    private static void createFile(String country, int pos) {
        String currentFilename = country + pos + ".txt";
        try(BufferedWriter br = new BufferedWriter(new FileWriter("files/input/" + currentFilename))) {
            Random random = new Random();
            for(String name : Concurents){
                double value = Math.round((random.nextDouble() * 11 - 1) * 100) / 100.0;
                if(value < 0)
                    value = -1;
                if(value != 0)
                    br.write(name + "-" + country + ";" + value + "\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
