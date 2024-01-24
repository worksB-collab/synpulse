package com.example.demo.transaction;

import com.example.demo.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Transaction {
  @Id
  private UUID id;
  private BigDecimal amount;
  private String currency;
  private String accountIban;
  private LocalDate valueDate;
  private String description;

  @ManyToOne(cascade = CascadeType.REMOVE)
  private Account account;

  public Transaction(final BigDecimal amount, final String currency, final String accountIban, final LocalDate valueDate,
                     final String description, final Account account) {
    this.id = UUID.randomUUID();
    this.amount = amount;
    this.currency = currency;
    this.accountIban = accountIban;
    this.valueDate = valueDate;
    this.description = description;
    this.account = account;
  }

}