package com.example.demo.transaction;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

class TransactionConsumerServiceTest {

    @Tested
    private TransactionConsumerService transactionConsumerService;

    @Injectable
    private TransactionService transactionService;

    @Test
    void consumeTransaction() {
        final String transactionJson = "[{\"amountWithCurrency\":\"10 USD\",\"accountIban\":\"IBAN1\",\"description\":\"Description1\"}]";
        final ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("ebanking-transactions-topic", 0, 0, null, transactionJson);

        new Expectations() {{
            transactionService.saveTransactionList(anyString);
            times = 1;
        }};

        transactionConsumerService.consumeTransaction(consumerRecord);

        new Verifications() {{
            transactionService.saveTransactionList(transactionJson);
        }};
    }
}
