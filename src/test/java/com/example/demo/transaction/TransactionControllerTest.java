package com.example.demo.transaction;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionControllerTest {

    @Tested
    private TransactionController transactionController;

    @Injectable
    private TransactionService transactionService;

    @Test
    void getPaginatedTransactionsSuccess() {
        final String token = "Bearer testToken";
        final Long accountId = 1L;
        final int pageNumber = 1;
        final int pageSize = 5;
        final String targetCurrency = "EUR";
        final List<Transaction> transactionList = List.of(new Transaction());
        final BigDecimal expectedTotalCredit = BigDecimal.valueOf(10L);
        final BigDecimal expectedTotalDebit = BigDecimal.valueOf(0L);
        final PaginatedTransactionResponse expectedResponse = new PaginatedTransactionResponse(transactionList, expectedTotalCredit, expectedTotalDebit);

        new Expectations() {{
            transactionService.getPaginatedTransactions(token, accountId, pageNumber, pageSize, targetCurrency);
            result = expectedResponse;
        }};

        ResponseEntity<PaginatedTransactionResponse> response = transactionController.getPaginatedTransactions(
                token, accountId, pageNumber, pageSize, targetCurrency
        );

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertEquals(expectedResponse, response.getBody(), "Response body should match the expected response");
    }

}
