package com.example.demo.transaction;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PaginatedTransactionResponse {

  private List<Transaction> transactions;
  private BigDecimal totalCredit;
  private BigDecimal totalDebit;

  public PaginatedTransactionResponse(List<Transaction> transactions, BigDecimal totalCredit, BigDecimal totalDebit) {
    this.transactions = transactions;
    this.totalCredit = totalCredit;
    this.totalDebit = totalDebit;
  }
}