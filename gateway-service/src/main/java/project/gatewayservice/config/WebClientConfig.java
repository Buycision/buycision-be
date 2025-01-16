package project.gatewayservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${BASE_SERVER_URL:http://localhost:8000}")
    private String BASE_URL;

    @Bean
    @LoadBalanced
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(BASE_URL)
                .build();
    }
}
