import java.util.*;
import org.junit.jupiter.api.Assertions;

public class Lab1 {

    /**
     * 1.Să se determine ultimul (din punct de vedere alfabetic) cuvânt care poate apărea într-un text care conține mai
     * multe cuvinte separate prin ” ” (spațiu). De ex. ultimul (dpdv alfabetic) cuvânt din ”Ana are mere rosii si galbene”
     * este cuvântul "si".
     * @param text - textul pentru care determinam cuvantul
     * @return ultimul cuvant dpdv alfabetic
     */
    public static String ex_1(String text){
        String[] cuvinte=text.split(" "); //separam textul in cuvinte
        String ultimulCuvant="";
        for(String cuvant :cuvinte) //parcurgem cuvintele din text
        {
            if(ultimulCuvant.compareTo(cuvant)<0) //comparam ultimul cuvant cu cuvantul curent (ultimulCuvant<cuvant)
                ultimulCuvant=cuvant;
        }
        return ultimulCuvant;
    }

    public static void test_ex_1()
    {
        Assertions.assertEquals(ex_1(""), "");
        Assertions.assertEquals(ex_1(" "), "");
        Assertions.assertEquals(ex_1("Ana"), "Ana"); // usor
        Assertions.assertEquals(ex_1("Ana are mere rosii si galbene"), "si"); // mediu
        Assertions.assertEquals(ex_1("Ana are mere rosii galbene si verzi pe care le cumpara de la piata"), "verzi"); // complex

        System.out.println("Test 1 cu succes");
    }

    /**
     * 2.Să se determine distanța Euclideană între două locații identificate prin perechi de numere.
     * De ex. distanța între (1,5) și (4,1) este 5.0
     * @param primaLocatie - prima locatie din operatie
     * @param aDouaLocatie - a doua locatie din operatie
     * @return distanta euclideana dintre cele doua
     */
    public static double ex_2(double[] primaLocatie, double[] aDouaLocatie){
        // distanta Euclideana = radical[(1-4)^2 + (5-1)^2]
        return Math.sqrt(Math.pow(primaLocatie[0] - aDouaLocatie[0], 2) + Math.pow(primaLocatie[1] - aDouaLocatie[1], 2));
    }

    public static void test_ex_2()
    {
        Assertions.assertEquals(ex_2(new double[]{1, 5}, new double[]{4, 1}),5.0); // usor
        Assertions.assertEquals(ex_2(new double[]{100,108}, new double[]{90,108}),10.0); // mediu
        Assertions.assertEquals(ex_2(new double[]{12,4}, new double[]{7,10}),7.810249675906654); // complex

        System.out.println("Test 2 cu succes");
    }

    /**
     * 3.Să se determine produsul scalar a doi vectori rari care conțin numere reale. Un vector este rar atunci când
     * conține multe elemente nule. Vectorii pot avea oricâte dimensiuni. De ex. produsul scalar a 2 vectori
     * unidimensionali [1,0,2,0,3] și [1,2,0,3,1] este 4.
     * @param primulVector - primul vector din operatie
     * @param alDoileaVector - al doilea vector din operatie
     * @return produsul scalar al celor doi vectori rari
     */
    public static double ex_3(double[] primulVector, double[] alDoileaVector)
    {
        // produsul scalar=1*1+0*2+2*0+0*3+3*1
        double produs = 0;
        for(int i = 0; i < primulVector.length; i++) {
            produs += primulVector[i] * alDoileaVector[i];
        }
        return produs;
    }

    public static void test_ex_3()
    {
        Assertions.assertEquals(ex_3(new double[]{1, 0}, new double[]{0, 2}), 0); // usor
        Assertions.assertEquals(ex_3(new double[]{1, 0, 2, 0, 3}, new double[]{1, 2, 0, 3, 1}), 4); // mediu
        Assertions.assertEquals(ex_3(new double[]{1, 0, 2, 0, 3, 8, 0, 12, 15}, new double[]{1, 2, 0, 3, 1, 2, 140, 0, 1}), 35); // complex

        System.out.println("Test 3 cu succes");
    }

