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
) {}