package project.gatewayservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthController {

    @Operation(summary = "카카오 로그인", description = "카카오 로그인 페이지로 리다이렉트합니다.")
    @GetMapping("/kakao")
    public Mono<Void> loginKakao(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().setLocation(URI.create("/oauth2/authorization/kakao"));
        return response.setComplete();
    }

    @Operation(summary = "구글 로그인", description = "구글 로그인 페이지로 리다이렉트합니다.")
    @GetMapping("/google")
    public Mono<Void> loginGoogle(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().setLocation(URI.create("/oauth2/authorization/google"));
        return response.setComplete();
    }
}
