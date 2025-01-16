package project.chatservice.domain.dto.response;

public record ChatUserResponseDto(
        String nickname,
        Boolean roomActive
) {
}
