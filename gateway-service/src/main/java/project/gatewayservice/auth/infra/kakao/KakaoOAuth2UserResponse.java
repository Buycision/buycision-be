package project.gatewayservice.auth.infra.kakao;

import project.gatewayservice.auth.domain.OAuthType;
import project.gatewayservice.auth.infra.OAuth2UserResponse;
import project.gatewayservice.auth.service.dto.UserDto;

public record KakaoOAuth2UserResponse(String email, String nickname, String profileImageUrl) implements OAuth2UserResponse {

    @Override
    public UserDto toUser() {
        return UserDto.builder()
                .email(email)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .oAuthType(OAuthType.KAKAO)
                .reliability(0.0)
                .build();
    }
}