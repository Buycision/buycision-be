package project.chatservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.chatservice.domain.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(Long roomId);

    // sender와 receiver로 활성화된 채팅방 조회
    @Query("""
        SELECT cr
        FROM ChatRoom cr
        WHERE 
            (cr.sender = :userId1 AND cr.receiver = :userId2) 
            OR 
            (cr.sender = :userId2 AND cr.receiver = :userId1)
        AND cr.roomActive = TRUE
    """)
    Optional<ChatRoom> findActiveChatRoom(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

}