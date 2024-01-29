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
        final String accountIban = "US123456789";
        final LocalDate valueDate = LocalDate.now();
        final String description = "Test transaction";
        final Account account = new Account(); // Initialize as needed

        Transaction transaction = new Transaction(amountWithCurrency, accountIban, valueDate, description, account);

        assertNotNull(transaction.getId(), "Transaction ID should be initialized");
        assertEquals(amountWithCurrency, transaction.getAmountWithCurrency(), "Amount with currency should be correctly set");
        assertEquals(accountIban, transaction.getAccountIban(), "Account IBAN should be correctly set");
        assertEquals(valueDate, transaction.getValueDate(), "Value date should be correctly set");
        assertEquals(description, transaction.getDescription(), "Description should be correctly set");
        assertEquals(account, transaction.getAccount(), "Account should be correctly set");
    }


}