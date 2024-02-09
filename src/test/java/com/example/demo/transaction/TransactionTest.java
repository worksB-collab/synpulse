package com.example.demo.transaction;

import com.example.demo.account.Account;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionTest {

    @Test
    void transactionInitialization() {
        final String amountWithCurrency = "100 USD";
        final String accountIban = "IBAN";
        final LocalDate valueDate = LocalDate.now();
        final String description = "description";
        final Account account = new Account();

        final Transaction transaction = new Transaction(amountWithCurrency, accountIban, valueDate, description, account);

        assertNotNull(transaction.getId());
        assertEquals(amountWithCurrency, transaction.getAmountWithCurrency());
        assertEquals(accountIban, transaction.getAccountIban());
        assertEquals(valueDate, transaction.getValueDate());
        assertEquals(description, transaction.getDescription());
        assertEquals(account.getId(), transaction.getAccount().getId());
    }


}