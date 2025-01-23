package project.chatservice.domain.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.chatservice.domain.dto.request.MessageRequest;
import project.chatservice.domain.dto.response.ChatResponseDto;
import project.chatservice.domain.dto.response.MessageResponse;
import project.chatservice.domain.service.ChatService;
import project.globalservice.response.BaseResponse;

@Tag(name = "Chat", description = "채팅 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public MessageResponse sendChatMessage(MessageRequest request, SimpMessageHeaderAccessor accessor) {
        return chatService.processMessage(request, accessor.getSessionId(), (String) accessor.getSessionAttributes().get("nickname"));
    }

}