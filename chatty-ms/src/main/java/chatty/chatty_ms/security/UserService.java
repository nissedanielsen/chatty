package chatty.chatty_ms.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;

    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return user;
    }

    public boolean validateCredentials(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public User saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {

            //encode password
            //salt+hash
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new RuntimeException("User already exists");
    }
}