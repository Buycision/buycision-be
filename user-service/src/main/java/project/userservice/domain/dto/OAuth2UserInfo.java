package project.userservice.domain.dto;

import project.globalservice.exception.BaseException;
import lombok.Builder;
import project.userservice.domain.entity.Authority;
import project.userservice.domain.entity.User;
import project.userservice.domain.exception.AuthExceptionType;

import java.util.Map;

@Builder
public record OAuth2UserInfo(
        String name,
        String email,
        String profile
) {
    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) { // registration id별로 userInfo 생성
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            default -> throw new BaseException(AuthExceptionType.ILLEGAL_REGISTRATION_ID);
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
//                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .build();
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
//                .profile((String) profile.get("profile_image_url"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(name)
                .profileImageUrl(profile)
                .reliability(0.0)
                .authority(Authority.ROLE_USER)
                .build();
    }
}
