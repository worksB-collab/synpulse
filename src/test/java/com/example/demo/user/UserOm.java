package com.example.demo.user;

import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class UserOm {

    public static CustomUserDetails newUser() {
        return newUser(randomAlphabetic(8), randomAlphabetic(8), randomAlphabetic(8));
    }

    public static CustomUserDetails newUser(final String userId) {
        return newUser(userId, randomAlphabetic(8), randomAlphabetic(8));
    }

    public static CustomUserDetails newUser(final String username, final String password) {
        return newUser(randomAlphabetic(8), username, password);
    }

    public static CustomUserDetails newUser(final String userId, final String username, final String password) {
        return new CustomUserDetails(userId, username, password);
    }
}
