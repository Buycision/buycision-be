package project.chatservice.domain.dto.request;

import lombok.Builder;

@Builder
public record MessageRequest(
        String content,
        Long roomId,
        Long userId,
        Long receiverId
) {}