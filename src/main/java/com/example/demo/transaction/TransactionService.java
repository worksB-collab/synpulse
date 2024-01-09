package com.example.demo.transaction;

import com.example.demo.currency_exchange.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
  
  @Autowired
  private TransactionDao transactionDao;
  
  @Autowired
  private CurrencyExchangeService currencyExchangeService;
  
  public PaginatedTransactionResponse getPaginatedTransactions(String userId, int pageNumber, int pageSize, int month,
                                                               int year, String currency) {
    // Fetch transactions from the repository
    List<Transaction> transactions = transactionDao.getTransactions(userId, month, year, pageNumber, pageSize);
    
    // Calculate total credit and debit
    BigDecimal totalCredit = transactions.stream()
                                         .filter(transaction -> transaction.getAmount()
                                                                           .signum() == 1) // Credit transactions
                                         .map(Transaction::getAmount)
                                         .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    BigDecimal totalDebit = transactions.stream()
                                        .filter(transaction -> transaction.getAmount()
                                                                          .signum() == -1) // Debit transactions
                                        .map(Transaction::getAmount)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    // Call external API to get exchange rate for the given currency
    BigDecimal exchangeRate = currencyExchangeService.getExchangeRate(currency);
    
    // Convert total credit and debit to the desired currency
    totalCredit = totalCredit.multiply(exchangeRate);
    totalDebit = totalDebit.multiply(exchangeRate);
    
    // Build PaginatedTransactionResponse object
    return new PaginatedTransactionResponse(transactions, totalCredit, totalDebit);
  }
  
}
