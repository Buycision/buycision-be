package project.chatservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.chatservice.domain.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(Long roomId);
}