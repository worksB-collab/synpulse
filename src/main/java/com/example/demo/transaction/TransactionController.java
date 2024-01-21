package com.example.demo.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/paginate")
    public ResponseEntity<PaginatedTransactionResponse> getPaginatedTransactions(
            @RequestHeader("Authorization") final String token,
            @RequestParam("pageNumber") final int pageNumber,
            @RequestParam("pageSize") final int pageSize,
            @RequestParam("originalCurrency") final String originalCurrency,
            @RequestParam("targetCurrency") final String targetCurrency) {
        final PaginatedTransactionResponse response = transactionService.getPaginatedTransactions(token, pageNumber, pageSize,
                originalCurrency, targetCurrency);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}