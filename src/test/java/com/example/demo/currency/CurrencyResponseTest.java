package com.example.demo.currency;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyResponseTest {

    @Test
    void deserializeCurrencyResponse() {
        final String json = "{"
                + "\"result\": \"success\","
                + "\"documentation\": \"https://www.exchangerate-api.com/docs\","
                + "\"terms_of_use\": \"https://www.exchangerate-api.com/terms\","
                + "\"time_last_update_unix\": 1706486401,"
                + "\"time_last_update_utc\": \"Mon, 29 Jan 2024 00:00:01 +0000\","
                + "\"time_next_update_unix\": 1706572801,"
                + "\"time_next_update_utc\": \"Tue, 30 Jan 2024 00:00:01 +0000\","
                + "\"base_code\": \"USD\","
                + "\"target_code\": \"TWD\","
                + "\"conversion_rate\": 31.2055"
                + "}";

        final Gson gson = new Gson();
        final CurrencyResponse response = gson.fromJson(json, CurrencyResponse.class);

        assertEquals("success", response.getResult(), "Result should be 'success'");
        assertEquals("https://www.exchangerate-api.com/docs", response.getDocumentation(), "Documentation URL should match");
        assertEquals("https://www.exchangerate-api.com/terms", response.getTerms_of_use(), "Terms of use URL should match");
        assertEquals(Integer.valueOf(1706486401), response.getTime_last_update_unix(), "Time last update unix should match");
        assertEquals("Mon, 29 Jan 2024 00:00:01 +0000", response.getTime_last_update_utc(), "Time last update UTC should match");
        assertEquals(Integer.valueOf(1706572801), response.getTime_next_update_unix(), "Time next update unix should match");
        assertEquals("Tue, 30 Jan 2024 00:00:01 +0000", response.getTime_next_update_utc(), "Time next update UTC should match");
        assertEquals("USD", response.getBase_code(), "Base code should be 'USD'");
        assertEquals("TWD", response.getTarget_code(), "Target code should be 'TWD'");
        assertEquals(new BigDecimal("31.2055"), response.getConversion_rate(), "Conversion rate should match");
    }
}
