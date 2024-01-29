package com.example.demo.util;

import com.example.demo.user.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "testSecretKey");
        ReflectionTestUtils.setField(jwtTokenUtil, "expiration", 3600000L); // 1 hour for testing
    }

    @Test
    void generateTokenTest() {
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId("testUserId");

        String token = jwtTokenUtil.generateToken(userDetails);

        assertNotNull(token, "Token should not be null");
    }

    @Test
    void getUserIdFromTokenTest() {
        final CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId("testUserId");

        final String token = jwtTokenUtil.generateToken(userDetails);
        final String userId = jwtTokenUtil.getUserIdFromToken(token);

        assertEquals("testUserId", userId, "UserId should match the one in the token");
    }

    @Test
    void validateTokenTest() {
        final CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId("testUserId");

        final String token = jwtTokenUtil.generateToken(userDetails);
        final boolean isValid = jwtTokenUtil.validateToken(token, userDetails);

        assertTrue(isValid, "Token should be valid");
    }

    @Test
    void isTokenExpiredTest() {
        final CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId("testUserId");

        final String token = jwtTokenUtil.generateToken(userDetails);
        final boolean isExpired = jwtTokenUtil.isTokenExpired(token);

        assertFalse(isExpired, "Token should not be expired");
    }
}
