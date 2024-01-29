package com.example.demo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void findByUsernameSuccess() {
        final String username = "testUsername";
        final CustomUserDetails userDetails = new CustomUserDetails("userId", username, "password"); // Adjust as needed
        userDao.save(userDetails);

        final Optional<CustomUserDetails> foundUser = userDao.findByUsername(username);

        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals(userDetails, foundUser.get(), "Found user should match the saved user");
    }

    @Test
    void findByUsernameNotFound() {
        final String username = "nonexistentUser";

        final Optional<CustomUserDetails> foundUser = userDao.findByUsername(username);

        assertFalse(foundUser.isPresent(), "User should not be found");
    }
}
