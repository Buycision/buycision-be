package project.chatservice.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.chatservice.domain.entity.ChatRoom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByRoomId(Long roomId);

    // TODO : User 테이블 완성 시
//    @Query("SELECT cr FROM chat_room cr WHERE (cr.user1.userId = :userId OR cr.user2.userId = :userId) AND cr.roomActive = TRUE")
//    Optional<ChatRoom> findActiveChatRoomByUserId(@Param("userId") Long userId);

    List<ChatRoom> findAllByRoomActiveIsTrue();

    List<ChatRoom> findAllByCreatedAtAfter(LocalDateTime createAt);
}
