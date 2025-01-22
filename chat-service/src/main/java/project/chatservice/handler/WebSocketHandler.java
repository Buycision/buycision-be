package project.chatservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import project.chatservice.domain.dto.response.MessageResponse;
import project.chatservice.domain.entity.MessageType;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String nickname = accessor.getFirstNativeHeader("nickname");

        accessor.getSessionAttributes().put("nickname", nickname);

        String sessionId = accessor.getSessionId();
        log.info("[SessionConnected]: sessionId = " + sessionId + ", nickname = " + nickname);

        MessageResponse responseDto = MessageResponse.builder()
                .type(MessageType.SYSTEM)
                .content(nickname + "님이 참가하였습니다.")
                .build();

        messagingTemplate.convertAndSend("/topic/chat", responseDto);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String nickname = (String) accessor.getSessionAttributes().get("nickname");

        String sessionId = accessor.getSessionId();
        log.info("[SessionDisconnected]: sessionId = " + sessionId + ", nickname = " + nickname);

        MessageResponse responseDto = MessageResponse.builder()
                .type(MessageType.SYSTEM)
                .content(nickname + "님이 나갔습니다.")
                .build();

        messagingTemplate.convertAndSend("/topic/chat", responseDto);
    }
}