package project.userservice.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "인가코드가 입력되지 않았습니다") String authCode
) {}