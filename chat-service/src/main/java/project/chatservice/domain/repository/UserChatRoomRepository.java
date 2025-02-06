package project.chatservice.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.chatservice.domain.entity.ChatRoom;
import project.chatservice.domain.entity.UserChatRoom;
import project.chatservice.domain.exception.ChatExceptionType;
import project.globalservice.exception.BaseException;

import java.util.Optional;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {

    default UserChatRoom getByUserId(Long userId) {
        return findByUserId(userId)
                .orElseThrow(() -> new BaseException(ChatExceptionType.NOT_FOUND_USER_CHAT_ROOM));
    }

    Optional<UserChatRoom> findByUserId(Long userId);

    @Query("SELECT c FROM ChatRoom c " +
            "JOIN UserChatRoom uc ON c.id = uc.chatRoomId " +
            "WHERE uc.userId = :userId")
    Slice<ChatRoom> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
