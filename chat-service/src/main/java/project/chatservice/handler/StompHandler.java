package project.chatservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import project.globalservice.jwt.JwtProvider;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {
    private final JwtProvider jwtProvider;

    /**
     * WebSocket 메세지가 클라이언트와 서버 간에 전송되기 전에 가로채어 JWT인증을 처리
     * WebSocket 연결 과정에서 STOMP 헤더를 분석하여 토큰 유효성을 검사
     * 일단 HTTP API와 통신 방식이 다르기 때문에 security에서 처리하는 단방향이 아닌,
     * 양방향 통신 및 상태 유지 연결을 위해 JWT 인증을 가로채어 처리
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        log.info("message: {}", message);
        log.info("header: {}", message.getHeaders());
        log.info("token: {}", accessor.getNativeHeader("Authorization"));

        if(StompCommand.CONNECT.equals(accessor.getCommand())) {
            if (!jwtProvider.validateToken(accessor.getFirstNativeHeader("Authorization"))) {
                throw new IllegalArgumentException();
            }
        }
        return message;
    }
}
