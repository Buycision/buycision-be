package project.chatservice.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import project.chatservice.domain.entity.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

    // 몽고디비에서 메세지를 조회(페이징, 정렬)
    Page<Message> findByRoomIdOrderByCreatedAtDesc(Long roomId, Pageable pageable);
}
