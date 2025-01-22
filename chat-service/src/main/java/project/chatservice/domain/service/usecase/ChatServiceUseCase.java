package project.chatservice.domain.service.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.chatservice.domain.dto.request.MessageRequest;
import project.chatservice.domain.dto.response.ChatPageResponseDto;
import project.chatservice.domain.dto.response.ChatResponseDto;
import project.chatservice.domain.dto.response.ChatUserResponseDto;
import project.chatservice.domain.dto.response.MessageResponse;
import project.chatservice.domain.entity.ChatRoom;
import project.chatservice.domain.entity.Message;
import project.chatservice.domain.entity.MessageType;
import project.chatservice.domain.repository.ChatRoomRepository;
import project.chatservice.domain.repository.MessageRepository;
import project.chatservice.domain.service.ChatService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceUseCase implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

    @Override
    public MessageResponse processMessage(MessageRequest request, String sessionId, String nickname) {
        // 메시지 처리 로직
        Message message = Message.builder()
                .roomId(request.roomId())
                .senderId(request.userId())
                .content(request.content())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        messageRepository.save(message);

        return MessageResponse.builder()
                .type(MessageType.CHAT)
                .content(request.content())
                .sessionId(sessionId)
                .nickname(nickname)
                .build();
    }

    @Override
    public ChatResponseDto getMessages(String authorizationHeader, Long roomId, int size, int page) {
        // 채팅 메시지 조회 로직
        ChatRoom chatRoom = validateChatRoom(roomId);

        Page<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtDesc(roomId, PageRequest.of(page, size));

        List<MessageRequest> chatList = messages.getContent().stream()
                .map(m -> MessageRequest.builder()
                        .content(m.getContent())
                        .roomId(m.getRoomId())
                        .userId(m.getSenderId())
                        .receiverId(extractReceiverId(chatRoom, m.getSenderId()))
                        .build())
                .collect(Collectors.toList());

        ChatPageResponseDto pageableDto = ChatPageResponseDto.builder()
                .size(size)
                .page(page)
                .totalPages(messages.getTotalPages())
                .totalElements(messages.getTotalElements())
                .build();

        ChatUserResponseDto userDto = new ChatUserResponseDto("사용자 닉네임", chatRoom.getRoomActive());

        return ChatResponseDto.builder()
                .user(userDto)
                .pageableDto(pageableDto)
                .chatList(chatList)
                .build();
    }

    private ChatRoom validateChatRoom(Long roomId) {
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByRoomId(roomId);
        if (optionalChatRoom.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다. roomId=" + roomId);
        }
        ChatRoom chatRoom = optionalChatRoom.get();

        if (!chatRoom.getRoomActive()) {
            throw new IllegalStateException("이미 비활성화된 채팅방입니다. roomId=" + roomId);
        }
        return chatRoom;
    }

    private Long extractReceiverId(ChatRoom chatRoom, Long senderId) {
        if (chatRoom.getSender().equals(senderId)) {
            return chatRoom.getReceiver();
        } else {
            return chatRoom.getSender();
        }
    }
}