package pl.edu.pk.ztpprojekt4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ZtpProjekt4Application {

    public static void main(String[] args) {
        SpringApplication.run(ZtpProjekt4Application.class, args);
    }

    @Bean
    public WebClient localApiClient() {
        return WebClient.create("http://localhost:8080/api/v1");
    }
}
