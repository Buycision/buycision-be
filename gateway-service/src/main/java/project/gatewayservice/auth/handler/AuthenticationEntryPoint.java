package project.gatewayservice.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        String requestPath = exchange.getRequest().getPath().value();

        log.error("Unauthorized error: {}", ex.getMessage());
        log.error("Requested path    : {}", requestPath);

        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);

        return serverHttpResponse.setComplete();
    }
}