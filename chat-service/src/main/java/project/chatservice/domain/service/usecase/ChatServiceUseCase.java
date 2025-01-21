package project.chatservice.domain.service.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
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

//    private final JwtProvider jwtProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

    /**
     * 클라이언트에서 채팅 메시지 전송 시 호출되는 메서드
     * 1) DB에 Message 엔티티를 생성해 저장
     * 2) 필요하다면 Redis 등을 이용한 실시간 전송 기능 확장 가능
     */
    @Override
    public void handleMessage(MessageRequestDto chatMessageDto) {
        // 실제로는 JWT 토큰 인증 로직과 사용자/채팅방 검증 로직 등을 거쳐야 할 수 있음
        Message message = Message.builder()
                .roomId(chatMessageDto.roomId())
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
     * 1) 채팅방을 조회해 해당 채팅방의 활성화 여부 확인
     * 2) 메시지를 페이징 처리하여 가져온 뒤 ChatResponseDto 형태로 반환
     *
     * @param authorizationHeader
     * @param roomId
     * @param size
     * @param page
     * @return
     */
    @Override
    public ChatResponseDto getMessages(String authorizationHeader, Long roomId, int size, int page) {

        ChatRoom chatRoom = validateChatRoom(roomId);

        Page<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtDesc(chatRoom.getRoomId(), PageRequest.of(page, size));

        List<MessageRequestDto> chatList = messages.getContent().stream()
                .map(m -> MessageRequestDto.builder()
                        .content(m.getContent())
                        .roomId(m.getRoomId())
                        .userId(m.getSenderId())
                        .build())
                .collect(Collectors.toList());

        ChatPageResponseDto pageableDto = ChatPageResponseDto.builder()
                .size(size)
                .page(page)
                .totalPages(messages.getTotalPages())
                .totalElements(messages.getTotalElements())
                .build();

        // 사용자(대화 상대) 정보 예시로 사용자 닉네임으로 하드코딩
        ChatUserResponseDto userDto = new ChatUserResponseDto("사용자 닉네임", chatRoom.getRoomActive());


        return ChatResponseDto.builder()
                .user(userDto)
                .pageableDto(pageableDto)
                .chatList(chatList)
                .build();
    }

    /**
     * 채팅 메시지 조회
     * 1) 채팅방을 조회해 활성 여부 검증
     * 2) 메시지를 페이징 처리하여 ChatResponseDto 형태로 반환
     */
//    @Override
//    public ChatResponseDto getMesages(String authorizationHeader, Long roomId, int size, int page) {
//        // JWT 토큰에서 사용자 ID 파싱
//        // (실제 구현 시 토큰 형식에 따라 "Bearer " 제거 로직 등이 필요할 수 있음)
//        String token = authorizationHeader.substring(7);
//        if (!jwtProvider.validateToken(token)) {
//            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
//        }
//        String userIdString = jwtProvider.decodeToken(token);
//        Long userId = Long.parseLong(userIdString);
//
//        // 채팅방 유효성 검증
//        ChatRoom chatRoom = validateChatRoom(roomId, userId);
//
//        // 메시지 목록 페이징 조회 (생성일 내림차순)
//        Page<Message> messages = messageRepository.findByRoomIdOrderByCreatedAtDesc(
//                chatRoom.getRoomId(),
//                PageRequest.of(page, size)
//        );
//
//        // Message 엔티티를 MessageRequestDto 형태로 매핑 (요청 DTO를 재활용하는 구조)
//        List<MessageRequestDto> chatList = messages.getContent().stream()
//                .map(m -> MessageRequestDto.builder()
//                        .content(m.getContent())
//                        .roomId(m.getRoomId())
//                        .userId(m.getSenderId())
//                        .build())
//                .collect(Collectors.toList());
//
//        ChatPageResponseDto pageableDto = ChatPageResponseDto.builder()
//                .size(size)
//                .page(page)
//                .totalPages(messages.getTotalPages())
//                .totalElements(messages.getTotalElements())
//                .build();
//
//        // 사용자(대화 상대) 정보
//        // 실제로는 userId로 사용자 닉네임/상태 등을 조회한 뒤 매핑
//        ChatUserResponseDto userDto = new ChatUserResponseDto(
//                "사용자닉네임_" + userId, // 예시
//                chatRoom.getRoomActive()
//        );
//
//        return ChatResponseDto.builder()
//                .user(userDto)
//                .pageableDto(pageableDto)
//                .chatList(chatList)
//                .build();
//    }

    /**
     * 채팅방 유효성 검사
     * 1) roomId로 채팅방 찾기
     * 2) 채팅방이 존재하고 활성 상태인지 확인
     * 3) 사용자가 이 채팅방에 접근 가능(참여자)한지 확인하는 로직을 여기에 추가 가능
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

        // 실제로는 userId로 해당 채팅방에 접근 권한이 있는지 따져볼 수 있음
        // if (!isParticipating(chatRoom, userId)) { throw new NoPermissionException(); }

        return chatRoom;
    }
}