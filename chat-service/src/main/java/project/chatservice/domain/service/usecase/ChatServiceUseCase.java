package project.chatservice.domain.service.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.chatservice.domain.dto.request.MessageRequest;
import project.chatservice.domain.dto.response.MessageResponse;
import project.chatservice.domain.entity.MessageType;
import project.chatservice.domain.repository.ChatRoomRepository;
import project.chatservice.domain.repository.MessageRepository;
import project.chatservice.domain.service.ChatService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceUseCase implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

    @Override
    public MessageResponse processMessage(MessageRequest request, String sessionId, String nickname) {
        return MessageResponse.builder()
                .type(MessageType.CHAT)
                .content(request.content())
                .sessionId(sessionId)
                .nickname(nickname)
                .build();
    }
}