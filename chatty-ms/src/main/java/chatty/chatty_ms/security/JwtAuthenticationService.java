package chatty.chatty_ms.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtAuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean authenticateUser(String token) {
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.extractUsername(token);
            if (username != null) {
                storeAuthentication(username);
                return true;
            }
        }
        return false;
    }

    public void storeAuthentication(String username) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
