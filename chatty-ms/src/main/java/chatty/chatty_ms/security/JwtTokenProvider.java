package chatty.chatty_ms.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "yCyRpKgVcCvLtQmXpBnMzAbWeFrTgUiOp4Px7L0No1M="; // 32 bytes for HS256

    private final long VALIDITY_IN_MILLISECONDS_1_DAY = 86_400_000L; // 1 day in ms
    private final long VALIDITY_IN_MILLISECONDS_20_SEC = 20_000L; // 10 sec in ms


    public String generateToken(String username) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + VALIDITY_IN_MILLISECONDS_1_DAY))
                .build();

        JWSSigner signer = new MACSigner(SECRET_KEY.getBytes());
        JWSObject jwsObject = new JWSObject(header, new Payload(claims.toJSONObject()));
        jwsObject.sign(signer);

        return jwsObject.serialize();
    }

    // check if token valid and has not been modified
    public boolean isTokenVerified(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

            return jwsObject.verify(verifier);
        } catch (Exception e) {
            return false;
        }
    }

    // check if token i expired
    public boolean isTokenExpired(String token) {
        try {
            JWTClaimsSet claims = extractClaims(token);
            Date expirationTime = claims.getExpirationTime();
            return expirationTime == null || expirationTime.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    // extract subject
    public String extractUsername(String token) {
        try {
            JWTClaimsSet claims = extractClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        return isTokenVerified(token) && !isTokenExpired(token);
    }


    private JWTClaimsSet extractClaims(String token) throws ParseException, JOSEException {
        JWSObject jwsObject = JWSObject.parse(token);
        return JWTClaimsSet.parse(jwsObject.getPayload().toString());
    }
}