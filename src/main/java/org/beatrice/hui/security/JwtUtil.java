package org.beatrice.hui.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private static SecretKey SECRET_KEY;
    private static final long EXPIRATION_DATE = 1000 * 60 * 60; // 1 hour

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() {
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public static String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith()
        }
    }
}
