package com.example.demo.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.Util.convertListToJson;

@Service
public class TransactionProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendTransaction(String transactionJson) {
        // Send the transaction to the specified Kafka topic
        kafkaTemplate.send("ebanking-transactions-topic", transactionJson);
    }

    public void sendTransactionList(final List<Transaction> transactions) {
        String transactionJson = convertListToJson(transactions);
        kafkaTemplate.send("ebanking-transactions-topic", transactionJson);
    }
}
