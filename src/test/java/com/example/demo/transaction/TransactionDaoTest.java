package com.example.demo.transaction;

import com.example.demo.account.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TransactionDaoTest {

    @Autowired
    private TransactionDao transactionDao;

    @Test
    void findByAccountIdSuccess() {
        final Account account = new Account();
        account.setId(1L);
        final Transaction transaction = new Transaction("100 USD", "IBAN", null, "Description", account);
        transactionDao.save(transaction);

        final Optional<List<Transaction>> foundTransactions = transactionDao.findByAccountId(account.getId());

        assertTrue(foundTransactions.isPresent(), "Transactions should be found");
        assertTrue(foundTransactions.get().contains(transaction), "Found transactions should contain the saved transaction");
    }

    @Test
    void findByAccountIdNotFound() {
        final Long accountId = 999L;

        final Optional<List<Transaction>> foundTransactions = transactionDao.findByAccountId(accountId);

        assertFalse(foundTransactions.isPresent(), "Transactions should not be found for nonexistent account");
    }
}
