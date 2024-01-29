package com.example.demo.transaction;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.demo.util.JsonUtil.convertToJson;

class TransactionProducerServiceTest {

    @Tested
    private TransactionProducerService transactionProducerService;

    @Injectable
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void sendTransactionList() {
        final List<Transaction> transactionList = IntStream.range(0, 5)
                .mapToObj(i -> new Transaction("10 USD", "IBAN" + i, null, "Description" + i, null))
                .collect(Collectors.toList());
        final String transactionJson = convertToJson(transactionList);

        new Expectations() {{
            kafkaTemplate.send("ebanking-transactions-topic", transactionJson);
            times = 1;
        }};

        transactionProducerService.sendTransactionList(transactionList);

        new Verifications() {{
            kafkaTemplate.send(withInstanceOf(ProducerRecord.class));
            times = 1;
        }};
    }
}
