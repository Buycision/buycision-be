package project.userservice.domain.dto.response;

import project.userservice.domain.entity.User;

public record UserInfoResponse(
        Long id,
        String email,
        String phoneNumber,
        String nickname,
        String profileImageUrl,
        Double reliability
) {
    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getNickname(),
                user.getProfileImageUrl(),
                user.getReliability()
        );
    }
}
