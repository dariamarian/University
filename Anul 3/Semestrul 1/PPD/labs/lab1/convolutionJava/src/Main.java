import java.io.*;
import java.util.Scanner;

public class Main {
    private static int N, M, n, m, p, lineOffset, columnOffset;
    private static int[][] matrix, kernel, finalMatrix;

    public static void read(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            if (myReader.hasNextLine()) {
                N = Integer.parseInt(myReader.nextLine());
                M = Integer.parseInt(myReader.nextLine());
            }
            matrix = new int[N][M];
            if (myReader.hasNextLine()) {
                for (int i = 0; i < N; i++) {
                    String data = myReader.nextLine();
                    String[] line = data.split(" ");
                    for (int j = 0; j < M; j++) {
                        matrix[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }

            if (myReader.hasNextLine()) {
                n = Integer.parseInt(myReader.nextLine());
                m = Integer.parseInt(myReader.nextLine());
            }
            kernel = new int[n][m];
            if (myReader.hasNextLine()) {
                for (int i = 0; i < n; i++) {
                    String data = myReader.nextLine();
                    String[] line = data.split(" ");
                    for (int j = 0; j < m; j++) {
                        kernel[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void write(String path, int[][] matrix) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (int[] elem : matrix) {
                for (int i : elem) {
                    bw.write(i + " ");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        read("C:\\Users\\daria\\Github\\University\\Anul3\\Semestrul1\\PPD\\labs\\lab1\\ConvolutionJava\\input.txt");
        finalMatrix = new int[N][M];
        p = Integer.parseInt(args[0]);
        lineOffset = (n - 1) / 2;
        columnOffset = (m - 1) / 2;
        if (p == 0)
            sequentialConvolution();
        else
            parallelConvolution();

        write("C:\\Users\\daria\\Github\\University\\Anul3\\Semestrul1\\PPD\\labs\\lab1\\ConvolutionJava\\output.txt", finalMatrix);

        if (p == 0) {
            write("C:\\Users\\daria\\Github\\University\\Anul3\\Semestrul1\\PPD\\labs\\lab1\\ConvolutionJava\\valid.txt", finalMatrix);
        } else {
            checkCompliance("C:\\Users\\daria\\Github\\University\\Anul3\\Semestrul1\\PPD\\labs\\lab1\\ConvolutionJava\\output.txt", "C:\\Users\\daria\\Github\\University\\Anul3\\Semestrul1\\PPD\\labs\\lab1\\ConvolutionJava\\valid.txt");
        }
    }

    private static void checkCompliance(String pathTest, String pathValid) throws Exception {
        File objTest = new File(pathTest);
        Scanner readerTest = new Scanner(objTest);

        File objValid = new File(pathValid);
        Scanner readerValid = new Scanner(objValid);

        while (readerTest.hasNextLine() && readerValid.hasNextLine()) {
            String test = readerTest.nextLine();
            String valid = readerValid.nextLine();

            if (!valid.equals(test)) {
                throw new Exception("Invalid output");
            }
        }

        if (readerTest.hasNextLine() || readerValid.hasNextLine()) {
            throw new Exception("Invalid output");
        }
    }

    public static int singlePixelConvolution(int x, int y) {
        int output = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // Compute neighbours
                int ii = x - lineOffset + i;
                int jj = y - columnOffset + j;

                // Out of bounds - line:
                if (ii < 0) ii = 0;
                else if (ii >= N) ii = N - 1;
                // Out of bounds - column:
                if (jj < 0) jj = 0;
                else if (jj >= M) jj = M - 1;

                output += matrix[ii][jj] * kernel[i][j];
            }
        }
        return output;
    }

    public static void sequentialConvolution() {
        long startTime = System.nanoTime();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                finalMatrix[i][j] = singlePixelConvolution(i, j);
            }
        }

        long endTime = System.nanoTime();
        System.out.println((double)(endTime - startTime) / 1E6);
    }

    public static void parallelConvolution() throws InterruptedException {
        Thread[] t = new MyThread[p];

        int start, end = 0;
        int mx = Math.max(N, M);
        int chunk = mx / p;
        int rest = mx % p;

        long startTime = System.nanoTime();

        for (int i = 0; i < t.length; i++) {
            start = end;
            end = start + chunk;
            if (rest > 0) {
                end++;
                rest--;
            }
            t[i] = new MyThread(M, start, end, finalMatrix);
            t[i].start();
        }

        for (Thread thread : t) {
            thread.join();
        }

        long stopTime = System.nanoTime();

        System.out.println((double)(stopTime - startTime) / 1E6);
    }

    public static class MyThread extends Thread {
        int M, start, end;
        int[][] finalMatrix;

        public MyThread(int M, int start, int end, int[][] finalMatrix) {
            this.M = M;
            this.start = start;
            this.end = end;
            this.finalMatrix = finalMatrix;
        }

        public void run() {
            if (N > M) {
                for (int i = start; i < end; i++) {
                    for (int j = 0; j < M; j++) {
                        this.finalMatrix[i][j] = Main.singlePixelConvolution(i, j);
                    }
                }
            }
            else {
                for (int i = 0; i < N; i++) {
                    for (int j = start; j < end; j++) {
                        this.finalMatrix[i][j] = Main.singlePixelConvolution(i, j);
                    }
                }
            }

        }
    }
}