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
    @RequestParam("pageNumber") int pageNumber,
    @RequestParam("pageSize") int pageSize,
    @RequestParam("month") int month,
    @RequestParam("year") int year,
    @RequestParam("currency") String currency,
    @AuthenticationPrincipal CustomUserDetails userDetails) {
    
    String userId = userDetails.getUserId();
    
    // Fetch paginated transactions from the service
    PaginatedTransactionResponse response = transactionService.getPaginatedTransactions(userId, pageNumber, pageSize,
                                                                                        month, year, currency);
    
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}