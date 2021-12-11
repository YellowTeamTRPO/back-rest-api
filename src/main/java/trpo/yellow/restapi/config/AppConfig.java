package trpo.yellow.restapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplateBuilder restTemplateBuilder(){
        return new RestTemplateBuilder();
    }

    @Bean
    public RestTemplate restTemplate(
            @Value("${app.loginService.connection.timeout}") int connectionTimeout,
            @Value("${app.loginService.read.timeout}") int readTimeout,
            RestTemplateBuilder restTemplateBuilder
    ){

        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }
}
