package com.example.demo.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.util.JsonUtil.convertToJson;

@Service
public class TransactionProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendTransactionList(final List<Transaction> transactionList) {
        final String transactionJson = convertToJson(transactionList);
        kafkaTemplate.send("ebanking-transactions-topic", transactionJson);
    }

}
