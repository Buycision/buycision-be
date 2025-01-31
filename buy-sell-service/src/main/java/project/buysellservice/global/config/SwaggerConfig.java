package project.buysellservice.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
@Component("buySellSwaggerConfig")
@Profile("buy-sell-service") //이 프로파일이 활성화될 때만 로드
public class SwaggerConfig {

    @Bean(name = "buySellCustomOpenAPI")
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("buySell API")
                        .version("v1")
                        .description("사용자 서비스 API"))
                .openapi("3.0.1");
    }
}

