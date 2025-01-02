package project.gatewayservice.auth.controller.dto.response;

public record LoginResponse(
        String userId,
        String nickname,
        String accessToken,
        String refreshToken
) {
    public static LoginResponse from(String userId, String nickname, String accessToken, String refreshToken) {
        return new LoginResponse(userId, nickname, accessToken, refreshToken);
    }
}
