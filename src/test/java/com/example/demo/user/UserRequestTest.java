package com.example.demo.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRequestTest {

    @Test
    void userRequestProperties() {
        final String username = "username";
        final String password = "password";

        final UserRequest userRequest = new UserRequest(username, password);

        assertEquals(username, userRequest.getUsername());
        assertEquals(password, userRequest.getPassword());
    }
}
