package ppd.server.myserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ppd.server.myserver.datastructure.MyList;
import ppd.server.myserver.datastructure.MyLinkedList;
import ppd.server.myserver.datastructure.MyQueue;
import ppd.server.myserver.entity.Concurent;
import ppd.server.myserver.util.Constants;
import ppd.server.myserver.util.Writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ConcursService extends Constants {

    private final Logger logger = LoggerFactory.getLogger(ConcursService.class);

    private final MyLinkedList resultList = new MyLinkedList();

    private final MyList myList = new MyList();

    private final MyQueue queue;

    private final Map<String, ReentrantLock> access = new HashMap<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(Constants.P_R);

    private final AtomicInteger readersLeft = new AtomicInteger();

    private final Map<String, Double> countryToRanking = new HashMap<>();

    private long lastUpdate = System.currentTimeMillis();

    private final long updateInterval = TimeUnit.MILLISECONDS.toMillis(Constants.DELTA_T);

    private final CountDownLatch writingCompletionLatch = new CountDownLatch(Constants.P_W);

    public ConcursService() {
        this.queue = new MyQueue(readersLeft);
        for(int i = 0; i < 1000; ++i) {
            access.put(String.valueOf(i), new ReentrantLock());
        }
    }

    public void setReadersLeft(int readersLeft) {
        this.readersLeft.set(readersLeft);
        startWriting();
    }

    private void startWriting() {
        List<Thread> consumerThreads = new ArrayList<>();

        for (int i = 0; i < Constants.P_W; ++i) {
            consumerThreads.add(new Writer(resultList, myList, queue, readersLeft, access));
        }
        consumerThreads.forEach(Thread::start);
        consumerThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            writingCompletionLatch.await(); // wait for all writer threads to signal completion
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        writeClasamentFinal();
    }

    public void insertConcurenti(List<Concurent> concurentList) {
        logger.info("Am primit concurenti de la " + concurentList.get(0).getId() + " pana la " +
            concurentList.get(concurentList.size() - 1).getId());
        executor.execute(() -> {
            for(Concurent concurent : concurentList) {
                try {
                    queue.push(concurent);
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(readersLeft.decrementAndGet() == 0) {
                queue.finish();
            }
        });
    }

    public Map<String, Double> calculateRanking() {
        long currentTime = System.currentTimeMillis();
        logger.info("Current time: " + currentTime + " thread: " + Thread.currentThread().getName());
        if (currentTime - lastUpdate < updateInterval && !countryToRanking.isEmpty()) {
            return countryToRanking;
        }

        Map<String, Double> countryToScore = new HashMap<>();
        for (Concurent concurent : resultList.getItemsAsList()) {
            countryToScore.putIfAbsent(concurent.getCountry(), 0.0);
            countryToScore.put(concurent.getCountry(), (countryToScore.get(concurent.getCountry()) + concurent.getScore()));
        }

        List<Map.Entry<String, Double>> sortedCountries = new ArrayList<>(countryToScore.entrySet());
        sortedCountries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        countryToRanking.clear();

        logger.info("Clasament:\n");
        for (int i = 0; i < sortedCountries.size(); ++i) {
            countryToRanking.put(computeCountry(sortedCountries.get(i).getKey(), i), sortedCountries.get(i).getValue());
            logger.info(sortedCountries.get(i).getKey() + " " + sortedCountries.get(i).getValue());
        }

        lastUpdate = currentTime;

        return countryToRanking;
    }

    private String computeCountry(String countryName, int rank) {
        return switch (rank) {
            case 0 -> "First: " + countryName;
            case 1 -> "Second: " + countryName;
            case 2 -> "Third: " + countryName;
            case 3 -> "Fourth: " + countryName;
            case 4 -> "Fifth: " + countryName;
            default -> "";
        };
    }

    private void writeClasamentFinal() {
        resultList.sortList();
        checkCompliance();

        byte[] concurentiRankingContent = getConcurentiRankingContent();
        byte[] countriesRankingContent = getCountriesRankingContent();
        myList.deleteAll();
        resultList.deleteAll();

        writeToFile("clasamentFinalConcurenti.txt", concurentiRankingContent);
        writeToFile("clasamentFinalCountries.txt", countriesRankingContent);

        writingCompletionLatch.countDown();
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

    private byte[] getConcurentiRankingContent() {

        StringBuilder contentBuilder = new StringBuilder();
        for (Concurent concurent : resultList.getItemsAsList()) {
            contentBuilder.append(concurent.toString()).append(System.lineSeparator());
        }
        return contentBuilder.toString().getBytes();
    }

    private byte[] getCountriesRankingContent() {
        StringBuilder contentBuilder = new StringBuilder();
        Map<String, Double> countriesRanking = calculateRanking();
        List<Map.Entry<String, Double>> sortedCountries = new ArrayList<>(countriesRanking.entrySet());
        sortedCountries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        for (Map.Entry<String, Double> sortedCountry : sortedCountries) {
            contentBuilder.append(sortedCountry.getKey()).append(" Total score: ")
                .append(sortedCountry.getValue()).append(System.lineSeparator());
        }
        return contentBuilder.toString().getBytes();
    }

    private void writeToFile(String fileName, byte[] content) {
        try {
            Path filePath = Paths.get(fileName);
            Files.write(filePath, content);
            logger.info("File {} created with ranking content.", fileName);
        } catch (IOException e) {
            logger.error("Error writing file {}: {}", fileName, e.getMessage());
        }
    }

    public byte[] loadFinalRankingConcurentiFile() {
        try {
            Path filePath = Paths.get("clasamentFinalConcurenti.txt");
            return Files.readAllBytes(filePath);
        } catch (Exception e) {
            logger.error("Error reading file {}: {}", "clasamentFinalConcurenti.txt", e.getMessage());
            return null;
        }
    }

    public byte[] loadFinalRankingCountriesFile() {
        try {
            Path filePath = Paths.get("clasamentFinalCountries.txt");
            return Files.readAllBytes(filePath);
        } catch (Exception e) {
            logger.error("Error reading file {}: {}", "clasamentFinalCountries.txt", e.getMessage());
            return null;
        }
    }

    private void checkCompliance() {
        try {
            Path filePath = Paths.get("src/main/resources/valid.txt");
            List<String> validLines = Files.readAllLines(filePath);
            List<Concurent> validConcurenti = new ArrayList<>();
            for (String validLine : validLines) {
                String[] tokens = validLine.split(",");
                String idAndCountry = tokens[0].substring(10);
                String id = idAndCountry.split(" ")[0];
                String country = getCountry(idAndCountry.split(" ")[1]);
                Double score = Double.parseDouble(tokens[1]);
                validConcurenti.add(new Concurent(id, score, country));
            }
            for(int i = 0; i < validConcurenti.size(); ++i) {
                if(!Objects.equals(validConcurenti.get(i).getScore(), resultList.getItemsAsList().get(i).getScore())) {
                    logger.error("Invalid result at line " + (i + 1) + "!");
                    break;
                }
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
