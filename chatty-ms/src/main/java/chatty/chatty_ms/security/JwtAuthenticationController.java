package chatty.chatty_ms.security;

import com.nimbusds.jose.JOSEException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;


    public JwtAuthenticationController(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws JOSEException {
        try {

            //fetch user by username
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            //validate login credentials
            if(!userDetailsService.validateCredentials(loginRequest.getUsername(), loginRequest.getPassword())){
                throw new BadCredentialsException("Invalid password");
            }

            // generate token with username
            String jwtToken = jwtTokenProvider.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}