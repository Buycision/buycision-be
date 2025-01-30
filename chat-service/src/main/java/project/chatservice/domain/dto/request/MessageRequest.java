package project.chatservice.domain.dto.request;

import lombok.Builder;
import project.chatservice.domain.entity.Message;

@Builder
public record MessageRequest(
        String content,
        Long roomId,
        Long userId,
        Long receiverId
) {
    public Message toEntity() {
        return Message.builder()
                .content(content)
                .roomId(roomId)
                .senderId(userId)
                .build();
    }
}