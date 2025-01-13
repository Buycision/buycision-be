package project.gatewayservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import project.gatewayservice.filter.dto.ResponseBody;
import project.globalservice.response.BaseResponse;
import reactor.core.publisher.Mono;

import static project.gatewayservice.filter.dto.GlobalPathConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper;
    private final ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String reqId = exchange.getRequest().getId();
        HttpMethod reqMethod = exchange.getRequest().getMethod();
        String reqPath = exchange.getRequest().getPath().toString();

        log.info("[ID: {}] [{}]: {} 요청이 들어왔어요.", reqId, reqMethod, reqPath);

        if (AUTH_WHITELIST.stream().anyMatch(reqPath::startsWith)) {
            return chain.filter(exchange);
        }

        long startTime = System.currentTimeMillis();

        return modifyResponseBodyGatewayFilterFactory
                .apply(modifyResponseGatewayFilterConfig(startTime, reqId, reqMethod, reqPath))
                .filter(exchange, chain);
    }

    private ModifyResponseBodyGatewayFilterFactory.Config modifyResponseGatewayFilterConfig(
            long startTime, String reqId, HttpMethod reqMethod, String reqPath) {
        return new ModifyResponseBodyGatewayFilterFactory.Config()
                .setRewriteFunction(String.class, String.class, (exchange, body) -> {
                            long duration = System.currentTimeMillis() - startTime;
                            HttpStatusCode statusCode = exchange.getResponse().getStatusCode();

                            log.info("[ID: {}] [{}]: {} 요청이 완료됐어요. 응답 시간: [{}ms], 상태 코드: {}",
                                    reqId, reqMethod, reqPath, duration, statusCode);

                            if (statusCode != null && statusCode.is2xxSuccessful()) {
                                ResponseBody responseBody = ResponseBody.of(body);
                                BaseResponse<Object> baseResponse = new BaseResponse<>(responseBody.body());

                                try {
                                    //log.info("[ID: {}] 응답: {}", reqId, objectMapper.writeValueAsString(baseResponse));
                                    return Mono.justOrEmpty(objectMapper.writeValueAsString(baseResponse));
                                } catch (JsonProcessingException e) {
                                    return Mono.empty();
                                }
                            }

                            return Mono.justOrEmpty(body);
                        }
                );
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
