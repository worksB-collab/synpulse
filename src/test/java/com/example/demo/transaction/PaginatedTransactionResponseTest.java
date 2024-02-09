package com.example.demo.transaction;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.example.demo.transaction.TransactionOm.newTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginatedTransactionResponseTest {

    @Test
    void paginatedTransactionResponseInitialization() {
        final Transaction transaction1 = newTransaction();
        final Transaction transaction2 = newTransaction();
        final BigDecimal totalCredit = BigDecimal.valueOf(300);
        final BigDecimal totalDebit = BigDecimal.valueOf(150);

        final PaginatedTransactionResponse response = new PaginatedTransactionResponse(List.of(transaction1, transaction2), totalCredit, totalDebit);

        assertEquals(2, response.getTransactionList().size());
        assertEquals(totalCredit, response.getTotalCredit());
        assertEquals(totalDebit, response.getTotalDebit());

        final Map<String, Object> transactionResponse1 = response.getTransactionList().get(0);
        assertEquals(transaction1.getId(), transactionResponse1.get("id"));
        assertEquals(transaction1.getAmountWithCurrency(), transactionResponse1.get("amount"));
        assertEquals(transaction1.getAccountIban(), transactionResponse1.get("accountIban"));
        assertEquals(transaction1.getValueDate(), transactionResponse1.get("valueDate"));
        assertEquals(transaction1.getDescription(), transactionResponse1.get("description"));

        final Map<String, Object> transactionResponse2 = response.getTransactionList().get(1);
        assertEquals(transaction2.getId(), transactionResponse2.get("id"));
        assertEquals(transaction2.getAmountWithCurrency(), transactionResponse2.get("amount"));
        assertEquals(transaction2.getAccountIban(), transactionResponse2.get("accountIban"));
        assertEquals(transaction2.getValueDate(), transactionResponse2.get("valueDate"));
        assertEquals(transaction2.getDescription(), transactionResponse2.get("description"));
    }
}
