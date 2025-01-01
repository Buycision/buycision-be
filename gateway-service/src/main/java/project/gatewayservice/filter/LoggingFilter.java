package project.gatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpMethod reqMethod = exchange.getRequest().getMethod();
        String reqUri = exchange.getRequest().getURI().toString();

        log.info("{}, {} 정보로 요청이 들어왔어요.", reqMethod, reqUri);

        long startTime = System.currentTimeMillis();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long duration = System.currentTimeMillis() - startTime;
            int statusCode = Objects.requireNonNull(exchange.getResponse().getStatusCode()).value();

            log.info("{} 요청이 완료되었어요. [{}ms], 상태 코드: {}", reqUri, duration, statusCode);
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
