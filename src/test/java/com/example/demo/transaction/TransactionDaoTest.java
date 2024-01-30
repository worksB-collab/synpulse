package com.example.demo.transaction;

import com.example.demo.account.Account;
import com.example.demo.account.AccountDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TransactionDaoTest {

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private AccountDao accountDao;

    @Test
    void findByAccountIdSuccess() {
        final Account account = new Account();
        account.setId(1L);
        accountDao.save(account);
        final Transaction transaction = new Transaction("100 USD", "IBAN", null, "Description", account);
        transactionDao.save(transaction);

        final Optional<List<Transaction>> result = transactionDao.findByAccountId(account.getId());

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }

    @Test
    void findByAccountIdNotFound() {
        final Long accountId = 999L;

        final Optional<List<Transaction>> result = transactionDao.findByAccountId(accountId);

        assertTrue(result.isPresent());
        assertEquals(0, result.get().size());
    }
}
