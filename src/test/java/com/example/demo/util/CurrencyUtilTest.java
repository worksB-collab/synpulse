package com.example.demo.util;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CurrencyUtilTest {

    @RepeatedTest(10)
    void getRandomCurrencyReturnsValidCurrency() {
        final String currency = CurrencyUtil.getRandomCurrency();

        assertNotNull(currency);
        assertFalse(currency.isEmpty());
    }
}
