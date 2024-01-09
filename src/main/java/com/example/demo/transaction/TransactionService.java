package com.example.demo.transaction;

import com.example.demo.currency_exchange.CurrencyExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class TransactionService {
  
  @Autowired
  private TransactionDao transactionDao;
  
  @Autowired
  private CurrencyExchangeService currencyExchangeService;

  public PaginatedTransactionResponse getPaginatedTransactions(String userId, int pageNumber, int pageSize, int month,
                                                               int year, String originalCurrency, String targetCurrency) {

    List<Transaction> transactions = transactionDao.getTransactions(userId, month, year, pageNumber, pageSize);

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
    BigDecimal exchangeRate = currencyExchangeService.getExchangeRate(originalCurrency, targetCurrency);

    if (exchangeRate == null) {
      log.info("exchangeRate is null");
      exchangeRate = BigDecimal.ONE; // no conversion
    }

    // Convert total credit and debit to the desired currency
    totalCredit = totalCredit.multiply(exchangeRate);
    totalDebit = totalDebit.multiply(exchangeRate);

    // Build PaginatedTransactionResponse object
    return new PaginatedTransactionResponse(transactions, totalCredit, totalDebit);
  }
  
}
