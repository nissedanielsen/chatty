package chatty.chatty_ms.websocket;

import chatty.chatty_ms.model.Message;
import chatty.chatty_ms.producer.MessageProducer;
import chatty.chatty_ms.service.MessageService;
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
    private final MessageService messageService;

    // Stores active WebSocket sessions mapped by chatId
    @Getter
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public MessageWebSocketHandler(MessageProducer messageProducer,
                                   MessageService messageService) {
        this.messageProducer = messageProducer;
        this.messageService = messageService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        Message chatMessage = objectMapper.readValue(message.getPayload(), Message.class);
        System.out.println("Received in WebSocket: " + chatMessage);

        // Save in db
        messageService.saveMessage(chatMessage);

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
        session.sendMessage(new TextMessage("Connected to chat: " + chatId));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("WebSocket disconnected: " + session.getId());
        sessions.remove(session.getId());
    }

    private String extractChatId(WebSocketSession session) {
        String uri = session.getUri().toString();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

}
