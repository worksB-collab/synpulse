package com.example.demo.account;

import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AccountDaoTest {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

    @Test
    void findByUserIdSuccess() {
        final CustomUserDetails user = new CustomUserDetails("id", "username", "password");
        userDao.save(user);
        userDao.flush();
        final Account account = new Account(user);
        accountDao.save(account);

        final Optional<List<Account>> result = accountDao.findByUserId("id");

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }

    @Test
    void findByUserIdNotFound() {
        final Optional<List<Account>> result = accountDao.findByUserId("testUserId");

        assertTrue(result.isPresent());
        assertEquals(0, result.get().size());
    }
}
