package org.beatrice.dgtuProject.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    private final String email = "test@mail.com"; // email for testing
    private String token;


    // Initialize the JwtUtil and regenerate the token before each test
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
    } // check that token is valid (passed token is valid)

    @Test
    void testValidateTokenInvalid() {
        assertFalse(jwtUtil.validateToken("invalid token"));
    } // check that token is invalid (passed token is invalid)


    // Generates an expired token and checks that it is expired
    @Test
    void testValidateTokenExpired() {
        String expiredToken = jwtUtil.generateToken(email, -1);
        assertFalse(jwtUtil.validateToken(expiredToken));
    }

    @Test
    void testGetEmailFromToken() {
        assertEquals(email, jwtUtil.getEmailFromToken(token));
    } // tries to extract email from token and check that it is the same as the email used to generate the token

    @Test
    void testValidateTokenNull() {
        assertFalse(jwtUtil.validateToken(null));
    } // check that null token is invalid

    @Test
    void testValidateTokenEmpty() {
        assertFalse(jwtUtil.validateToken(""));
    } // check that empty token is invalid


    // Checks that a malformed token is invalid
    @Test
    void testValidateTokenInvalidFormat() {
        String malformedToken = "malformed.token";
        assertFalse(jwtUtil.validateToken(malformedToken));
    }

}
