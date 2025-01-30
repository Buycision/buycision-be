package project.chatservice.domain.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import project.chatservice.domain.dto.request.MessageRequest;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Document(collection = "message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Message implements Serializable {

    @Id
    private String id;

    private Long roomId; // MySQL ChatRoom

    private Long senderId; // 어떤 사용자? userId

    private String content;

    private LocalDateTime createdAt;

    public static Message of(MessageRequest chatMessageDto) {
        return Message.builder()
                .roomId(chatMessageDto.roomId())
                .senderId(chatMessageDto.userId())
                .content(chatMessageDto.content())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}


