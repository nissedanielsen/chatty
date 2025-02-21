package chatty.chatty_ms.websocket;

import chatty.chatty_ms.producer.MessageProducer;
import chatty.chatty_ms.service.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageProducer messageProducer;
    private final MessageService messageService;

    public WebSocketConfig(MessageProducer messageProducer, MessageService messageService) {
        this.messageProducer = messageProducer;
        this.messageService = messageService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/ws/joinchat/{chatId}").setAllowedOrigins("*");
    }

    @Bean
    public MessageWebSocketHandler webSocketHandler() {
        return new MessageWebSocketHandler(messageProducer, messageService);
    }

}



