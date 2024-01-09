package com.example.demo.transaction;

import java.math.BigDecimal;
import java.util.List;

public class PaginatedTransactionResponse {
  
  private List<Transaction> transactions;
  private BigDecimal totalCredit;
  private BigDecimal totalDebit;
  
  // Constructors, getters, and setters
  
  public PaginatedTransactionResponse(List<Transaction> transactions, BigDecimal totalCredit, BigDecimal totalDebit) {
    this.transactions = transactions;
    this.totalCredit = totalCredit;
    this.totalDebit = totalDebit;
  }
  
  public List<Transaction> getTransactions() {
    return transactions;
  }
  
  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }
  
  public BigDecimal getTotalCredit() {
    return totalCredit;
  }
  
  public void setTotalCredit(BigDecimal totalCredit) {
    this.totalCredit = totalCredit;
  }
  
  public BigDecimal getTotalDebit() {
    return totalDebit;
  }
  
  public void setTotalDebit(BigDecimal totalDebit) {
    this.totalDebit = totalDebit;
  }
}