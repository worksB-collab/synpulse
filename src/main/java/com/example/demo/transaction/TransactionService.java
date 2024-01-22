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
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.Util.convertJsonToList;

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

    public PaginatedTransactionResponse getPaginatedTransactions(final String token, final int pageNumber, final int pageSize,
                                                                 final String originalCurrency,
                                                                 final String targetCurrency) {

        final CustomUserDetails user = userDao.findByUsername(jwtTokenUtil.getUsernameFromToken(token.substring(7)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        int offset = (pageNumber - 1) * pageSize;

        final List<Transaction> transactions = transactionDao
                .findByUserId(user.getUserId())
                .stream()
                .flatMap(Collection::stream)
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());

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

    public void saveTransactionList(final String transactionListJson) {
        transactionDao.saveAll(convertJsonToList(transactionListJson, Transaction.class));
    }

}
