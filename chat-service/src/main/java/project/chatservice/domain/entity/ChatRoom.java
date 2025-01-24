package project.chatservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private Boolean isActivated;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 채팅방 비활성화
    public void roomDeActivate() {
        this.isActivated = false;
    }

    public static ChatRoom from(String name) {
        return ChatRoom.builder()
                .name(name)
                .isActivated(true)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

}
