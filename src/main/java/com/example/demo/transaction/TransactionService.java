package com.example.demo.transaction;

import com.example.demo.account.AccountService;
import com.example.demo.currency.CurrencyService;
import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.UserDao;
import com.example.demo.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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

import static com.example.demo.util.JsonUtil.convertJsonToList;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    @Autowired
    private final UserDao userDao;
    @Autowired
    private final TransactionDao transactionDao;
    @Autowired
    private final AccountService accountService;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private final CurrencyService currencyService;

    private final Map<String, Map<String, BigDecimal>> currencyToCurrencyToRateMap = new HashMap<>();

    public PaginatedTransactionResponse getPaginatedTransactions(final String token, final Long accountId,
                                                                 final int pageNumber, final int pageSize,
                                                                 final String targetCurrency) {

        final CustomUserDetails user = userDao.findById(jwtTokenUtil.getUserIdFromToken(token.substring(7)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!accountService.isAccountBelongToUser(accountId, user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account not belongs to user");
        }

        int offset = (pageNumber - 1) * pageSize;

        final List<Transaction> transactions = transactionDao
                .findByAccountId(accountId)
                .stream()
                .flatMap(Collection::stream)
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());

        final BigDecimal totalCredit = getTotal(transactions, targetCurrency, 1);
        final BigDecimal totalDebit = getTotal(transactions, targetCurrency, -1);

        return new PaginatedTransactionResponse(transactions, totalCredit, totalDebit);
    }

    @NotNull
    private BigDecimal getTotal(final List<Transaction> transactions, final String targetCurrency, final int signum) {
        return transactions.stream()
                .filter(transaction -> {
                    final String amountStr = transaction.getAmountWithCurrency().split(" ")[1];
                    final BigDecimal amount = new BigDecimal(amountStr);
                    return amount.signum() == signum;
                })
                .map(transaction -> {
                    final String transactionCurrency = transaction.getAmountWithCurrency().split(" ")[0];
                    final String amountStr = transaction.getAmountWithCurrency().split(" ")[1];
                    final BigDecimal amount = new BigDecimal(amountStr);
                    return amount.multiply(getCurrency(transactionCurrency, targetCurrency));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getCurrency(final String originalCurrency, final String targetCurrency) {
        final Map<String, BigDecimal> foundCurrencyToRateMap = currencyToCurrencyToRateMap.get(originalCurrency);
        final BigDecimal rate = currencyService.getExchangeRate(originalCurrency, targetCurrency);
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
