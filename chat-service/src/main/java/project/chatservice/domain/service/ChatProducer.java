package project.chatservice.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import project.chatservice.domain.entity.Message;

@Service
@RequiredArgsConstructor
public class ChatProducer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public void sendChatMessage(Message message) {
        kafkaTemplate.send("buy-chat", message);
    }
}
