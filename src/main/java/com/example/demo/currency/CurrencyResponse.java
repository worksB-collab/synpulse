package com.example.demo.currency;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyResponse {
  private String result;
  private String documentation;
  private String terms_of_use;
  private Integer time_last_update_unix;
  private String time_last_update_utc;
  private Integer time_next_update_unix;
  private String time_next_update_utc;
  private String base_code;
  private String target_code;
  private BigDecimal conversion_rate;

}
