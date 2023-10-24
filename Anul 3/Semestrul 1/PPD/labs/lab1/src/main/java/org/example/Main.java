package org.example;

import java.util.Arrays;
import java.util.Random;

public class Main {

    private static int[] A, B, C;
    static int N = 10;
    static int P = 4;

    public static void main(String[] args) {

        Random rand = new Random();

        int L = 5;
        int threadTasks = N / P;
        int reminder = N % P;

        //MyThread[] threads = new MyThread[P];
        MyThread2[] threads = new MyThread2[P];

        A = new int[N];
        B = new int[N];
        C = new int[N];

        for (int i = 0; i < A.length; i++) {
            A[i] = rand.nextInt(L) + 1;
            B[i] = rand.nextInt(L) + 1;
            C[i] = 0;
        }

        int start = 0;
        int end;
        for (int i = 0; i < P; ++i) {
            //threads[i] = new MyThread1(i);
            threads[i] = new MyThread2(i);
            threads[i].start();
        }

//        for (int i = 0; i < P; ++i) {
//            end = start + threadTasks;
//            if (reminder > 0) {
//                --reminder;
//                ++end;
//            }
//            threads[i] = new MyThread(start, end);
//            threads[i].start();
//            start = end;
//        }
//
//
//        int reminderUsed = 0;
//        for (int i = 0;  i < P; ++i) {
//            int start = i * threadTasks + reminderUsed;
//            int end = (i + 1) * threadTasks + reminderUsed - 1;
//            if (reminder > 0) {
//                ++reminderUsed;
//                --reminder;
//                ++end;
//            }
//            threads[i] = new MyThread(start, end);
//            threads[i].start();
//        }

        for (int i = 0; i < P; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(Arrays.toString(A));
        System.out.println(Arrays.toString(B));
        System.out.println(Arrays.toString(C));
    }

    public static class MyThread extends Thread {

        private int start, end;

        public MyThread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; ++i) {
                C[i] = A[i] + B[i];
            }
        }
    }

    public static class MyThread2 extends Thread {

        private int id;

        public MyThread2(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            for (int i = id; i < N; i = i + P) {
                C[i] = A[i] + B[i];
            }
        }
    }
}