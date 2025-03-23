package com.carsell.platform.security;

import com.carsell.platform.config.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;

@Component
public class JWTUtil {

    // Inject the secret and expiration from application.yml
//    @Value("${jwt.secret}")
//    private String jwtSecret; // Base64-encoded secret key

//    @Value("${jwt.expirationMs}")
//    private long jwtExpirationMs;



    private final JWTProperties jwtProperties;

    public JWTUtil(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Generates a JWT token using the modern Java time API.
     *
     * @param username the username to set as subject in the token.
     * @return the generated JWT token.
     */
    public String generateJwtToken(String username) {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMillis(jwtProperties.getExpirationMs()));

        // Use our helper method to convert Instant to java.util.Date internally.
        return Jwts.builder()
                .subject(username)
                .issuedAt(toDate(now))
                .expiration(toDate(expiry))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Retrieves the username from a given JWT token.
     *
     * @param token the JWT token.
     * @return the username (subject) contained in the token.
     */
    public String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Validates the given JWT token.
     *
     * @param token the JWT token.
     * @return true if valid; false otherwise.
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException ex) {
            // Optionally log the error here
            return false;
        }
    }

    // --- Private Helpers ---

    /**
     * Returns the signing key derived from the Base64-encoded secret.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Converts an Instant to java.util.Date.
     * This conversion is the only place where java.util.Date is used.
     *
     * @param instant the Instant to convert.
     * @return the corresponding java.util.Date.
     */
    private java.util.Date toDate(Instant instant) {
        return java.util.Date.from(instant);
    }

}
