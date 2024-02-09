package com.example.demo.transaction;

import com.example.demo.account.Account;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

import static com.example.demo.account.AccountOm.newAccount;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class TransactionOm {

    public static Transaction newTransaction() {
        return newTransaction(newAccount());
    }

    public static Transaction newTransaction(final Account account) {
        return new Transaction(randomAlphabetic(8), randomAlphabetic(8), LocalDate.now(), randomAlphabetic(8), account);
    }

}
