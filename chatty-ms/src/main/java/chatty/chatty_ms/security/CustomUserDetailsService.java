package chatty.chatty_ms.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> users = new HashMap<>();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomUserDetailsService() {

        // pre-populate 10 user to users, acting as db
        for (int i = 0; i < 10; i++) {
            String username = "user" + i;
            users.put(username, User.builder()
                    .username(username)
                    .password(passwordEncoder.encode("password"))
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                    .build());
        }

    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return user;
    }

    public boolean validateCredentials(String username, String password) {
        UserDetails user = users.get(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}