package project.chatservice.domain.service;

import org.springframework.data.domain.Pageable;
import project.chatservice.domain.dto.response.ChatRoomResponses;

public interface ChatRoomService {

    ChatRoomResponses getChatRooms(Long userId, Pageable pageable);

    void createChatRoom(Long userId, String title);

    void deactivateChatRoom(Long userId, Long roomId);

}
