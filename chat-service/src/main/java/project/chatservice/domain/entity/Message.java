package project.chatservice.domain.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import project.chatservice.domain.dto.ChatMessageDto;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Document(collection = "message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Message {

    @Id
    private String id;

    private Long roomId; // MySQL ChatRoom

    private Long senderId; // 어떤 사용자? userId

    private String content;

    private LocalDateTime createdAt;

    @Builder
    public Message(Long roomId, Long senderId, String content, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Message of(ChatMessageDto chatMessageDto) {
        return Message.builder()
                .roomId(chatMessageDto.roomId())
                .senderId(chatMessageDto.userId())
                .content(chatMessageDto.content())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

}


