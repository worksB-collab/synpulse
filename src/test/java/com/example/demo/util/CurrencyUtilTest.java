package com.example.demo.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CurrencyUtilTest {

    @RepeatedTest(10)
    void getRandomCurrencyReturnsValidCurrency() {
        final String currency = CurrencyUtil.getRandomCurrency();

        Assertions.assertNotNull("getRandomCurrency should not return null", currency);
        assertFalse(currency.isEmpty(), "getRandomCurrency should not return an empty string");
    }
}
