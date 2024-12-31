package project.userservice.domain.dto.response;

public record LoginResponse(
        Long userId,
        String accessToken,
        String refreshToken
) {
    public static LoginResponse of(Long userId, String accessToken, String refreshToken) {
        return new LoginResponse(userId, accessToken, refreshToken);
    }
}
