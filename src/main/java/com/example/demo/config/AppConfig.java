package com.example.demo.config;

import com.example.demo.transaction.Transaction;
import com.example.demo.transaction.TransactionProducerService;
import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.UserDao;
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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private final TransactionProducerService transactionProducerService;
    @Autowired
    private final UserDao userDao;


    @Bean
    public void initData() {
        final CustomUserDetails user = new CustomUserDetails("P-0123456789", "test", bCryptPasswordEncoder()
                .encode("test"));
        userDao.save(user);

        final List<Transaction> transactionList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) { // todo random 1m transactions
            final Transaction transaction = new Transaction();
            transaction.setAmount(new BigDecimal("100")); // todo random
            transaction.setCurrency("USD"); // todo random
            transaction.setAccountIban("CH93-0000-0000-0000-0000-0");
            transaction.setValueDate(LocalDate.of(2023, 12, 1)); // todo random
            transaction.setUser(user);

            transactionList.add(transaction);
        }
        transactionProducerService.sendTransactionList(transactionList);
    }

}