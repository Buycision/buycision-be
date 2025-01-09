package project.userservice.domain.dto.request;

import project.userservice.domain.entity.User;

public record UserSignUpRequest(
        Long id,
        String email,
        String phoneNumber,
        String nickname,
        String profileImageUrl
) {
    public User toEntity() {
        return User.builder()
                .id(id)
                .email(email)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .reliability(0.0)
                .build();
    }
}
