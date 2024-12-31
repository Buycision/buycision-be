package project.userservice.domain.dto.response;

import project.userservice.domain.entity.User;

import java.time.LocalDateTime;

public record UserSignUpResponse(
        Long id,                 // 생성된 사용자 ID
        String nickname,         // 사용자의 닉네임
        String email,            // 가입된 이메일
        LocalDateTime joinedAt   // 가입 일시
) {
    public static UserSignUpResponse of(User user) {
        return new UserSignUpResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}