import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<String> filenameList = new ArrayList<>();

    static MyQueue queue = new MyQueue();

    static MyLinkedList list = new MyLinkedList();

    public static void generateFilenames() {
        for(String country : FileGenerator.getCountries()) {
            for(int i = 0; i < 10; i++) {
                filenameList.add(country + i + ".txt");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        FileGenerator.generateInputFiles();
        generateFilenames();
        int numberOfThreads = Integer.parseInt(args[0]);
        if(numberOfThreads == 0)
            sequential();
        else
            parallel(numberOfThreads);
    }

    private static void sequential() {
        long startTime = System.currentTimeMillis();
        for(String filename : filenameList) {
            readSequential(filename);
        }
        writeFile("files/output/valid.txt");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    private static void readSequential(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader("files/input/" + filename))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] tokens = line.split(";");
                String name = tokens[0];
                double score = Double.parseDouble(tokens[1]);
                list.insert(new Concurent(name, score));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void parallel(int numberOfThreads) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        List<Reader> readerThreads = createReaderThreads();
        List<Worker> workerThreads = createWorkerThreads(numberOfThreads);
        readerThreads.forEach(Thread::start);
        workerThreads.forEach(Thread::start);
        for(Reader readerThread : readerThreads) {
            readerThread.join();
        }
        for(Worker workerThread : workerThreads) {
            workerThread.join();
        }
        writeFile("files/output/output.txt");
        checkValid("files/output/output.txt", "files/output/valid.txt");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    private static void readParallel(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader("files/input/" + filename))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] tokens = line.split(";");
                String name = tokens[0];
                double score = Double.parseDouble(tokens[1]);
                queue.put(new Concurent(name, score));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static class Reader extends Thread {
        int start;

        int end;

        public Reader(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for(String filename : filenameList.subList(start, end)) {
                readParallel(filename);
            }
            queue.increment();
        }
    }

    static class Worker extends Thread {

        @Override
        public void run() {
            try {
                while(true) {
                    Concurent concurent = queue.take();
                    if(concurent == null) {
                        break;
                    }
                    list.insert(concurent);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Reader> createReaderThreads() {
        int nrOfReaders = Constants.READERS;
        int filesPerReader = filenameList.size() / nrOfReaders;
        List<Reader> readerThreads = new ArrayList<>();
        int start = 0;
        for(int i = 0; i < nrOfReaders; i++) {
            int end = Math.min(start + filesPerReader, filenameList.size());
            readerThreads.add(new Reader(start, end));
            start = end;
        }
        return readerThreads;
    }

    private static List<Worker> createWorkerThreads(int numberOfThreads) {
        int nrOfWorkers = numberOfThreads - Constants.READERS;
        List<Worker> workers = new ArrayList<>();
        for(int i = 0; i < nrOfWorkers; i++) {
            workers.add(new Worker());
        }
        return workers;
    }

    private static void writeFile(String filename) {
        try(BufferedWriter br = new BufferedWriter(new FileWriter(filename))) {
            for(Concurent concurent : list.getConcurents()) {
                br.write(concurent.toString() + "\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkValid(String outputFilename, String validFilename) {
        try(BufferedReader br1 = new BufferedReader(new FileReader(outputFilename));
            BufferedReader br2 = new BufferedReader(new FileReader(validFilename))) {
            String line1;
            String line2;
            while((line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null) {
                if(!line1.equals(line2)) {
                    System.err.println("Output is not valid!");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}