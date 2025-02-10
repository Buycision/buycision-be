package project.buysellservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import project.buysellservice.global.config.MinioConfig;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(MinioConfig.class)
public class BuySellServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuySellServiceApplication.class, args);
    }

}