    /**
     * 4.Să se determine cuvintele unui text care apar exact o singură dată în acel text. De ex. cuvintele care apar o
     * singură dată în ”ana are ana are mere rosii ana" sunt: 'mere' și 'rosii'.
     * @param text - textul pentru care determinam cuvintele
     * @return lista de cuvinte care apar o singura data in text
     */
    public static List<String> ex_4(String text){

        String[] cuvinte = text.split(" "); //separam textul in cuvinte
        List<String> rezultat = new ArrayList<>();
        Map<String, Integer> nrAparitii = new HashMap<>(); //stocam fiecare cuvant si nr lui de aparitii

        for(String cuvant : cuvinte)
        {
            if(nrAparitii.containsKey(cuvant))
            {
                int nouNrAparitii = nrAparitii.get(cuvant) + 1; //crestem nr de ap de fiecare data cand vedem cuvantul
                nrAparitii.replace(cuvant, nouNrAparitii);
            }
            else {
                nrAparitii.put(cuvant, 1);
            }
        }
        for(String cuvant : nrAparitii.keySet()) {
            if(nrAparitii.get(cuvant).equals(1)) {
                rezultat.add(cuvant); //punem in rezultat cuvintele care apar o data
            }
        }

        return rezultat;
    }

    public static void test_ex_4()
    {
        String input = "ana ana";
        Assertions.assertEquals(ex_4(input).size(),0); // usor

        String input1 = "ana are ana are mere rosii ana";
        Assertions.assertEquals(ex_4(input1).size(),2);
        Assertions.assertEquals(ex_4(input1).get(0),"mere");
        Assertions.assertEquals(ex_4(input1).get(1),"rosii"); // mediu

        String input2 = "ana are mere ana are mere rosii ana ana mere are";
        Assertions.assertEquals(ex_4(input2).size(),1);
        Assertions.assertEquals(ex_4(input2).get(0),"rosii"); // complex

        System.out.println("Test 4 cu succes");
    }

    /**
     * 5.Pentru un șir cu n elemente care conține valori din mulțimea {1, 2, ..., n - 1} astfel încât o singură valoare
     * se repetă de două ori, să se identifice acea valoare care se repetă. De ex. în șirul [1,2,3,4,2] valoarea 2
     * apare de două ori.
     * @param sir - sirul pentru care determinam valoarea
     * @return valoarea care apare de doua ori
     */
    public static int ex_5(int n, int[] sir)
    {
        int sumaSir = 0, suma=0;
        for(int element : sir)
            sumaSir += element; // calculam suma elementelor din sir
        for(int i=1; i<n; i++)
            suma+=i; // calculam suma de la 1 la n-1

        return sumaSir-suma;
    }

    public static void test_ex_5()
    {
        Assertions.assertEquals(ex_5(3,new int[]{1, 2, 1}),1); // usor
        Assertions.assertEquals(ex_5(5,new int[]{1, 2, 3, 4, 2}),2); // mediu
        Assertions.assertEquals(ex_5(11,new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 8}),8); // complex

