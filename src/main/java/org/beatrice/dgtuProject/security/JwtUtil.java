package org.beatrice.dgtuProject.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey SECRET_KEY;

    @Value("${jwt.expiration:3600000}") // Default: 1 hour (3_600_000 ms)
    private long expiration;

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT key is too short or missing: " + secret);
        }
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * Generates a JWT token with a predefined expiration time.
     *
     * @param email the email address to be used as the subject of the token.
     * @return a string representing the generated JWT token.
     * @throws IllegalArgumentException if the token cannot be generated due to a missing or invalid secret key.
     */
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY)
                .compact();
    }


    /**
     * Generates a JWT token with a custom expiration time defined by an offset in milliseconds.
     * Used only for tests
     *
     * <p>If the offset value is negative, the token will be considered expired.</p>
     *
     * @param email                  the email address to be used as the subject of the token.
     * @param expirationOffsetMillis the time offset in milliseconds for the token expiration relative to the current time.
     *                               Positive values indicate a future expiration time, while negative values represent an expired token.
     * @return a string representing the generated JWT token.
     * @throws IllegalArgumentException if the token cannot be generated due to a missing or invalid secret key.
     */
    public String generateToken(String email, long expirationOffsetMillis) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationOffsetMillis))
                .signWith(SECRET_KEY)
                .compact();
    }



    /**
     * @param token the token from which to extract claims
     * @return claims (payload) from the token
     * @throws IllegalArgumentException if the token is empty or null
     */
    public Claims getAllClaims(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is null or empty");
        }

        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates the given JWT token by verifying its signature and checking for any errors.
     *
     * <p>This method checks whether the token is null or empty. Then it attempts to parse the token's claims
     * and validate its signature using the provided secret key. If the token is valid, it returns true; otherwise,
     * it returns false.</p>
     *
     * @param token the JWT token to validate
     * @return true if the token is valid (correct signature and not expired), false otherwise
     */
    public boolean validateToken(String token) {
        if (token == null || token.isEmpty() || SECRET_KEY == null) {
            return false;
        }

        try {
            return getAllClaims(token).getExpiration().after(new Date());
        } catch (JwtException _) {
            return false;
        }
    }

    /**
     * Extracts the email stored in the token
     *
     * @param token the token from which to extract the email
     * @return the email extracted from the token
     * @throws IllegalArgumentException if the token is null or empty
     */
    public String getEmailFromToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is null or empty");
        }
        if (SECRET_KEY == null) {
            throw new IllegalStateException("JWT secret key is not initialized");
        }
        return getAllClaims(token).getSubject();
    }

    public String getTokenFromHeader(String header) {
        if (header == null || header.isEmpty()) {
            throw new IllegalArgumentException("Header is missing");
        }

        if (!header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid header format");
        }

        return header.substring(7);
    }
}
