package com.example.demo.transaction;

import com.example.demo.account.AccountService;
import com.example.demo.currency.CurrencyService;
import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.UserDao;
import com.example.demo.util.JwtTokenUtil;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @Tested
    private TransactionService transactionService;

    @Injectable
    private UserDao userDao;

    @Injectable
    private TransactionDao transactionDao;

    @Injectable
    private AccountService accountService;

    @Injectable
    private JwtTokenUtil jwtTokenUtil;

    @Injectable
    private CurrencyService currencyService;

    @Test
    void getPaginatedTransactionsSuccess() {
        final String token = "Bearer testToken";
        final Long accountId = 1L;
        final int pageNumber = 1;
        final int pageSize = 5;
        final String targetCurrency = "USD";
        final CustomUserDetails user = new CustomUserDetails();
        final List<Transaction> transactions = IntStream.range(0, pageSize)
                .mapToObj(i -> new Transaction("10 USD", "IBAN", null, "Description", null))
                .collect(Collectors.toList());

        new Expectations() {{
            jwtTokenUtil.getUserIdFromToken(token.substring(7));
            result = "userId";
            userDao.findById(anyString);
            result = Optional.of(user);
            accountService.isAccountBelongToUser(accountId, user.getUserId());
            result = true;
            transactionDao.findByAccountId(accountId);
            result = Optional.of(transactions);
            currencyService.getExchangeRate(anyString, anyString);
            result = BigDecimal.ONE;
        }};

        final PaginatedTransactionResponse response = transactionService.getPaginatedTransactions(token, accountId, pageNumber, pageSize, targetCurrency);

        assertNotNull(response, "Response should not be null");
        assertEquals(transactions.size(), response.getTransactionList().size(), "Should return correct number of transactions");
    }

    @Test
    void getPaginatedTransactionsUnauthorized() {
        final String token = "Bearer testToken";
        final Long accountId = 1L;
        final int pageNumber = 1;
        final int pageSize = 5;
        final String targetCurrency = "USD";
        final CustomUserDetails user = new CustomUserDetails();

        new Expectations() {{
            jwtTokenUtil.getUserIdFromToken(token.substring(7));
            result = "userId";
            userDao.findById(anyString);
            result = Optional.of(user);
            accountService.isAccountBelongToUser(accountId, user.getUserId());
            result = false;
        }};

        assertThrows(ResponseStatusException.class,
                () -> transactionService.getPaginatedTransactions(token, accountId, pageNumber, pageSize, targetCurrency),
                "Should throw ResponseStatusException for unauthorized access");
    }

    @Test
    void getCurrencySuccess() {
        final String originalCurrency = "USD";
        final String targetCurrency = "EUR";
        final BigDecimal expectedRate = BigDecimal.valueOf(0.85);

        new Expectations() {{
            currencyService.getExchangeRate(originalCurrency, targetCurrency);
            result = expectedRate;
        }};

        final BigDecimal rate = transactionService.getCurrency(originalCurrency, targetCurrency);

        assertEquals(expectedRate, rate, "Rate should match the expected exchange rate");
    }

    @Test
    void saveTransactionList() {
        final String transactionListJson = "[{\"amountWithCurrency\":\"100 USD\",\"accountIban\":\"IBAN\"," +
                "\"valueDate\":\"2022-01-01\",\"description\":\"Test\",\"account\":null}]";

        new Verifications() {{
            transactionService.saveTransactionList(transactionListJson);
            times = 1;
        }};

        transactionService.saveTransactionList(transactionListJson);
    }

}
