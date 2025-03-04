package chatty.chatty_ms.security;

import com.nimbusds.jose.JOSEException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    public JwtAuthenticationController(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws JOSEException {
        try {

            //fetch user by username
            User user = userService.loadUserByUsername(loginRequest.getUsername());

            //validate login credentials
            if(!userService.validateCredentials(loginRequest.getUsername(), loginRequest.getPassword())){
                throw new BadCredentialsException("Invalid password");
            }

            // generate token with username
            String jwtToken = jwtTokenProvider.generateToken(user.getUsername());

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}