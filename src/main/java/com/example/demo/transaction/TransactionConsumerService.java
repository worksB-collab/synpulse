package com.example.demo.transaction;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumerService {

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "ebanking-transactions-topic", groupId = "ebanking-consumer-group")
    public void consumeTransaction(final ConsumerRecord<String, String> record) {
        // Deserialize the JSON representation of the transaction and process it
        final String transactionJson = record.value();

        // Implement your logic to process the transaction data
        transactionService.saveTransactionList(transactionJson);
    }

}
