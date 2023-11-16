import java.io.*;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {
    private static int N, M, n, m, p;
    private static int[][] matrix, kernel;
    private static CyclicBarrier cyclicBarrier;

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
        read("C:\\Users\\daria\\Github\\University\\Anul 3\\Semestrul 1\\PPD\\labs\\lab2\\ConvolutionJava\\input.txt");
        p = Integer.parseInt(args[0]);
        if (p != 0) {
            cyclicBarrier = new CyclicBarrier(p);
        }
        if (p == 0)
            sequential();
        else
            parallel();

        write("C:\\Users\\daria\\Github\\University\\Anul 3\\Semestrul 1\\PPD\\labs\\lab2\\ConvolutionJava\\output.txt", matrix);

        if (p == 0) {
            write("C:\\Users\\daria\\Github\\University\\Anul 3\\Semestrul 1\\PPD\\labs\\lab2\\ConvolutionJava\\valid.txt", matrix);
        } else {
            checkValid("C:\\Users\\daria\\Github\\University\\Anul 3\\Semestrul 1\\PPD\\labs\\lab2\\ConvolutionJava\\output.txt", "C:\\Users\\daria\\Github\\University\\Anul 3\\Semestrul 1\\PPD\\labs\\lab2\\ConvolutionJava\\valid.txt");
        }
    }

    private static void checkValid(String pathTest, String pathValid) throws Exception {
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

    public static void sequential() {
        long startTime = System.nanoTime();

        int[] previousLine = new int[M];
        int[] currentLine = new int[M];

        // copy the first row of the matrix to previousLine and currentLine
        System.arraycopy(matrix[0], 0, previousLine, 0, M);
        System.arraycopy(matrix[0], 0, currentLine, 0, M);

        for (int i = 0; i < N; i++) {
            int[] buffer = new int[M]; // create a buffer to store the convolution result for the current row

            for (int j = 0; j < M; j++) {
                int output;
                // compute the convolution for the current element in the matrix
                output = calculateConvolution(previousLine, j, 0) + calculateConvolution(currentLine, j, 1) +
                        calculateConvolution(matrix[min(N - 1, i + 1)], j, 2);

                buffer[j] = output;
            }

            // copy the contents of the buffer back to the matrix for the current row
            System.arraycopy(buffer, 0, matrix[i], 0, M);

            // update previousLine and currentLine for the next iteration
            System.arraycopy(currentLine, 0, previousLine, 0, currentLine.length);
            System.arraycopy(matrix[min(N - 1, i + 1)], 0, currentLine, 0, currentLine.length);
        }

        long endTime = System.nanoTime();
        System.out.println((double) (endTime - startTime) / 1E6);
    }

    public static void parallel() throws InterruptedException {
        Thread[] t = new MyThread[p];

        int start, end = 0;
        // the only thing I changed is that we no longer need to calculate the max between N and M because we only use line offset
        int segment = N / p;
        int rest = N % p;

        long startTime = System.nanoTime();

        for (int i = 0; i < t.length; i++) {
            start = end;
            end = start + segment;
            if (rest > 0) {
                end++;
                rest--;
            }
            t[i] = new MyThread(start, end);
            t[i].start();
        }

        for (Thread thread : t) {
            thread.join();
        }

        long stopTime = System.nanoTime();
        System.out.println((double) (stopTime - startTime) / 1E6);
    }

    // function to compute the convolution for a single element
    private static int calculateConvolution(int[] values, int j, int kernelRow) {
        return values[max(j - 1, 0)] * kernel[kernelRow][0] + values[j] * kernel[kernelRow][1] +
                values[min(M - 1, j + 1)] * kernel[kernelRow][2];
    }

    public static class MyThread extends Thread {
        int start, end;

        public MyThread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            int[] previousLine = new int[M];
            int[] currentLine = new int[M];

            // copy the rows that this thread is responsible for
            System.arraycopy(matrix[Math.max(start - 1, 0)], 0, previousLine, 0, M);
            System.arraycopy(matrix[start], 0, currentLine, 0, M);

            // I use two buffers to handle edge cases where the convolution operation involves neighboring elements that may not be available
            int[] bufferTop = new int[M];
            int[] bufferBottom = new int[M];

            for (int i = start; i < end; i++) { // loop through the rows assigned to this thread
                for (int j = 0; j < M; j++) {
                    int output;
                    // compute the convolution for the current element in the matrix
                    output = calculateConvolution(previousLine, j, 0) + calculateConvolution(currentLine, j, 1) +
                            calculateConvolution(matrix[min(N - 1, i + 1)], j, 2);

                    if (i == start) {
                        bufferTop[j] = output;
                    } else if (i == end - 1) {
                        bufferBottom[j] = output;
                    } else {
                        matrix[i][j] = output;
                    }
                }

                System.arraycopy(currentLine, 0, previousLine, 0, currentLine.length);
                System.arraycopy(matrix[min(N - 1, i + 1)], 0, currentLine, 0, currentLine.length);
            }

            try {
                cyclicBarrier.await(); // synchronize with other threads using a cyclic barrier
            } catch (InterruptedException | BrokenBarrierException ignored) {
            }

            // copy the upper and lower buffers back to the matrix
            System.arraycopy(bufferTop, 0, matrix[start], 0, M);
            System.arraycopy(bufferBottom, 0, matrix[end - 1], 0, M);
        }
    }
}