package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate/login")
    public ResponseEntity<?> login(@RequestBody final UserRequest userRequest) {
        return userService.login(userRequest.getUsername(), userRequest.getPassword());
    }

}
