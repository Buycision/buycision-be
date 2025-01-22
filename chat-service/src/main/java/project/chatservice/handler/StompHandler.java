package project.chatservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
            log.info("Received WebSocket message: {}", message.getHeaders());
            // JWT 인증 로직 추가 가능
        } catch (Exception e) {
            log.error("WebSocket 핸들링 중 오류 발생: ", e);
            throw e;
        }
        return message;
    }
}