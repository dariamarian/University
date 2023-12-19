package org.example;

import org.example.domain.Node;
import org.example.domain.Concurent;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Pair {
    public String id;
    public String country;

    Pair(String id, String country) {
        this.id = id;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair pair)) return false;
        return Objects.equals(id, pair.id) && Objects.equals(country, pair.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country);
    }
}

class MyList {
    // A list of pairs (id, country) that have been already processed
    // This is used to avoid processing the same concurent multiple times
    // in the case that it appears in multiple files
    List<Pair> list = new ArrayList<>();
    Lock lock = new ReentrantLock();

    public boolean contains(Pair pair) {
        lock.lock();
        boolean value = list.contains(pair);
        lock.unlock();
        return value;
    }

    public void add(Pair pair) {
        lock.lock();
        list.add(pair);
        lock.unlock();
    }

    public List<Pair> getList() {
        return list;
    }
}

public class Main {
    private static final int readers = 4;
    private static final int writers = 4;
    private static final List<String> files = new ArrayList<>();
    private static final MyLinkedList resultList = new MyLinkedList();
    private static final MyList myList = new MyList();
    private static final ExecutorService executor = Executors.newFixedThreadPool(readers);
    private static final AtomicInteger readersLeft = new AtomicInteger(50);
    private static final MyQueue queue = new MyQueue(readersLeft);
    private static final Map<String, ReentrantLock> access = new HashMap<>();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        obtainFileNames();

        for (int i = 0; i < 1000; ++i) {
            access.put(String.valueOf(i), new ReentrantLock());
        }

        Thread[] writersThreads = new Thread[writers];

        for (int i = 0; i < writers; ++i) {
            Thread thread = new Writer();
            writersThreads[i] = thread;
        }

        Arrays.stream(writersThreads).forEach(Thread::start);

        files.forEach(file -> executor.execute(() -> {
            try {
                Reader(file);
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        executor.shutdown();

        Arrays.stream(writersThreads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        myList.getList().forEach(item -> resultList.addConcurent(new Concurent(item.id, -1, item.country)));

        resultList.sortList();
        printListToFile(resultList, "clasament_parallel");

        checkCompliance();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    private static void checkCompliance() {
        LinkedList<Concurent> valid = new LinkedList<>();

        try {
            File obj = new File("src/main/resources/clasament_valid");
            Scanner scanner = new Scanner(obj);

            while (scanner.hasNextLine()) {
                String[] rez = scanner.nextLine().split(",");
                valid.add(new Concurent(rez[0], Integer.parseInt(rez[1]), ""));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Concurent> result = resultList.getItemsAsList();

        if (valid.size() != result.size()) {
            throw new Error("The sizes of the two results differ");
        }

        for (int i = 0; i < result.size() - 1; ++i) {
            if (result.get(i).getScore() < result.get(i + 1).getScore()) {
                throw new Error("The array is not sorted in the right direction");
            }
        }

        for (Concurent concurent : result) {
            if (valid.stream()
                    .noneMatch(item ->
                            item.getId().equals(concurent.getId()) &&
                                    item.getScore() == concurent.getScore()
                    )
            ) {
                throw new Error(String.format("Element with id %s and score %d and country %s exists in the calculated result but doesn't in the valid result", concurent.getId(), concurent.getScore(), concurent.getCountry()));
            }
        }
    }

    private static void printListToFile(MyLinkedList list, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/" + filename));

            Node node = list.head.next;
            if (node == list.tail) {
                return;
            }
            while (node.isNotLastNode()) {
                bw.write(node.getData().getId() + "," + node.getData().getScore() + "," + node.getData().getCountry());
                bw.newLine();
                node = node.next;
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void Reader(String file) throws FileNotFoundException, InterruptedException {
        String country = "C" + file.charAt(file.indexOf('C') + 1);
        File obj = new File("src/main/resources/input_files/" + file);
        Scanner scanner = new Scanner(obj);

        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");

            queue.push(new Concurent(data[0], Integer.parseInt(data[1]), country));
        }

        if (readersLeft.decrementAndGet() == 0) {
            queue.finish();
        }
    }

    public static class Writer extends Thread {
        @Override
        public void run() {
            while (readersLeft.get() != 0 || !queue.isEmpty()) {
                Concurent concurent = null;
                try {
                    concurent = queue.pop();
                } catch (InterruptedException ignored) {
                }

                if (concurent == null) {
                    queue.finish();
                    continue;
                }

                access.get(concurent.getId()).lock();

                if (!myList.contains(new Pair(concurent.getId(), concurent.getCountry()))) {
                    if (concurent.getScore() == -1) {
                        resultList.deleteConcurent(concurent);
                        myList.add(new Pair(concurent.getId(), concurent.getCountry()));
                    } else {
                        Node actual = resultList.updateConcurent(concurent);

                        if (actual == null) {
                            resultList.addConcurent(concurent);
                        }
                    }
                }

                access.get(concurent.getId()).unlock();
            }
        }
    }

    private static void obtainFileNames() {
        String format = "RezultateC%d_P%d";
        for (int i = 1; i <= 5; ++i) {
            for (int j = 1; j <= 10; ++j) {
                files.add(String.format(format, i, j));
            }
        }
    }
}
