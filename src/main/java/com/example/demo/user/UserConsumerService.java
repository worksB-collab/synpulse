package com.example.demo.user;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserConsumerService {

    @Autowired
    private final UserService userService;

    @KafkaListener(topics = "ebanking-user-topic", groupId = "ebanking-consumer-group")
    public void consumeUser(final ConsumerRecord<String, String> record) {
        final String userJson = record.value();
        userService.saveUser(userJson);
    }
}
