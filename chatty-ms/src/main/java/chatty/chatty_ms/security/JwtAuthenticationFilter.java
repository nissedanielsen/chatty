package chatty.chatty_ms.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationService jwtAuthenticationService;

    public JwtAuthenticationFilter(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String token = jwtAuthenticationService.extractJwtFromRequest(request);
        if (token != null) {
            jwtAuthenticationService.authenticateUser(token);
        }

        filterChain.doFilter(request, response);
    }

}