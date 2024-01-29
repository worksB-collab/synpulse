package com.example.demo.transaction;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginatedTransactionResponseTest {

    @Test
    void paginatedTransactionResponseInitialization() {
        final Transaction transaction1 = new Transaction("100 USD", "IBAN1", LocalDate.now(), "Description1", null);
        transaction1.setId(UUID.randomUUID());
        final Transaction transaction2 = new Transaction("200 USD", "IBAN2", LocalDate.now(), "Description2", null);
        transaction2.setId(UUID.randomUUID());
        final BigDecimal totalCredit = BigDecimal.valueOf(300);
        final BigDecimal totalDebit = BigDecimal.valueOf(150);

        final PaginatedTransactionResponse response = new PaginatedTransactionResponse(List.of(transaction1, transaction2), totalCredit, totalDebit);

        assertEquals(2, response.getTransactionList().size(), "Transaction list should contain 2 transactions");
        assertEquals(totalCredit, response.getTotalCredit(), "Total credit should be correctly set");
        assertEquals(totalDebit, response.getTotalDebit(), "Total debit should be correctly set");

        final Map<String, Object> transactionResponse1 = response.getTransactionList().get(0);
        assertEquals(transaction1.getId(), transactionResponse1.get("id"), "Transaction ID should be correctly set");
        assertEquals(transaction1.getAmountWithCurrency(), transactionResponse1.get("amount"), "Transaction amount should be correctly set");
        assertEquals(transaction1.getAccountIban(), transactionResponse1.get("accountIban"), "Transaction account IBAN should be correctly set");
        assertEquals(transaction1.getValueDate(), transactionResponse1.get("valueDate"), "Transaction value date should be correctly set");
        assertEquals(transaction1.getDescription(), transactionResponse1.get("description"), "Transaction description should be correctly set");
    }
}
