package ppd.client.myclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import ppd.client.myclient.entity.Concurent;
import ppd.client.myclient.util.Client;
import ppd.client.myclient.util.IOHandler;

import java.util.List;

@SpringBootApplication
public class MyClientApplication {

    public static void main(String[] args) {
        List<String> files = IOHandler.generateFileNames();
        List<Concurent> concurenti = IOHandler.readConcurenti(files);

        double start = System.currentTimeMillis();

        Client client = new Client("http://localhost:8080/concurs", concurenti);
        client.sendChunkSize(concurenti.size());
        client.startSending();


        double end = System.currentTimeMillis();

        System.out.println(end - start);
    }

}
