package com.example.demo.currency_exchange;

import java.math.BigDecimal;

public class ExchangeRateResponse {
  private BigDecimal rate;
  
  public BigDecimal getRate() {
    return rate;
  }
  
  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }
}
