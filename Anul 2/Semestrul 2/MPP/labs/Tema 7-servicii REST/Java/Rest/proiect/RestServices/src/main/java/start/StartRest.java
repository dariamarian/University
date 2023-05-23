package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.Properties;

@ComponentScan({"proiect.persistence.repository","proiect.services.rest"})
@SpringBootApplication
public class StartRest {
    public static void main(String[] args) {

        SpringApplication.run(StartRest.class, args);
    }

    @Bean(name="props")
    @Primary
    public Properties getServerProperties(){
        Properties props = new Properties();
        try {
            props.load(StartRest.class.getResourceAsStream("/server.properties"));
        } catch (IOException e) {
            System.err.println("Configuration file bd.cong not found" + e);
        }
        return props;
    }
}