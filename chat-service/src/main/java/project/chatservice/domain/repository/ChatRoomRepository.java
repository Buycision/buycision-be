package project.chatservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.chatservice.domain.entity.ChatRoom;
import project.chatservice.domain.exception.ChatExceptionType;
import project.globalservice.exception.BaseException;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    default ChatRoom getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new BaseException(ChatExceptionType.NOT_FOUND_CHAT_ROOM));
    }

    Optional<ChatRoom> findByRoomId(Long roomId);

    // sender와 receiver로 활성화된 채팅방 조회
    @Query("""
        SELECT cr
        FROM ChatRoom cr
    """)
    Optional<ChatRoom> findActiveChatRoom();

}