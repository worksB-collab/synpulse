package com.example.demo.transaction;

import com.example.demo.JwtTokenUtil;
import com.example.demo.currency_exchange.CurrencyExchangeService;
import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.JsonUtil.convertJsonToList;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    private final Map<String, Map<String, BigDecimal>> currencyToCurrencyToRateMap = new HashMap<>();

    public PaginatedTransactionResponse getPaginatedTransactions(final String token, final int pageNumber, final int pageSize,
                                                                 final String targetCurrency) {

        final CustomUserDetails user = userDao.findById(jwtTokenUtil.getUserIdFromToken(token.substring(7)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        int offset = (pageNumber - 1) * pageSize;

        final List<Transaction> transactions = transactionDao
                .findByAccountId(user.getUserId())
                .stream()
                .flatMap(Collection::stream)
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());

        final BigDecimal totalCredit = transactions.stream()
                .filter(transaction -> transaction.getAmount()
                        .signum() == 1) // Credit transactions
                .map(transaction ->
                        transaction.getAmount().multiply(getCurrency(transaction.getCurrency(), targetCurrency)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal totalDebit = transactions.stream()
                .filter(transaction -> transaction.getAmount()
                        .signum() == -1) // Debit transactions
                .map(transaction ->
                        transaction.getAmount().multiply(getCurrency(transaction.getCurrency(), targetCurrency)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PaginatedTransactionResponse(transactions, totalCredit, totalDebit);
    }

    public BigDecimal getCurrency(final String originalCurrency, final String targetCurrency) {
        final Map<String, BigDecimal> foundCurrencyToRateMap = currencyToCurrencyToRateMap.get(originalCurrency);
        final BigDecimal rate = currencyExchangeService.getExchangeRate(originalCurrency, targetCurrency);
        if (foundCurrencyToRateMap == null) {
            final Map<String, BigDecimal> map = Map.of(targetCurrency, rate);
            currencyToCurrencyToRateMap.put(originalCurrency, map);
            return rate;
        }
        final BigDecimal foundRate = foundCurrencyToRateMap.get(targetCurrency);
        if (foundRate == null) {
            foundCurrencyToRateMap.put(targetCurrency, rate);
            return rate;
        } else {
            return foundRate;
        }
    }


    public void saveTransactionList(final String transactionListJson) {
        transactionDao.saveAll(convertJsonToList(transactionListJson, Transaction.class));
    }

}
