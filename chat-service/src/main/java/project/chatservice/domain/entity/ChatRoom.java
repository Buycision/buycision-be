package project.chatservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Table(name = "chat_room")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    // TODO: 기본적으로 1:1 채팅을 목적으로 함 추후 User쪽이 다 끝나면 그때 마무리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    private Long sender; // 보낸 사람

    private Long receiver; // 받는 사람

    @Column(nullable = false)
    private Boolean roomActive;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 채팅방 비활성화
    public void roomDeActivate() {
        this.roomActive = false;
    }

    @Builder
    public ChatRoom(Long sender, Long receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.roomActive = true;
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
