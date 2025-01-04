package project.chatservice.domain.dto.request;

import lombok.Builder;

@Builder
public record MessageRequestDto(
        String content,
        Long roomId,
        Long userId
) {}
