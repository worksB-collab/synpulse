package com.example.demo.transaction;

import com.example.demo.currency_exchange.CurrencyExchangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.example.demo.Util.convertToJson;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionProducerService producerService;
    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    public PaginatedTransactionResponse getPaginatedTransactions(final String userId, final int pageNumber, final int pageSize,
                                                                 final int month, final int year, final String originalCurrency,
                                                                 final String targetCurrency) {

        final List<Transaction> transactions = transactionDao.getTransactions(userId, month, year, pageNumber, pageSize).orElseThrow();

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

    public void saveTransaction(final Transaction transaction) {
        final String transactionJson = convertToJson(transaction);
        producerService.sendTransaction(transactionJson);
    }

    public void processTransaction(final String transactionJson) {
        try {
            final Transaction transaction = objectMapper.readValue(transactionJson, Transaction.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
