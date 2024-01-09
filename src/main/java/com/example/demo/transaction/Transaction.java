package com.example.demo.transaction;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Transaction {
  @Id
  private String id;
  private BigDecimal amount;
  private String currency;
  private String accountIban;
  private LocalDate valueDate;
  private String description;
  
}