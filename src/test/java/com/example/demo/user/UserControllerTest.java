package com.example.demo.user;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    @Tested
    private UserController userController;

    @Injectable
    private UserService userService;

    @Test
    void loginSuccessful() {
        final UserRequest userRequest = new UserRequest("username", "password");
        final ResponseEntity<?> expectedResponse = ResponseEntity.ok("Login successful");

        new Expectations() {{
            userService.login(userRequest.getUsername(), userRequest.getPassword());
            result = expectedResponse;
        }};

        final ResponseEntity<?> response = userController.login(userRequest);

        assertEquals(expectedResponse, response);
    }

    @Test
    void loginUnsuccessful() {
        final UserRequest userRequest = new UserRequest("username", "password");
        final ResponseEntity<?> expectedResponse = ResponseEntity.status(401).body("Login failed");

        new Expectations() {{
            userService.login(userRequest.getUsername(), userRequest.getPassword());
            result = expectedResponse;
        }};

        final ResponseEntity<?> response = userController.login(userRequest);

        assertEquals(expectedResponse, response);
    }
}
