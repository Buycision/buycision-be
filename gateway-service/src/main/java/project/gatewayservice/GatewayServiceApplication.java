package project.gatewayservice;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

@EnableWebFlux
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "project")
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    @Lazy(false)
    public Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> apis(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        Objects.requireNonNull(definitions).stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
            String name = routeDefinition.getId().replaceAll("-service", "");
            AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl(name, DEFAULT_API_DOCS_URL+"/" + name, null);
            urls.add(swaggerUrl);
        });
        swaggerUiConfigProperties.setUrls(urls);
        return urls;
    }
}
