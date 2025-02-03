package project.gatewayservice.auth.infra.google;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth.google")
public class GoogleConfig {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String tokenUri;
    private String resourceUri;
    private String grantType;
}
