package com.example.demo.account;

import com.example.demo.user.CustomUserDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class AccountTest {

    @Test
    void constructor() {
        CustomUserDetails userDetails = new CustomUserDetails();
        Account account = new Account(userDetails);

        assertSame(userDetails, account.getUser(), "The user should be the one set in the constructor");
    }

    @Test
    void getAndSetId() {
        Account account = new Account();
        Long expectedId = 123L;
        account.setId(expectedId);

        assertEquals(expectedId, account.getId(), "Getter and setter for id should work correctly");
    }

    @Test
    void getAndSetUser() {
        Account account = new Account();
        CustomUserDetails userDetails = new CustomUserDetails();
        account.setUser(userDetails);

        assertSame(userDetails, account.getUser(), "Getter and setter for user should work correctly");
    }
}
