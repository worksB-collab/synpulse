package com.example.demo.config;

import com.example.demo.transaction.Transaction;
import com.example.demo.transaction.TransactionProducerService;
import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.UserProducerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
public class AppConfig {

    @Autowired
    private final TransactionProducerService transactionProducerService;
    @Autowired
    private final UserProducerService userProducerService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public void initData() {
        final List<Transaction> transactionList = newRandomTransactions();
        final CustomUserDetails user = new CustomUserDetails("test", "test", "test");
        user.setTransactionList(transactionList);
        userProducerService.sendUser(user);
    }

    private List<Transaction> newRandomTransactions() {
        final List<Transaction> transactionList = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            final Transaction transaction = new Transaction();
            transaction.setAmount(new BigDecimal("100"));
            transaction.setCurrency("USD");
            transaction.setAccountIban("US12345678901234567890");
            transaction.setValueDate(LocalDate.of(2023, 12, i));
            transaction.setDescription("Transaction " + i);

            transactionList.add(transaction);
        }
        transactionProducerService.sendTransactionList(transactionList);

        return transactionList;
    }
}