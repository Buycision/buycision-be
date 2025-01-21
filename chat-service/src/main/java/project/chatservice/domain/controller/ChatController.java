package project.chatservice.domain.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.chatservice.domain.dto.request.MessageRequestDto;
import project.chatservice.domain.dto.response.ChatResponseDto;
import project.chatservice.domain.service.ChatService;
import project.globalservice.response.BaseResponse;

@Tag(name="Chat", description = "채팅 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    // TODO : Webflux 로 migration?

    private final ChatService chatService;

    @MessageMapping
    public void handleChatMessage(MessageRequestDto chatMessageDto) {
        chatService.handleMessage(chatMessageDto);
    }

    @GetMapping
    public BaseResponse<ChatResponseDto> getChatMessages(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam Long roomId,
            @RequestParam int size,
            @RequestParam int page) {
        ChatResponseDto responseDto = chatService.getMesages(authorizationHeader, roomId, size, page);
        return new BaseResponse<>(responseDto);
    }

}
