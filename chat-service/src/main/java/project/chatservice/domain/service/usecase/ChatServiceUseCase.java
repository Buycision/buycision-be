package project.chatservice.domain.service.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.chatservice.domain.dto.UserFeignClient;
import project.chatservice.domain.dto.request.MessageRequestDto;
import project.chatservice.domain.dto.response.ChatPageResponseDto;
import project.chatservice.domain.dto.response.ChatResponseDto;
import project.chatservice.domain.dto.response.ChatUserResponseDto;
import project.chatservice.domain.entity.ChatRoom;
import project.chatservice.domain.entity.Message;
import project.chatservice.domain.repository.ChatRoomRepository;
import project.chatservice.domain.repository.MessageRepository;
import project.chatservice.domain.service.ChatService;
//import project.gatewayservice.auth.service.JwtProvider;

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
    private final UserFeignClient userFeignClient;

    /**
     * 클라이언트에서 채팅 메시지 전송 시 호출되는 메서드
     * 1) roomId가 있으면 해당 방 검증 후 메시지 생성
     * 2) roomId가 없으면 (senderId, receiverId)로 기존 활성 방 검색 후 없으면 새로 생성
     * 3) DB에 Message 엔티티 저장
     */
    @Override
    public void handleMessage(MessageRequestDto chatMessageDto) {
        // roomId 유무에 따라 분기
        ChatRoom chatRoom;
        if (chatMessageDto.roomId() != null) {
            chatRoom = validateChatRoom(chatMessageDto.roomId());
            // 참여자 검증 로직 (sender/receiver 중 하나라도 일치해야 한다는 식)
            if (!isParticipant(chatRoom, chatMessageDto.userId())) {
                throw new IllegalArgumentException("채팅방 참여자가 아닙니다. roomId=" + chatRoom.getRoomId());
            }
        } else {
            // senderId, receiverId가 유효한지 검사
            validateUsers(chatMessageDto.userId(), chatMessageDto.receiverId());
            // 활성화된 채팅방이 있는지 확인
            Optional<ChatRoom> activeRoom = chatRoomRepository.findActiveChatRoom(chatMessageDto.userId(), chatMessageDto.receiverId());
            if (activeRoom.isPresent()) {
                chatRoom = activeRoom.get();
            } else {
                // 없으면 새로 생성
                ChatRoom newRoom = ChatRoom.builder()
                        .sender(chatMessageDto.userId())
                        .receiver(chatMessageDto.receiverId())
                        .build();
                chatRoomRepository.save(newRoom);
                chatRoom = newRoom;
            }
        }

        // 메시지 엔티티 생성 후 저장
        Message message = Message.builder()
                .roomId(chatRoom.getRoomId())
                .senderId(chatMessageDto.userId())
                .content(chatMessageDto.content())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        messageRepository.save(message);
        log.info("채팅 메시지 저장 완료: roomId={}, userId={}, content={}",
                message.getRoomId(), message.getSenderId(), message.getContent());
    }

    /**
     * 채팅 메시지 조회
     * 1) roomId로 채팅방 찾기
     * 2) 채팅방이 존재하고 활성 상태인지 확인
     * 3) 메시지를 페이징 처리하여 가져온 뒤 ChatResponseDto 형태로 반환
     */
    @Override
    public ChatResponseDto getMessages(String authorizationHeader, Long roomId, int size, int page) {
        ChatRoom chatRoom = validateChatRoom(roomId);

        // 실제로는 authorizationHeader에서 userId를 뽑아내어
        // 해당 채팅방에 접근 권한이 있는지 확인할 수 있음
        // ex) Long currentUserId = jwtService.getUserIdFromHeader(authorizationHeader);
        // if (!isParticipant(chatRoom, currentUserId)) { throw new NoPermissionException(); }

        Page<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtDesc(roomId, PageRequest.of(page, size));

        // 메시지를 MessageRequestDto(혹은 별도 ResponseDto)로 변환
        List<MessageRequestDto> chatList = messages.getContent().stream()
                .map(m -> MessageRequestDto.builder()
                        .content(m.getContent())
                        .roomId(m.getRoomId())
                        .userId(m.getSenderId())
                        // receiverId는 DB 구조나 요구사항에 따라 결정
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

        // 예시로 사용자(대화 상대) 정보를 담는 객체
        // 실제로는 FeignClient로 nickname 등을 조회해도 됨
        ChatUserResponseDto userDto = new ChatUserResponseDto("사용자 닉네임", chatRoom.getRoomActive());

        return ChatResponseDto.builder()
                .user(userDto)
                .pageableDto(pageableDto)
                .chatList(chatList)
                .build();
    }

    /**
     * roomId로 채팅방 유효성 검사
     * 1) DB에 채팅방 존재 여부
     * 2) 활성 상태인지
     */
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

    /**
     * senderId, receiverId 모두 유효한지 검사
     */
    private void validateUsers(Long senderId, Long receiverId) {
        if (!userFeignClient.isUserValid(senderId) || !userFeignClient.isUserValid(receiverId)) {
            throw new IllegalArgumentException("잘못된 사용자 정보입니다. senderId="
                    + senderId + ", receiverId=" + receiverId);
        }
    }

    /**
     * 특정 ChatRoom에 userId가 참여자인지 확인
     * (예: senderId == userId or receiverId == userId)
     */
    private boolean isParticipant(ChatRoom chatRoom, Long userId) {
        return chatRoom.getSender().equals(userId) || chatRoom.getReceiver().equals(userId);
    }

    /**
     * 메시지 목록을 뿌릴 때 receiverId를 구하는 예시 메서드
     * (만약 메시지 테이블에 receiverId가 별도로 저장되지 않는다면)
     */
    private Long extractReceiverId(ChatRoom chatRoom, Long senderId) {
        // senderId가 chatRoom의 sender와 같다면 receiver 반환
        // 다르면 sender 반환
        // (실제로는 훨씬 복잡할 수 있으니 필요에 맞게 작성)
        if (chatRoom.getSender().equals(senderId)) {
            return chatRoom.getReceiver();
        } else {
            return chatRoom.getSender();
        }
    }
}