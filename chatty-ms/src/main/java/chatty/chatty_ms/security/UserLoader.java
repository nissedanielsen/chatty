package chatty.chatty_ms.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserLoader(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {

        //TODO: move logic to init-container

        System.out.println("Loading users in commandLineRunner");

        // Pre-populate 10 users in the database
        for (int i = 0; i < 10; i++) {
            String username = "user" + i;
            if (userRepository.findByUsername(username) == null) {
                User user = User.builder()
                        .username(username)
                        .password("password")
                        .role("ROLE_USER")
                        .build();

                User savedUser = userService.saveUser(user);
            }
        }
    }
}
