package project.userservice.domain.dto.request;

public record UserRenewPasswordRequest(
        String password,    // 기존 비밀번호
        String newPassword  // 새 비밀번호
) {}
