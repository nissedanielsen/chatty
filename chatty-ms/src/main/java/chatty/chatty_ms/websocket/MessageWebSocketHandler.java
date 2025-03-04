package chatty.chatty_ms.websocket;

import chatty.chatty_ms.model.Message;
import chatty.chatty_ms.producer.MessageProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    private final MessageProducer messageProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Stores active WebSocket sessions mapped by chatId
    @Getter
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public MessageWebSocketHandler(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        Message chatMessage = objectMapper.readValue(message.getPayload(), Message.class);
        System.out.println("Received in WebSocket: " + chatMessage);

        // Send message to Kafka
        messageProducer.sendMessage(chatMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        System.out.println("New WebSocket connection: " + session.getId());
        String chatId = extractChatId(session);

        // Store the session by chatId
        sessions.put(session.getId(), session);
        session.getAttributes().put("chatId", chatId);

        //TODO: handle initial message in client
 //       session.sendMessage(new TextMessage("Connected to chat: " + chatId));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("WebSocket disconnected: " + session.getId());
        sessions.remove(session.getId());
    }

    private String extractChatId(WebSocketSession session) {
        String uri = session.getUri().toString();
        String path = uri.substring(uri.lastIndexOf("/") + 1);
        return path.contains("?") ? path.substring(0, path.indexOf("?")) : path;
    }

}
