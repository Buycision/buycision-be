package project.gatewayservice.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gatewayservice.auth.controller.dto.request.LoginRequest;
import project.gatewayservice.auth.controller.dto.response.LoginResponse;
import project.gatewayservice.auth.service.AuthService;
import reactor.core.publisher.Mono;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/{oAuthType}")
    public Mono<ResponseEntity<LoginResponse>> login(
            @RequestBody @Valid LoginRequest loginRequest,
            @PathVariable String oAuthType
    ) {
        log.info("loginRequest: {}", loginRequest);
        return authService.login(loginRequest, oAuthType)
                .map(ResponseEntity::ok);
    }
}
