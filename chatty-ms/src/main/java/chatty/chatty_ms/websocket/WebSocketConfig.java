package chatty.chatty_ms.websocket;

import chatty.chatty_ms.producer.MessageProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageProducer messageProducer;
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    public WebSocketConfig(MessageProducer messageProducer, JwtHandshakeInterceptor jwtHandshakeInterceptor) {
        this.messageProducer = messageProducer;
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/ws/joinchat/{chatId}")
                .setAllowedOrigins("*")
                .addInterceptors(jwtHandshakeInterceptor);;
    }

    @Bean
    public MessageWebSocketHandler webSocketHandler() {
        return new MessageWebSocketHandler(messageProducer);
    }

}



