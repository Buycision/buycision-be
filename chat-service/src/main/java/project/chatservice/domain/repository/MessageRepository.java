package project.chatservice.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import project.chatservice.domain.entity.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
    Page<Message> findByRoomIdOrderByCreatedAtDesc(Long roomId, Pageable pageable);
}