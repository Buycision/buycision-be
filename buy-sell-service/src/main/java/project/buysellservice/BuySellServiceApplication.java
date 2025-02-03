package project.buysellservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BuySellServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuySellServiceApplication.class, args);
    }

}