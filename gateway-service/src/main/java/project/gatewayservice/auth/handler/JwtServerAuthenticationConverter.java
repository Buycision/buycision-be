package project.gatewayservice.auth.handler;

import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import project.gatewayservice.auth.service.dto.BearerToken;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

//        log.info("JwtServerAuthenticationConverter.convert: {}", token);

        if (token == null || !token.startsWith("Bearer ")) {
            return Mono.just(BearerToken.of("Guest"));
        }

        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.substring(7))
                .map(BearerToken::new);
    }
}
