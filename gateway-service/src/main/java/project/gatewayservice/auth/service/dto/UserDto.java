package project.gatewayservice.auth.service.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UserDto(
        Long id,
        String email,
        String phoneNumber,
        String nickname,
        String profileImageUrl,
        Double reliability
) {
    public static UserDto of(UserDto user) {
        return new UserDto(
                user.id(),
                user.email(),
                user.phoneNumber(),
                user.nickname(),
                user.profileImageUrl(),
                user.reliability()
        );
    }
}
