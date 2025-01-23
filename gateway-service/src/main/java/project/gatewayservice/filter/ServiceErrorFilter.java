package project.gatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ServiceErrorFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String reqId = exchange.getRequest().getId();
        HttpMethod reqMethod = exchange.getRequest().getMethod();
        String reqPath = exchange.getRequest().getPath().toString();

        return chain.filter(exchange)
                .doOnSuccess(aVoid -> {
                    HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
                    if (statusCode != null && statusCode.is4xxClientError()) {
                        log.error("[ID: {}] [{}]: '{}' 해당 API가 존재하지 않아요 - 상태 코드: {}",
                                reqId, reqMethod, reqPath, statusCode);
                    } else if (statusCode != null && statusCode.is5xxServerError()) {
                        log.error("[ID: {}] [{}]: '{}' 오류가 발생했어요 - 상태 코드: {}",
                                reqId, reqMethod, reqPath, statusCode);
                    }
                })
                .onErrorResume(throwable -> {
                    log.error("오류가 발생했습니다.: ", throwable);
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    DataBuffer buffer = exchange.getResponse().bufferFactory()
                            .wrap("오류가 발생했습니다.".getBytes(StandardCharsets.UTF_8));
                    return exchange.getResponse().writeWith(Mono.just(buffer));
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
