package com.example.demo.account;

import com.example.demo.user.CustomUserDetails;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Tested
    private AccountService accountService;

    @Injectable
    private AccountDao accountDao;

    @Test
    void getAccountIdsSuccess() {
        final String userId = "testUserId";
        final Account account1 = new Account();
        account1.setId(1L);
        final Account account2 = new Account();
        account2.setId(2L);

        new Expectations() {{
            accountDao.findByUserId(userId);
            result = Optional.of(Arrays.asList(account1, account2));
        }};

        final List<Long> accountIds = accountService.getAccountIds(userId);

        assertEquals(Arrays.asList(1L, 2L), accountIds, "Should return correct account IDs");
    }

    @Test
    void getAccountIdsNotFound() {
        final String userId = "testUserId";

        new Expectations() {{
            accountDao.findByUserId(userId);
            result = Optional.empty();
        }};

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> accountService.getAccountIds(userId),
                "Should throw ResponseStatusException when no accounts found"
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus(), "Exception status should be NOT_FOUND");
    }

    @Test
    void isAccountBelongToUserSuccess() {
        final Long accountId = 1L;
        final String userId = "testUserId";
        final CustomUserDetails user = new CustomUserDetails();
        user.setUserId(userId);
        final Account account = new Account();
        account.setUser(user);

        new Expectations() {{
            accountDao.findById(accountId);
            result = Optional.of(account);
        }};

        assertTrue(accountService.isAccountBelongToUser(accountId, userId), "Should return true if account belongs to user");
    }

    @Test
    void isAccountBelongToUserNotFound() {
        final Long accountId = 1L;
        final String userId = "testUserId";

        new Expectations() {{
            accountDao.findById(accountId);
            result = Optional.empty();
        }};

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> accountService.isAccountBelongToUser(accountId, userId),
                "Should throw ResponseStatusException when account not found"
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus(), "Exception status should be NOT_FOUND");
    }
}
