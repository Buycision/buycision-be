package project.communityservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
@Component("communitySwaggerConfig")
@Profile("community-service") //이 프로파일이 활성화될 때만 로드
public class SwaggerConfig {

    @Bean(name = "communityCustomOpenAPI")
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Chat API")
                        .version("v1")
                        .description("커뮤니티 서비스 API"))
                .openapi("3.0.1");
    }
}