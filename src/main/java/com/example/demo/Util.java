package com.example.demo;

import com.example.demo.transaction.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class Util {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(final Transaction transaction) {
        try {
            return objectMapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            // Handle exception (e.g., log it or throw a custom exception)
            return null;
        }
    }

    public static String convertListToJson(final List<Transaction> transactions) {
        try {
            return objectMapper.writeValueAsString(transactions);
        } catch (JsonProcessingException e) {
            // Handle exception (log or rethrow, depending on your use case)
            return null;
        }
    }

    public static List<Transaction> convertJsonToTransactionList(String transactionsJson) {
        try {
            return objectMapper.readValue(transactionsJson, new TypeReference<List<Transaction>>() {
            });
        } catch (JsonProcessingException e) {
            // Handle exception (log or rethrow, depending on your use case)
            return Collections.emptyList();
        }
    }
}
