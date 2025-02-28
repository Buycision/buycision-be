package project.gatewayservice.auth.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "인가코드가 입력되지 않았습니다") String accessToken
) {}