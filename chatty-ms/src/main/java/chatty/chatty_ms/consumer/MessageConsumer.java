package chatty.chatty_ms.consumer;

import chatty.chatty_ms.websocket.MessageWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@Service
public class MessageConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "chat-messages", groupId = "chat-group")
    public void listen(ConsumerRecord<String, String> record) throws IOException {
        String chatId = record.key();
        System.out.println("Received Kafka message for chatId: " + chatId);

        // Retrieve active WebSocket sessions
        Map<String, WebSocketSession> sessions = MessageWebSocketHandler.getSessions();

        for (WebSocketSession session : sessions.values()) {
            String sessionChatId = (String) session.getAttributes().get("chatId");
            if (sessionChatId.equals(chatId)) {
                session.sendMessage(new TextMessage(record.value()));
            }
        }
    }
}
