package com.example.demo.account;

import com.example.demo.user.CustomUserDetails;
import org.junit.jupiter.api.Test;

import static com.example.demo.account.AccountOm.newAccount;
import static com.example.demo.user.UserOm.newUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class AccountTest {

    @Test
    void constructor() {
        final CustomUserDetails user = newUser();
        final Account account = newAccount(user);

        assertSame(user, account.getUser());
    }

    @Test
    void getAndSetId() {
        final Long expectedId = 1L;
        final Account account = newAccount(expectedId);

        assertEquals(expectedId, account.getId());
    }

    @Test
    void getAndSetUser() {
        final CustomUserDetails user = newUser();
        final Account account = newAccount(user);

        assertSame(user, account.getUser());
    }
}
