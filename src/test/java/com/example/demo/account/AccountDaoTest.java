package com.example.demo.account;

import com.example.demo.user.CustomUserDetails;
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

    @Test
    void findByUserId() {
        CustomUserDetails user = new CustomUserDetails(); // Properly create a CustomUserDetails instance
        user.setUserId("testUserId"); // Assuming CustomUserDetails has a setUserId method

        Account account = new Account(user);
        accountDao.save(account);

        Optional<List<Account>> result = accountDao.findByUserId("testUserId");
        assertTrue(result.isPresent(), "Result should be present");
        assertEquals(1, result.get().size(), "Should find one account");
        assertEquals(account, result.get().get(0), "The account should match the one saved");
    }
}
