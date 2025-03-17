package org.beatrice.hui.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    private final String email = "test@mail.com";
    private String token;


    @BeforeEach
    void setup() {
        jwtUtil.init();
        token = jwtUtil.generateToken(email);
    }

    @Test
    void testGenerateToken() {
        assertNotNull(token); // check that token exists
    }

    @Test
    void testValidateTokenValid() {
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testValidateTokenInvalid() {
        assertFalse(jwtUtil.validateToken("invalid token"));
    }

    @Test
    void testValidateTokenExpired() {
        String expiredToken = jwtUtil.generateToken(email, -1);
        assertFalse(jwtUtil.validateToken(expiredToken));
    }

    @Test
    void testGetEmailFromToken() {
        assertEquals(email, jwtUtil.getEmailFromToken(token));
    }

    @Test
    void testValidateTokenNull() {
        assertFalse(jwtUtil.validateToken(null));
    }

    @Test
    void testValidateTokenEmpty() {
        assertFalse(jwtUtil.validateToken(""));
    }

    @Test
    void testValidateTokenInvalidFormat() {
        String malformedToken = "malformed.token";
        assertFalse(jwtUtil.validateToken(malformedToken));
    }

}
