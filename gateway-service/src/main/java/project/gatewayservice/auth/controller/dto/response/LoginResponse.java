package project.gatewayservice.auth.controller.dto.response;

public record LoginResponse(
        String userId,
        String nickname,
        String accessToken
) {
    public static LoginResponse from(String userId, String nickname, String accessToken) {
        return new LoginResponse(userId, nickname, accessToken);
    }
}
