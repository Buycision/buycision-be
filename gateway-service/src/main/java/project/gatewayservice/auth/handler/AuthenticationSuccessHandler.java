package project.gatewayservice.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.globalservice.jwt.JwtProvider;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        String token = jwtProvider.createToken(authentication.getName());

        log.info("Login successful. Username: {}", authentication.getName());

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", token);
        response.put("message", "Login successful");

        byte[] responseBody = response.toString().getBytes(StandardCharsets.UTF_8);

        var responseHeaders = webFilterExchange.getExchange().getResponse();
        responseHeaders.setStatusCode(HttpStatus.OK);
        responseHeaders.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return responseHeaders.writeWith(Mono.just(responseHeaders.bufferFactory().wrap(responseBody)));
    }
}
