package com.example.demo.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRequestTest {

    @Test
    void userRequestProperties() {
        final String username = "testUsername";
        final String password = "testPassword";

        final UserRequest userRequest = new UserRequest(username, password);

        assertEquals(username, userRequest.getUsername(), "Username should be correctly set and retrieved");
        assertEquals(password, userRequest.getPassword(), "Password should be correctly set and retrieved");
    }
}
