package com.example.demo.transaction;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static com.example.demo.transaction.TransactionOm.newTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionControllerTest {

    @Tested
    private TransactionController transactionController;

    @Injectable
    private TransactionService transactionService;

    @Test
    void getPaginatedTransactions() {
        final String token = "Bearer testToken";
        final Long accountId = 1L;
        final int pageNumber = 1;
        final int pageSize = 5;
        final String targetCurrency = "EUR";
        final List<Transaction> transactionList = List.of(newTransaction());
        final BigDecimal expectedTotalCredit = BigDecimal.valueOf(10L);
        final BigDecimal expectedTotalDebit = BigDecimal.valueOf(0L);
        final PaginatedTransactionResponse expectedResponse = new PaginatedTransactionResponse(transactionList, expectedTotalCredit, expectedTotalDebit);

        new Expectations() {{
            transactionService.getPaginatedTransactions(token, accountId, pageNumber, pageSize, targetCurrency);
            result = expectedResponse;
        }};

        final ResponseEntity<PaginatedTransactionResponse> response = transactionController.getPaginatedTransactions(
                token, accountId, pageNumber, pageSize, targetCurrency
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

}
