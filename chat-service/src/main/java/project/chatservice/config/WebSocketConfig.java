package project.chatservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import project.chatservice.handler.StompHandler;

@Configuration
@EnableWebSocketMessageBroker // WebSocket 메세지 브로커를 활성화, STOMP 기반 메세지 송수신을 지원
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 프로세스의 흐름
     * 1. 클라이언트가 /chat 엔드포인트를 통해 WebSocket 연결을 요청
     * 2. STOMP 프로토콜을 통해 연결 요청 시 Authorization 헤더에 jwt토큰 포함
     * 3. StompHandler가 preSend 메서드에서 메세지를 가로채어 토큰을 추출하고 검증
     */
    private final StompHandler stompHandler;

    @Value("${websocket.allowedOrigins}")
    private String allowedOrigins;

    /**
     * STOMP 엔드포인트 등록
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOrigins(allowedOrigins)
                .withSockJS();
    }

    /**
     * 메시지 브로커 설정
     * 클라이언트가 서버로 메세지를 보낼 때의 경로가 /pub
     * 서버가 클러이언트에게 메세지를 전달할 때 /sub
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.setApplicationDestinationPrefixes("/pub");

        registry.enableSimpleBroker("/sub");
    }

    /**
     * 아웃바운드 메세지 검증
     * 클라이언트에서 서버로 나가는 메세지를 처리하기 위함
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {

        registration.interceptors(stompHandler);
    }

}
