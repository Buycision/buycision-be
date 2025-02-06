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

    Optional<ChatRoom> findByIsActivatedTrue();
}