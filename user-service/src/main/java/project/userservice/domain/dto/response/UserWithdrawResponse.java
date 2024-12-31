package project.userservice.domain.dto.response;

import java.time.LocalDateTime;

public record UserWithdrawResponse(
        Long id,                 // 생성된 사용자 ID
        String nickname,         // 사용자의 닉네임
        LocalDateTime deletedAt  // 탈퇴 일시
) {
}
