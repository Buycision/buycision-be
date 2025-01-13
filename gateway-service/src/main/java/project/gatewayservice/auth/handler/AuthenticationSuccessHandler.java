package project.gatewayservice.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.gatewayservice.auth.service.JwtProvider;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        String token = jwtProvider.createToken(authentication.getName());

        // test logging
        log.info("Login Success token: {}", token);

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().add("Authorization", token);
        response.setStatusCode(HttpStatus.OK);

        return webFilterExchange.getChain().filter(webFilterExchange.getExchange());
    }
}
