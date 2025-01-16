package project.gatewayservice.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAuthenticationProcessFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(context -> {
                    Authentication authentication = context.getAuthentication();
                    if (authentication != null && authentication.isAuthenticated()) {
                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(exchange.getRequest().mutate()
                                        .header("X-USER-ID", authentication.getName())
                                        .build())
                                .build();
                        return chain.filter(mutatedExchange);
                    }
                    return chain.filter(exchange);
                });
    }
}