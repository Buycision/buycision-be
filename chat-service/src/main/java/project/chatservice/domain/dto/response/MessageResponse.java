package project.chatservice.domain.dto.response;

import lombok.Builder;
import project.chatservice.domain.entity.MessageType;

@Builder
public record MessageResponse(
        MessageType type,
        String content,
        String sessionId,
        String nickname,
        Long roomId
) {
    public static MessageResponse from(String content, String sessionId, String nickname, Long roomId) {
        return MessageResponse.builder()
                .type(MessageType.CHAT)
                .content(content)
                .sessionId(sessionId)
                .nickname(nickname)
                .roomId(roomId)
                .build();
    }
}