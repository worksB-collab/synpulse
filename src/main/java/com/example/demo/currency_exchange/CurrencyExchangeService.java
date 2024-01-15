package com.example.demo.currency_exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class CurrencyExchangeService {
    @Value("${external.api.url}")
    private String EXCHANGE_RATE_API_URL;

    @Autowired
    private RestTemplate restTemplate;

    public BigDecimal getExchangeRate(final String originalCurrency, final String targetCurrency) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ExchangeRateResponse> response = restTemplate.exchange(
                EXCHANGE_RATE_API_URL + originalCurrency + "/" + targetCurrency,
                HttpMethod.GET,
                entity,
                ExchangeRateResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(response.getBody()).getConversion_rate();
        } else {
            throw new RuntimeException("Failed to fetch exchange rate");
        }
    }
}