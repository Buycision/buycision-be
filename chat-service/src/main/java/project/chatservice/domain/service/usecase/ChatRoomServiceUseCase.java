package project.chatservice.domain.service.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.chatservice.domain.dto.response.ChatRoomResponses;
import project.chatservice.domain.entity.ChatRoom;
import project.chatservice.domain.repository.ChatRoomRepository;
import project.chatservice.domain.repository.UserChatRoomRepository;
import project.chatservice.domain.service.ChatRoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceUseCase implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    @Override
    @Transactional(readOnly = true)
    public ChatRoomResponses getChatRooms(Long userId, Pageable pageable) {
        Slice<ChatRoom> slice = userChatRoomRepository.findAllByUserId(userId, pageable);

        boolean hasNext = slice.hasNext();
        int number = slice.getNumber();
        int numberOfElements = slice.getNumberOfElements();
        List<ChatRoom> userChatRooms = slice.getContent();

        return ChatRoomResponses.from(hasNext, number, numberOfElements, userChatRooms);
    }

    @Override
    @Transactional
    public void createChatRoom(Long userId, String title) {
        ChatRoom chatRoom = ChatRoom.from(title);
        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void deactivateChatRoom(Long userId, Long roomId) {

    }
}
