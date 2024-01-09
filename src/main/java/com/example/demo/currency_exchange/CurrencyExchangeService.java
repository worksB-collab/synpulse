package com.example.demo.currency_exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CurrencyExchangeService {
  
  // Placeholder URL for an external exchange rate API
  private static final String EXCHANGE_RATE_API_URL = "https://api.example.com/exchange-rate?baseCurrency=USD&targetCurrency=";
  
  @Value("${external.api.key}") // Use application.properties or application.yml to store API keys
  private String apiKey;
  
  @Autowired
  private RestTemplate restTemplate; // Ensure that you have RestTemplate configured in your application
  
  public BigDecimal getExchangeRate(String targetCurrency) {
    // Placeholder logic to fetch exchange rate from an external API
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + apiKey);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    
    ResponseEntity<ExchangeRateResponse> response = restTemplate.exchange(
      EXCHANGE_RATE_API_URL + targetCurrency,
      HttpMethod.GET,
      entity,
      ExchangeRateResponse.class
                                                                         );
    
    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody().getRate();
    } else {
      throw new RuntimeException("Failed to fetch exchange rate");
    }
  }
}