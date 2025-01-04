package project.chatservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Table(name = "chat_room")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class ChatRoom {

    // TODO: 기본적으로 1:1 채팅을 목적으로 함 추후 User쪽이 다 끝나면 그때 마무리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    // FIXME: 유저 테이블 구현되면 그때 연결
//    @ManyToOne
//    @JoinColumn(name = "user1_id", nullable = false)
//    private User user1;
//
//    @ManyToOne
//    @JoinColumn(name = "user2_id")
//    private User user2;

    @Column(nullable = false)
    private Boolean roomActive;

    @Column(nullable = false)
    private LocalDateTime cratedAt;

    // 채팅방 비활성화
    public void roomDeActivate() {
        this.roomActive = false;
    }
}
