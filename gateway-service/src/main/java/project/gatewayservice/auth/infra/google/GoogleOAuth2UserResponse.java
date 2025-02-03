package project.gatewayservice.auth.infra.google;

import project.gatewayservice.auth.infra.OAuth2UserResponse;
import project.gatewayservice.auth.service.dto.UserDto;

public record GoogleOAuth2UserResponse(String email, String name, String picture) implements OAuth2UserResponse {

    @Override
    public UserDto toUser() {
        return UserDto.builder()
                .email(email)
                .nickname(name)
                .profileImageUrl(picture)
                .build();
    }
}