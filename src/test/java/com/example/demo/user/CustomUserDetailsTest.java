package com.example.demo.user;

import org.junit.jupiter.api.Test;

import static com.example.demo.user.UserOm.newUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomUserDetailsTest {

    @Test
    void userInit() {
        final String userId = "userId";
        final String username = "username";
        final String password = "password";

        final CustomUserDetails userDetails = newUser(userId, username, password);

        assertEquals(userId, userDetails.getUserId());
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
    }

}
