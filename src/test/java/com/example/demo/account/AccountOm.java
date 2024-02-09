package com.example.demo.account;

import com.example.demo.user.CustomUserDetails;
import lombok.experimental.UtilityClass;

import static com.example.demo.user.UserOm.newUser;
import static org.apache.commons.lang3.RandomUtils.nextLong;


@UtilityClass
public class AccountOm {
    public static Account newAccountWithoutUser() {
        return new Account(null);
    }

    public static Account newAccount() {
        return newAccount(nextLong(), newUser());
    }

    public static Account newAccount(final CustomUserDetails user) {
        return newAccount(nextLong(), user);
    }

    public static Account newAccount(final Long accountId) {
        return newAccount(accountId, newUser());
    }

    public static Account newAccount(final Long accountId, final CustomUserDetails user) {
        return new Account(accountId, user);
    }
}
