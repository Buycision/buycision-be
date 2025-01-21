package project.chatservice.domain.service;

import project.chatservice.domain.dto.request.MessageRequestDto;
import project.chatservice.domain.dto.response.ChatResponseDto;

public interface ChatService {

    void handleMessage(MessageRequestDto chatMessageDto);

    ChatResponseDto getMessages(String authorizationHeader, Long roomId, int size, int page);
}
