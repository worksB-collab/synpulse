package com.example.demo.transaction;

import com.example.demo.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/paginate")
    public ResponseEntity<PaginatedTransactionResponse> getPaginatedTransactions(
            @RequestParam("pageNumber") final int pageNumber,
            @RequestParam("pageSize") final int pageSize,
            @RequestParam("originalCurrency") final String originalCurrency,
            @RequestParam("targetCurrency") final String targetCurrency,
            @AuthenticationPrincipal final CustomUserDetails userDetails) {

        String userId = "1";//userDetails.getUserId();

        final PaginatedTransactionResponse response = transactionService.getPaginatedTransactions(userId, pageNumber, pageSize,
                originalCurrency, targetCurrency);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}