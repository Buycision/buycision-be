package project.chatservice.domain.service.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.chatservice.domain.dto.request.MessageRequest;
import project.chatservice.domain.dto.response.MessageResponse;
import project.chatservice.domain.entity.ChatRoom;
import project.chatservice.domain.entity.Message;
import project.chatservice.domain.exception.ChatExceptionType;
import project.chatservice.domain.repository.ChatRoomRepository;
import project.chatservice.domain.repository.MessageRepository;
import project.chatservice.domain.service.ChatService;
import project.chatservice.domain.service.UserFeignClient;
import project.globalservice.exception.BaseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatServiceUseCase implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final UserFeignClient userFeignClient;

    @Override
    public MessageResponse processMessage(MessageRequest request, String sessionId, String nickname) {
        // 사용자 유효성 검사
        validateUsers(request.userId(), request.receiverId());

        // roomId 여부에 따라 채팅방 조회/생성
        ChatRoom chatRoom;
        if (request.roomId() == null) {
            chatRoom = chatRoomRepository.findByIsActivatedTrue()
                    .orElseGet(() -> {
                        // 기존 채팅방이 없으므로 새로 생성
                        ChatRoom chatRoom1 = ChatRoom.from(request.receiverId().toString());
                        return chatRoomRepository.save(chatRoom1);
                    });
        } else {
            chatRoom = validateChatRoom(request.roomId());
        }

        // 메시지 DB 저장
        Message message = Message.of(request);

        messageRepository.save(message);

        // 클라이언트로 반환할 응답
        return MessageResponse.from(request.content(), sessionId, nickname, chatRoom.getId());
    }

    private ChatRoom validateChatRoom(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.getById(roomId);

        if (!chatRoom.getIsActivated()) {
            throw new BaseException(ChatExceptionType.ALREADY_DEACTIVATED_CHAT_ROOM);
        }
        return chatRoom;
    }

    private void validateUsers(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("동일한 사용자에게 메시지를 보낼 수 없습니다.");
        }
        boolean senderValid = userFeignClient.isUserValid(senderId);
        boolean receiverValid = userFeignClient.isUserValid(receiverId);

        if (!senderValid || !receiverValid) {
            throw new IllegalArgumentException("잘못된 사용자 정보입니다. senderId="
                    + senderId + ", receiverId=" + receiverId);
        }
    }
}