package project.chatservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
@Component("chatSwaggerConfig")
@Profile("chat-service") //이 프로파일이 활성화될 때만 로드
public class SwaggerConfig {

    @Bean(name = "chatCustomOpenAPI")
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Chat API")
                        .version("v1")
                        .description("채팅 서비스 API"))
                .openapi("3.0.1");
    }
}
