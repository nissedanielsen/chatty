package chatty.chatty_ms.websocket;

import chatty.chatty_ms.security.JwtAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtAuthenticationService jwtAuthenticationService;

    public JwtHandshakeInterceptor(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        System.out.println("Handshake initiated");
        String token = getJwtFromQueryParam(request);

        if(jwtAuthenticationService.authenticateUser(token)) {
            System.out.println("Handshake successful");
            response.setStatusCode(HttpStatus.OK);
            return true;
        }

        System.out.println("Handshake failed");
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return false;
    }

    private String getJwtFromQueryParam(ServerHttpRequest request) {
        String query = request.getURI().getQuery();
        if (query != null && query.startsWith("token=")) {
            return query.substring(6);
        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("After handshake");
    }

}
