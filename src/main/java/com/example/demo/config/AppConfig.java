package com.example.demo.config;

import com.example.demo.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class AppConfig {

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public void initData() {
        List<Transaction> transactions = new ArrayList<>();

        // Placeholder logic to simulate fetching transactions
        for (int i = 1; i <= 30; i++) {
            Transaction transaction = new Transaction();
            transaction.setId(UUID.randomUUID()
                    .toString());
            transaction.setAmount(new BigDecimal("100"));
            transaction.setCurrency("USD");
            transaction.setAccountIban("US12345678901234567890");
            transaction.setValueDate(LocalDate.of(2023, 12, i));
            transaction.setDescription("Transaction " + i);

            transactions.add(transaction);
        }
    }
}