package com.example.demo.transaction;

import com.example.demo.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Transaction {
  @Id
  private UUID id;
  private String amountWithCurrency;
  private String accountIban;
  private LocalDate valueDate;
  private String description;

  @ManyToOne(cascade = CascadeType.REMOVE)
  private Account account;

  public Transaction(final String amountWithCurrency, final String accountIban, final LocalDate valueDate,
                     final String description, final Account account) {
    this.id = UUID.randomUUID();
    this.amountWithCurrency = amountWithCurrency;
    this.accountIban = accountIban;
    this.valueDate = valueDate;
    this.description = description;
    this.account = account;
  }

}