        System.out.println("Test 5 cu succes");
    }

    /**
     *  6.Pentru un șir cu n numere întregi care conține și duplicate, să se determine elementul majoritar (care apare
     *  de mai mult de n / 2 ori). De ex. 2 este elementul majoritar în șirul [2,8,7,2,2,5,2,3,1,2,2].
     * @param sir - sirul pentru care determinam elementul
     * @return elementul majoritar
     */
    public static int ex_6(int[] sir)
    {
        Map<Integer, Integer> nrAparitii = new HashMap<>(); //stocam fiecare element si nr lui de aparitii

        for(int element : sir)
        {
            if(nrAparitii.containsKey(element))
            {
                int nouNrAparitii = nrAparitii.get(element) + 1; //crestem nr de ap de fiecare data cand vedem elementul
                nrAparitii.replace(element, nouNrAparitii);
            }
            else {
                nrAparitii.put(element, 1);
            }
        }
        for(Integer element : nrAparitii.keySet()) {
            if(nrAparitii.get(element) >= sir.length / 2) {
                return element; //returnam elementul care apare de minim n/2
            }
        }
        return -1; //returnam -1 daca nu exista un asemenea element
    }

    public static void test_ex_6()
    {
        Assertions.assertEquals(ex_6(new int[]{1,8,7,6,4}),-1);
        Assertions.assertEquals(ex_6(new int[]{1,8,7,1,1}),1); // usor
        Assertions.assertEquals(ex_6(new int[]{2,8,7,2,2,5,2,3,1,2,2}),2); // greu
        Assertions.assertEquals(ex_6(new int[]{2,8,7,2,2,5,2,3,1,2,2,5,5,5,5,5,5,5,5}),5); // complex

        System.out.println("Test 6 cu succes");
    }

    /**
     * 7.Să se determine al k-lea cel mai mare element al unui șir de numere cu n elemente (k < n).
     * De ex. al 2-lea cel mai mare element din șirul [7,4,6,3,9,1] este 7.
     * @param sir - sirul pentru care determinam cel mai mare element
     * @param k - indicele elementului
     * @return al k-lea cel mai mare element
     */
    private static int ex_7(int[] sir, int k)
    {
        int index;

        //functie de sortare cu 2 foruri ca sa nu folosim sort
        for (int i = 0; i <sir.length; i++){
            index = i;
            for (int j = i ; j <= sir.length-1; j++){
                if (sir[j] > sir[index]){
                    index = j;
                }
            }
            int temp = sir[i];
            sir[i] = sir[index];
            sir[index] = temp;
        }
        return sir[k-1];
    }

    public static void test_ex_7()
    {
        Assertions.assertEquals(ex_7(new int[]{7,4}, 1),7); // usor
        Assertions.assertEquals(ex_7(new int[]{7,4,6,3,9,1}, 2),7); // greu
        Assertions.assertEquals(ex_7(new int[]{7,4,6,3,9,1,13,8,3,17,100}, 3),13); // complex

        System.out.println("Test 7 cu succes");
    }

    /**
     * Functie care imi transforma un numar intreg in reprezentarea sa binara
     * @param numar - numarul care trebuie convertit
     * @return un String care reprezinta numarul in binar
     */
    public static String convertIntToBinary(int numar)
    {
        String rez="";
        int[] binar = new int[100];
        int index = 0;

        while (numar > 0) {
            binar[index++] = numar % 2;
            numar = numar / 2;
        }
        index--;

        for (int i = index; i >= 0; i--)
            rez += binar[i]+"";
        return rez;
    }

    /**
     * 8. Să se genereze toate numerele (în reprezentare binară) cuprinse între 1 și n. De ex. dacă
     * n = 4, numerele sunt: 1, 10, 11, 100.
     * @param n - numarul pentru care cautam elementele cuprinse intre 1 si el
     * @return numerele binare cuprinse intre 1 si n
     */
    private static ArrayList<String> ex_8(int n)
    {
        ArrayList<String> rezultat = new ArrayList<>();

        for(int i=1;i<=n;i++)
            rezultat.add(convertIntToBinary(i));
        return rezultat;
    }

    public static void test_ex_8()
    {
        ArrayList<String> rezultat = ex_8(1);
        ArrayList<String> test = new ArrayList<>();
        test.add("1");
        Assertions.assertEquals(rezultat,test); // usor

        ArrayList<String> rezultat1 = ex_8(4);
        ArrayList<String> test1 = new ArrayList<>(Arrays.asList("1","10","11","100"));
        Assertions.assertEquals(rezultat1,test1); // mediu

        ArrayList<String> rezultat2 = ex_8(10);
        ArrayList<String> test2 = new ArrayList<>(Arrays.asList("1","10","11","100","101","110","111","1000","1001","1010"));
        Assertions.assertEquals(rezultat2,test2); // complex

        System.out.println("Test 8 cu succes");
    }

    /**
     * 9. Considerându-se o matrice cu n x m elemente întregi și o listă cu perechi formate din coordonatele a 2 căsuțe
     * din matrice ((p,q) și (r,s)), să se calculeze suma elementelor din sub-matricile identificate de fieare pereche.
     * De ex, pt matricea
     * [[0, 2, 5, 4, 1],
     * [4, 8, 2, 3, 7],
     * [6, 3, 4, 6, 2],
     * [7, 3, 1, 8, 3],
     * [1, 5, 7, 9, 4]]
     * și lista de perechi ((1, 1) și (3, 3)), ((2, 2) și (4, 4)), suma elementelor din prima
     * sub-matrice este 38, iar suma elementelor din a 2-a sub-matrice este 44.
     * @param matrice - matricea pentru care calculam
     * @param primaPereche - prima pereche implicata in calcul
     * @param aDouaPereche - a doua pereche implicata in calcul
     * @return suma elementelor din sub-matrici
     */
    private static int ex_9(int[][] matrice, int[] primaPereche, int[] aDouaPereche)
    {
        int suma = 0;
        for(int i = primaPereche[0]; i <= aDouaPereche[0]; i++) {
            for(int j = primaPereche[1]; j <= aDouaPereche[1]; j++) {
                suma += matrice[i][j];
            }
        }
        return suma;
    }

    public static void test_ex_9()
    {
        int[][] matrice = new int[][]{{0,2}, {4,8}, {6,3}, {7,3}, {1,5}};
        Assertions.assertEquals(ex_9(matrice, new int[]{0,0}, new int[]{1,1}),14); // usor

        int[][] matrice1 = new int[][]{{0,2,5,4,1}, {4,8,2,3,7}, {6,3,4,6,2}, {7,3,1,8,3}, {1,5,7,9,4}};
        Assertions.assertEquals(ex_9(matrice1, new int[]{1,1}, new int[]{3,3}),38);
        Assertions.assertEquals(ex_9(matrice1, new int[]{2,2}, new int[]{4,4}),44); // mediu

        int[][] matrice2 = new int[][]{{0,2,5,4,1,6,8,9,10}, {4,8,2,3,7,12,3,4,5}, {6,3,4,6,2,19,12,0,3}, {7,3,1,8,3,21,3,5,78}, {1,5,7,9,4,12,2,4,10}};
        Assertions.assertEquals(ex_9(matrice2, new int[]{1,1}, new int[]{3,3}),38);
        Assertions.assertEquals(ex_9(matrice2, new int[]{2,2}, new int[]{4,4}),44); // complex

        System.out.println("Test 9 cu succes");
    }

    /**
     * 10.	Considerându-se o matrice cu n x m elemente binare (0 sau 1) sortate crescător pe linii,
     * să se identifice indexul liniei care conține cele mai multe elemente de 1.
     * De ex. în matricea
     * [[0,0,0,1,1],
     * [0,1,1,1,1],
     * [0,0,1,1,1]]
     * a doua linie conține cele mai multe elemente 1.
     * @param matrice - matricea pentru care determinam indexul liniei
     * @return indexul liniei cu cele mai multe elemente 1
     */
    private static int ex_10(int[][] matrice)
    {
        int sumaMaxima = 0;
        int linie = -1;

        for(int i = 0; i < matrice.length; i++)
        {
            int suma = 0;
            for (int j = 0; j < matrice[i].length; j++) {
                suma += matrice[i][j]; // calculam suma elementelor de pe linia i
            }
            if(suma > sumaMaxima) { // comparam cu sumaMaxima
                linie = i; // daca e mai mare, linia cautata devine linia i
            }
        }

        return linie;
    }

    public static void test_ex_10()
    {
        int[][] matrice = new int[][]{{0}, {1}, {0}};
        Assertions.assertEquals(ex_10(matrice),1); // usor

        int[][] matrice1 = new int[][]{{0, 0, 0, 1, 1}, {0, 1, 1, 1, 1}, {0, 0, 1, 1, 1}};
        Assertions.assertEquals(ex_10(matrice1),2); // mediu

        int[][] matrice2 = new int[][]{{0, 0, 0, 1, 1, 1, 0, 1}, {0, 1, 1, 1, 1, 0, 1, 0}, {0, 0, 1, 1, 1, 1, 0, 0}};
        Assertions.assertEquals(ex_10(matrice2),2); // complex

        System.out.println("Test 10 cu succes");
    }
}