package chatty.chatty_ms.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final MessageWebSocketHandler messageWebSocketHandler;

    public WebSocketConfig( JwtHandshakeInterceptor jwtHandshakeInterceptor, MessageWebSocketHandler messageWebSocketHandler) {
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
        this.messageWebSocketHandler = messageWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageWebSocketHandler, "/ws/joinchat/{chatId}")
                .setAllowedOrigins("*")
                .addInterceptors(jwtHandshakeInterceptor);;
    }

}



