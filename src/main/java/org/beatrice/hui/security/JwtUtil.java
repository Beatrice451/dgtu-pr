package org.beatrice.hui.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey SECRET_KEY;
    private final long EXPIRATION_DATE = 1000 * 60 * 60; // 1 hour

    @Value("${jwt.secret}")
    private String secret; // Переменная окружения или из application.properties

    @PostConstruct
    public void init() {
        System.out.println("we're in init() meth. secret=" + secret);
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT key is too short or missing: " + secret);
        }
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
        System.out.println("SECRET_KEY=" + SECRET_KEY);
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
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
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
        if (token == null || token.isEmpty()) {
            return false;
        }

        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (JwtException e) {
            System.out.println("Token error: " + e.getMessage());
        }
        return false;
    }


    /**
     * Extracts the email stored in the token
     *
     * @param token the token from which to extract the email
     * @return the email extracted from the token
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

}
