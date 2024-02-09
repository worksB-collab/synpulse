package com.example.demo.transaction;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PaginatedTransactionResponse {

    private final List<Map<String, Object>> transactionList;
    private final BigDecimal totalCredit;
    private final BigDecimal totalDebit;

    public PaginatedTransactionResponse(final List<Transaction> transactionList, final BigDecimal totalCredit, final BigDecimal totalDebit) {
        this.transactionList = getTransactionResponse(transactionList);
        this.totalCredit = totalCredit;
        this.totalDebit = totalDebit;
    }

    private List<Map<String, Object>> getTransactionResponse(final List<Transaction> transactionList) {
        final List<Map<String, Object>> list = new ArrayList<>();
        transactionList.forEach(transaction -> {
            final Map<String, Object> map = new HashMap<>();
            map.put("id", transaction.getId());
            map.put("amount", transaction.getAmountWithCurrency());
            map.put("currency", transaction.getAmountWithCurrency());
            map.put("accountIban", transaction.getAccountIban());
            map.put("valueDate", transaction.getValueDate());
            map.put("description", transaction.getDescription());
            list.add(map);
        });
        return list;
    }
}