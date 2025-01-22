package project.gatewayservice.auth.service.dto;

import lombok.Builder;
import project.gatewayservice.auth.domain.OAuthType;

@Builder
public record UserDto(
        Long id,
        String email,
        String phoneNumber,
        String nickname,
        String profileImageUrl,
        Double reliability,
        OAuthType oAuthType
) {
}
