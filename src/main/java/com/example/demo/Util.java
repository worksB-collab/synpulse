package com.example.demo;

import com.example.demo.transaction.Transaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Util {

    private static final Gson gson = new Gson();


    public static String convertListToJson(final List<Transaction> transactions) {
        return gson.toJson(transactions);
    }

    public static List<Transaction> convertJsonToTransactionList(final String transactionListJson) {
        return gson.fromJson(transactionListJson, new TypeToken<List<Transaction>>() {
        }.getType());
    }
}
