package project.chatservice.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import project.chatservice.domain.dto.request.MessageRequest;
import project.chatservice.domain.dto.response.MessageResponse;
import project.chatservice.domain.service.ChatProducer;
import project.chatservice.domain.service.ChatService;

@Tag(name = "Chat", description = "채팅 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final ChatProducer chatProducer;

    @Operation(summary = "채팅 메시지 전송", description = "'topic' 을 구독한 모든 사용자에게 채팅 메시지를 전송합니다.")
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public MessageResponse sendChatMessage(MessageRequest request, SimpMessageHeaderAccessor accessor) {
        log.info("sendChatMessage: {}", request);
        chatProducer.sendChatMessage(request.toEntity());
        return chatService.processMessage(
                request, accessor.getSessionId(),
                (String) accessor.getSessionAttributes().get("nickname")
        );
    }

}