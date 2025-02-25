package chatty.chatty_ms.consumer;

import chatty.chatty_ms.model.HuggingFaceRequest;
import chatty.chatty_ms.model.Message;
import chatty.chatty_ms.service.HuggingFaceService;
import chatty.chatty_ms.service.MessageService;
import chatty.chatty_ms.websocket.MessageWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class MessageConsumer {

    private final HuggingFaceService huggingFaceService;
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    public MessageConsumer(HuggingFaceService huggingFaceService, MessageService messageService, ObjectMapper objectMapper) {
        this.huggingFaceService = huggingFaceService;
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "chat-messages", groupId = "chat-group")
    public void listen(ConsumerRecord<String, Message> record) throws IOException {
        String chatId = record.key();
        Message message = record.value();
        String messageJson = objectMapper.writeValueAsString(message);

        // send message to clients
        broadcastMessageToClients(chatId, messageJson);

        if (message.getContent().contains("@bot")) {
            handleBotResponse(chatId, message);
        }

    }

    private void broadcastMessageToClients(String chatId, String message) throws IOException {
        // Retrieve active WebSocket sessions
        System.out.println("broadcasting message: " + message);
        Map<String, WebSocketSession> sessions = MessageWebSocketHandler.getSessions();

        for (WebSocketSession session : sessions.values()) {

            String sessionChatId = (String) session.getAttributes().get("chatId");
            if (sessionChatId.equals(chatId)) {
                    session.sendMessage(new TextMessage(message));
            }
        }
    }

    private void handleBotResponse(String chatId, Message message) throws IOException {
        String contentForBot = message.getContent().substring(4).trim();
        String contentFromBot = huggingFaceService.getResponseFromModel(new HuggingFaceRequest(contentForBot)).getResponse();

        Message newMessage = Message.builder()
                .chatId(chatId)
                .senderId("@bot")
                .content(contentFromBot)
                .timestamp(LocalDateTime.now().toString())
                .build();

        //TODO: Fix issue with save of bot-message

        // make sure to use message received from repository since it contains timestamp and generated ID
        // Message botMessage = messageService.saveMessage(newMessage);

        String botMessageJson = objectMapper.writeValueAsString(newMessage);

        // send the bot's response to all clients
        broadcastMessageToClients(chatId, botMessageJson);
    }
}