package com.carsell.platform.security;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// We supply test properties directly via the annotation for convenience.
// The jwt.secret must be a valid Base64-encoded string that is at least 512 bits (64 bytes) long for HS512.
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "jwt.secret=U29tZUJhc2U2NEVuY29kZWRTZWNyZXRLZXlGb3JIUzUxMjRDb21wYW55MTIzNDU2Nzg5MA==",
                "jwt.expirationMs=86400000"
        })
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    void testGenerateAndValidateToken() {
        String username = "testUser";
        String token = jwtUtil.generateJwtToken(username);
        assertNotNull(token, "Token should not be null");

        // Validate token
        boolean isValid = jwtUtil.validateJwtToken(token);
        assertTrue(isValid, "Token should be valid");

        // Extract username from token
        String extractedUsername = jwtUtil.getUsernameFromJwtToken(token);
        assertEquals(username, extractedUsername, "Extracted username should match the original");
    }

    @Test
    void testInvalidToken() {
        String invalidToken = "invalid.token.value";
        boolean isValid = jwtUtil.validateJwtToken(invalidToken);
        assertFalse(isValid, "Invalid token should not be valid");
    }
}
