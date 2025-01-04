package project.chatservice.domain.service.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.chatservice.domain.dto.request.MessageRequestDto;
import project.chatservice.domain.dto.response.ChatResponseDto;
import project.chatservice.domain.service.ChatService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceUseCase implements ChatService {

    @Override
    public void handleMessage(MessageRequestDto chatMessageDto) {

    }

    @Override
    public ChatResponseDto getMesages(String authorizationHeader, Long roomId, int size, int page) {
        return null;
    }
}
