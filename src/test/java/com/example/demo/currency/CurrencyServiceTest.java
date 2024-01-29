package com.example.demo.currency;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyServiceTest {

    private final static String url = "https://v6.exchangerate-api.com/v6/4a85dbf1b9662036d90c0df5/pair/";
    @Tested
    private CurrencyService currencyService;
    @Injectable
    private RestTemplate restTemplate;

    @Test
    void getExchangeRateSuccess() {
        final String originalCurrency = "USD";
        final String targetCurrency = "EUR";
        final BigDecimal expectedRate = BigDecimal.valueOf(0.85);

        CurrencyResponse mockResponse = new CurrencyResponse();
        mockResponse.setConversion_rate(expectedRate);

        new Expectations() {{
            restTemplate.exchange(
                    url + originalCurrency + "/" + targetCurrency,
                    org.springframework.http.HttpMethod.GET,
                    (org.springframework.http.HttpEntity<?>) any,
                    CurrencyResponse.class
            );
            result = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        }};

        BigDecimal rate = currencyService.getExchangeRate(originalCurrency, targetCurrency);

        assertEquals(expectedRate, rate, "Exchange rate should match the expected value");
    }

    @Test
    void getExchangeRateFailure() {
        final String originalCurrency = "USD";
        final String targetCurrency = "EUR";

        new Expectations() {{
            restTemplate.exchange(
                    url + originalCurrency + "/" + targetCurrency,
                    org.springframework.http.HttpMethod.GET,
                    (org.springframework.http.HttpEntity<?>) any,
                    CurrencyResponse.class
            );
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }};

        assertThrows(RuntimeException.class,
                () -> currencyService.getExchangeRate(originalCurrency, targetCurrency),
                "Should throw RuntimeException on failed request"
        );
    }
}
