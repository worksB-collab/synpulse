package com.example.demo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.example.demo.user.UserOm.newUser;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void findByUsernameSuccess() {
        final String username = "username";
        final CustomUserDetails user = newUser(username, "password");
        userDao.save(user);

        final Optional<CustomUserDetails> foundUser = userDao.findByUsername(username);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    void findByUsernameNotFound() {
        final String username = "nonexistentUser";

        final Optional<CustomUserDetails> foundUser = userDao.findByUsername(username);

        assertFalse(foundUser.isPresent());
    }
}
