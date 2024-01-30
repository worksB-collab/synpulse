package com.example.demo.user;

import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class UserOm {

    public static CustomUserDetails newUser() {
        return newUser(randomAlphabetic(8));
    }

    public static CustomUserDetails newUser(final String userId) {
        return new CustomUserDetails(userId, randomAlphabetic(8), randomAlphabetic(8));
    }
}
