package com.example.demo.config;

import com.example.demo.account.Account;
import com.example.demo.account.AccountDao;
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

import static com.example.demo.util.CurrencyUtil.getRandomCurrency;


@Configuration
@AllArgsConstructor
public class AppConfig {

    @Autowired
    private final TransactionProducerService transactionProducerService;
    @Autowired
    private final UserDao userDao;
    @Autowired
    private final AccountDao accountDao;

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
        final CustomUserDetails user = new CustomUserDetails(
                "P-0123456789",
                "test",
                bCryptPasswordEncoder().encode("test"));
        userDao.save(user);

        initOneAccountForUser(user); // first account
        initOneAccountForUser(user); // second account

        final CustomUserDetails user2 = new CustomUserDetails(
                "P-0123456782",
                "test2",
                bCryptPasswordEncoder().encode("test2"));
        userDao.save(user2);

    }

    private void initOneAccountForUser(final CustomUserDetails user) {
        final Account account = new Account(user);
        accountDao.save(account);

        final List<Transaction> transactionList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            final Transaction transaction = new Transaction(
                    getRandomCurrency() + " " + BigDecimal.valueOf(Math.random() * 100),
                    "CH93-0000-0000-0000-0000-0",
                    LocalDate.of(2023, 12, 1),
                    "" + i,
                    account
            );

            transactionList.add(transaction);
        }
        transactionProducerService.sendTransactionList(transactionList);
    }
}