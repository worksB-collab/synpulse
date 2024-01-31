package com.example.demo.transaction;

import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static com.example.demo.transaction.TransactionOm.newTransaction;
import static com.example.demo.util.JsonUtil.convertToJson;

class TransactionProducerServiceTest {

    @Tested
    private TransactionProducerService transactionProducerService;

    @Injectable
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void sendTransactionList() {
        final List<Transaction> transactionList = List.of(newTransaction(), newTransaction());
        final String transactionJson = convertToJson(transactionList);

        transactionProducerService.sendTransactionList(transactionList);

        new Verifications() {{
            kafkaTemplate.send("ebanking-transactions-topic", transactionJson);
            times = 1;
        }};
    }
}
