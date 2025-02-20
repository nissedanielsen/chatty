package chatty.chatty_ms.producer;

import chatty.chatty_ms.model.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Message message) {
        System.out.println("Sending message to Kafka: " + message);
        kafkaTemplate.send("chat-messages", message.getChatId(), message);
    }
}
