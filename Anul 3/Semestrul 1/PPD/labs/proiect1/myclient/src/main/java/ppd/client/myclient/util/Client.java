package ppd.client.myclient.util;

import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ppd.client.myclient.MyClientApplication;
import ppd.client.myclient.entity.Concurent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

public class Client extends MyClientApplication {

    private static final int CHUNK_SIZE = 20;

    private static final double DELTA_X = 1;

    private final String serverUrl;

    @Getter
    private final ExecutorService executor;

    private final List<Concurent> concurenti;

    public Client(String serverUrl, List<Concurent> concurenti) {
        this.serverUrl = serverUrl;
        this.executor = Executors.newFixedThreadPool(5);
        this.concurenti = concurenti;
    }

    public void startSending() {
        // create a CountDownLatch to synchronize the threads
        CountDownLatch latch = new CountDownLatch(Constants.NUMBER_OF_THREADS);
        for(int i = 0; i < Constants.NUMBER_OF_THREADS; ++i) {
            int finalI = i;
            int size = concurenti.size() / Constants.NUMBER_OF_THREADS;
            executor.submit(() -> {
                int start = finalI * size;
                int end = start + size;
                for(int j = start; j < end; j += CHUNK_SIZE) {
                    sendChunk(concurenti.subList(j, Math.min(j + CHUNK_SIZE, end)));
                    System.out.println("Sent " + j + " to " + Math.min(j + CHUNK_SIZE, end));
                    try {
                        Thread.sleep((long) (DELTA_X * 1000));
                    } catch(InterruptedException e) {
                        System.out.println("Interrupted while sleeping!");
                    }
                }
                getClasament();
                getClasamentFinal();
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch(InterruptedException e) {
            System.out.println("Interrupted while waiting for threads to finish!");
        }
        executor.shutdown();
    }

    private void sendChunk(List<Concurent> chunk) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Concurent>> request = new HttpEntity<>(chunk, headers);
        String response = restTemplate.postForObject(serverUrl + "/concurenti", request, String.class);
        System.out.println("Raspuns de la server: " + response);
    }

    public void sendChunkSize(int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer> request = new HttpEntity<>(size / 20, headers);
        // create an HTTP request entity with the batch size and send it asynchronously
        CompletableFuture<String> response = CompletableFuture.supplyAsync(() -> {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject(serverUrl + "/readersLeft", request, String.class);
        });
        try {
            System.out.println("Raspuns de la server: " + response.get(1, TimeUnit.SECONDS));
        } catch(ExecutionException | TimeoutException | InterruptedException e) {
            System.out.println("Late response");
        }
    }

    public void getClasament() {
        RestTemplate restTemplate = new RestTemplate();
        // send a request to the server to calculate and return the ranking
        CompletableFuture<Map<String, Double>> ranking = CompletableFuture.supplyAsync(() -> {
            ResponseEntity<Map<String, Double>> responseEntity = restTemplate.exchange(
                    serverUrl + "/getClasament",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });
            return responseEntity.getBody();
        });

        try {
            ranking.get()
                    .forEach((key, value) -> System.out.println(key + " " + value +
                            ", de la threadul cu id = " + Thread.currentThread().getId()));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void getClasamentFinal() {
        RestTemplate restTemplate = new RestTemplate();
        // send a request to the server to get the final ranking files as a byte array
        byte[] zipFile = restTemplate.getForObject(serverUrl + "/clasamentFinal", byte[].class);
        // save the received byte array as a local zip file
        saveFile("clasamentFinal" + Thread.currentThread().getId() + ".zip", Objects.requireNonNull(zipFile));
    }

    private void saveFile(String fileName, byte[] fileContent) {
        try {
            Path filePath = Paths.get(fileName);
            Files.write(filePath, fileContent);
            System.out.println("Fisier " + fileName + " salvat local.");
        } catch (IOException e) {
            System.out.println("Error saving file " + fileName + ": " + e.getMessage());
        }
    }

}