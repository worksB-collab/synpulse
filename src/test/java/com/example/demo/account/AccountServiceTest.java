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

import static com.example.demo.account.AccountOm.newAccount;
import static com.example.demo.user.UserOm.newUser;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Tested
    private AccountService accountService;

    @Injectable
    private AccountDao accountDao;

    @Test
    void getAccountIdsSuccess() {
        final String userId = "userId";
        final CustomUserDetails user = newUser(userId);
        final Account account1 = newAccount(1L, user);
        final Account account2 = newAccount(2L, user);

        new Expectations() {{
            accountDao.findByUserId(userId);
            result = Optional.of(Arrays.asList(account1, account2));
        }};

        final List<Long> accountIds = accountService.getAccountIds(userId);

        assertEquals(Arrays.asList(1L, 2L), accountIds);
    }

    @Test
    void getAccountIdsNotFound() {
        final String userId = "testUserId";

        new Expectations() {{
            accountDao.findByUserId(userId);
            result = Optional.empty();
        }};

        final ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> accountService.getAccountIds(userId),
                "no accounts found"
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void isAccountBelongToUserSuccess() {
        final CustomUserDetails user = newUser("userId");
        final Account account = newAccount(1L, user);

        new Expectations() {{
            accountDao.findById(account.getId());
            result = Optional.of(account);
        }};

        assertTrue(accountService.isAccountBelongToUser(account.getId(), user.getUserId()));
    }

    @Test
    void isAccountBelongToUserNotFound() {
        final Long accountId = 1L;
        final String userId = "userId";

        new Expectations() {{
            accountDao.findById(accountId);
            result = Optional.empty();
        }};

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> accountService.isAccountBelongToUser(accountId, userId),
                "account not found"
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
