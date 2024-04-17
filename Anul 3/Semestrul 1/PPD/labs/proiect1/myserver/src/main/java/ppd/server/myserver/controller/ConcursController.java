package ppd.server.myserver.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import ppd.server.myserver.entity.Concurent;
import ppd.server.myserver.service.ConcursService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/concurs")
public class ConcursController extends ConcursService {
    Log logger = LogFactory.getLog(ConcursController.class);
    private final ConcursService service;

    public ConcursController(ConcursService service) {
        this.service = service;
    }

    @Async
    @PostMapping("/concurenti")
    public CompletableFuture<ResponseEntity<?>> insertConcurenti(@RequestBody List<Concurent> concurentList) {
        service.insertConcurenti(concurentList);
        return CompletableFuture.completedFuture(new ResponseEntity<>("OK", HttpStatus.OK));
    }

    @Async
    @GetMapping("/getClasament")
    public CompletableFuture<ResponseEntity<?>> calculateRanking() {
        logger.info("Get clasament");
        return CompletableFuture.completedFuture(new ResponseEntity<>(service.calculateRanking(), HttpStatus.OK));
    }

    @Async
    @GetMapping("/clasamentFinal")
    public CompletableFuture<ResponseEntity<byte[]>> getFinalRanking() {
        try {
            logger.info("Get clasament final");
            byte[] concurentiRankingContent = service.loadFinalRankingConcurentiFile();
            byte[] countriesRankingContent = service.loadFinalRankingCountriesFile();

            ByteArrayOutputStream zipStream = new ByteArrayOutputStream();
            try (ZipOutputStream zipOut = new ZipOutputStream(zipStream)) {
                addToZip("clasamentFinalConcurenti.txt", concurentiRankingContent, zipOut);
                addToZip("clasamentFinalCountries.txt", countriesRankingContent, zipOut);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "clasamentFinal.zip");
            return CompletableFuture.completedFuture(new ResponseEntity<>(zipStream.toByteArray(), headers, HttpStatus.OK));
        } catch (IOException e) {
            return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Async
    @PostMapping("/readersLeft")
    public CompletableFuture<ResponseEntity<?>> setReadersLeft(@RequestBody Integer readersLeft) {
        service.setReadersLeft(readersLeft);
        logger.info("Total producer tasks: " + readersLeft);
        return CompletableFuture.completedFuture(new ResponseEntity<>("OK", HttpStatus.OK));
    }

    private void addToZip(String fileName, byte[] content, ZipOutputStream zipOut) throws IOException {
        ZipEntry entry = new ZipEntry(fileName);
        zipOut.putNextEntry(entry);
        zipOut.write(content);
        zipOut.closeEntry();
    }
}
