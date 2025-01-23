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
import project.chatservice.domain.exception.ChatExceptionType;
import project.chatservice.domain.repository.ChatRoomRepository;
import project.chatservice.domain.repository.MessageRepository;
import project.chatservice.domain.service.ChatService;
import project.chatservice.domain.service.UserFeignClient;
import project.globalservice.exception.BaseException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceUseCase implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final UserFeignClient userFeignClient;

    /**
     * 채팅 메시지 전송 처리
     * 1) sender, receiver 유효성 검사 (동일 ID, 탈퇴/존재 X 등)
     * 2) roomId가 없으면 (sender, receiver)로 기존 채팅방 조회 → 없으면 신규 생성
     * 3) 메시지 MongoDB 저장
     * 4) 전송 결과를 MessageResponse로 반환
     * @param request
     * @param sessionId
     * @param nickname
     * @return
     */
    @Override
    public MessageResponse processMessage(MessageRequest request, String sessionId, String nickname) {
        // 사용자 유효성 검사
        validateUsers(request.userId(), request.receiverId());

        // roomId 여부에 따라 채팅방 조회/생성
        ChatRoom chatRoom;
        if (request.roomId() == null) {
            chatRoom = chatRoomRepository.findActiveChatRoom(request.userId(), request.receiverId())
                    .orElseGet(() -> {
                        // 기존 채팅방이 없으므로 새로 생성
                        ChatRoom newRoom = ChatRoom.builder()
                                .sender(request.userId())
                                .receiver(request.receiverId())
                                .build();
                        return chatRoomRepository.save(newRoom);
                    });
        } else {
            chatRoom = validateChatRoom(request.roomId());
        }

        // 메시지 DB 저장
        Message message = Message.builder()
                .roomId(chatRoom.getRoomId())
                .senderId(request.userId())
                .content(request.content())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        messageRepository.save(message);

        // 클라이언트로 반환할 응답
        return MessageResponse.builder()
                .type(MessageType.CHAT)
                .content(request.content())
                .sessionId(sessionId)
                .nickname(nickname)
                .roomId(chatRoom.getRoomId())
                .build();
    }

    /**
     * 채팅 메시지 목록 조회
     * 1) roomId로 채팅방 유효성 확인
     * 2) MongoDB에서 메시지 페이징 조회
     * 3) ChatResponseDto 형태로 반환
     * @param authorizationHeader
     * @param roomId
     * @param size
     * @param page
     * @return
     */
    @Override
    public ChatResponseDto getMessages(String authorizationHeader, Long roomId, int size, int page) {
        ChatRoom chatRoom = validateChatRoom(roomId);

        Page<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtDesc(
                roomId, PageRequest.of(page, size)
        );

        // Message -> MessageRequest 형태로 변환 (receiverId 추론)
        List<MessageRequest> chatList = messages.getContent().stream()
                .map(m -> MessageRequest.builder()
                        .content(m.getContent())
                        .roomId(m.getRoomId())
                        .userId(m.getSenderId())
                        .receiverId(extractReceiverId(chatRoom, m.getSenderId()))
                        .build())
                .collect(Collectors.toList());

        // 페이징 정보
        ChatPageResponseDto pageableDto = ChatPageResponseDto.builder()
                .size(size)
                .page(page)
                .totalPages(messages.getTotalPages())
                .totalElements(messages.getTotalElements())
                .build();

        // 예시로 roomActive, nickname 정도만
        ChatUserResponseDto userDto = new ChatUserResponseDto("사용자 닉네임", chatRoom.getRoomActive());

        return ChatResponseDto.builder()
                .user(userDto)
                .pageableDto(pageableDto)
                .chatList(chatList)
                .build();
    }

    /**
     * roomId로 채팅방 유효성 검사
     * - 존재하는지
     * - 활성화된 방인지
     * @param roomId
     * @return
     */
    private ChatRoom validateChatRoom(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new BaseException(ChatExceptionType.CHAT_ROOM_NOT_FOUND));

        if (!chatRoom.getRoomActive()) {
            throw new BaseException(ChatExceptionType.ALREADY_DEACTIVATION_ROOM);
        }
        return chatRoom;
    }

    /**
     * senderId, receiverId 유효성 검사
     * - 동일 아이디인지(자기 자신에게 보내는 경우)
     * - userFeignClient.isUserValid(...) 로 존재 여부 확인
     * @param senderId
     * @param receiverId
     */
    private void validateUsers(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new BaseException(ChatExceptionType.INVALID_CHAT_USER);
        }
        boolean senderValid = userFeignClient.isUserValid(senderId);
        boolean receiverValid = userFeignClient.isUserValid(receiverId);

        if (!senderValid || !receiverValid) {
            throw new BaseException(ChatExceptionType.INVALID_USER_INFO);
        }
    }

    /**
     * 메시지가 DB에서 꺼내질 때 receiverId를 추론하는 메서드
     * (현재 1:1 구조이므로 senderId와 반대되는 사용자)
     */
    private Long extractReceiverId(ChatRoom chatRoom, Long senderId) {
        return (chatRoom.getSender().equals(senderId))
                ? chatRoom.getReceiver()
                : chatRoom.getSender();
    }
}