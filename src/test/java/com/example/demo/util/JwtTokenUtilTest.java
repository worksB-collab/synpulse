package com.example.demo.util;

import com.example.demo.user.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static com.example.demo.user.UserOm.newUser;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "secret");
        ReflectionTestUtils.setField(jwtTokenUtil, "expiration", 3600000L);
    }

    @Test
    void generateTokenTest() {
        final CustomUserDetails user = newUser();

        String token = jwtTokenUtil.generateToken(user);

        assertNotNull(token);
    }

    @Test
    void getUserIdFromTokenTest() {
        final CustomUserDetails user = newUser();

        final String token = jwtTokenUtil.generateToken(user);
        final String userId = jwtTokenUtil.getUserIdFromToken(token);

        assertEquals(user.getUserId(), userId);
    }

    @Test
    void validateTokenTest() {
        final CustomUserDetails user = newUser();

        final String token = jwtTokenUtil.generateToken(user);
        final boolean isValid = jwtTokenUtil.validateToken(token, user);

        assertTrue(isValid);
    }

    @Test
    void isTokenExpiredTest() {
        final CustomUserDetails user = newUser();

        final String token = jwtTokenUtil.generateToken(user);
        final boolean isExpired = jwtTokenUtil.isTokenExpired(token);

        assertFalse(isExpired);
    }
}
