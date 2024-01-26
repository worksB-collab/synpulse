package com.example.demo.account;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {

    private final static String NO_ACCOUNT = "no account under this user";

    @Autowired
    private final AccountDao accountDao;

    public List<Long> getAccountIds(final String userId) {
        return accountDao.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NO_ACCOUNT))
                .stream().map(Account::getId)
                .collect(Collectors.toList());
    }

    public boolean isAccountBelongToUser(final Long id, final String userId) {
        return accountDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NO_ACCOUNT))
                .getUser().getUserId().equals(userId);
    }

}
