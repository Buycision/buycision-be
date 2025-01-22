package project.chatservice.domain.service;

import project.chatservice.domain.dto.request.MessageRequest;
import project.chatservice.domain.dto.response.MessageResponse;

public interface ChatService {

    MessageResponse handleMessage(MessageRequest request, String sessionId, String nickname);

}
