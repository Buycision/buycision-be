package project.chatservice.domain.service;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import project.chatservice.domain.entity.Message;

@Service
@RequiredArgsConstructor
public class ChatConsumer {

    @KafkaListener(topics = "buy-chat", groupId = "chat-group")
    public void consume(Message message) {
        System.out.println("Received message: " + message);
        // WebSocket을 통해 프론트엔드로 전달 가능
    }
}