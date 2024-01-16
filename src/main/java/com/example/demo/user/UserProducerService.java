package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.demo.Util.convertToJson;

@Service
public class UserProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendUser(final CustomUserDetails user) {
        final String userJson = convertToJson(user);
        kafkaTemplate.send("ebanking-user-topic", userJson);
    }
}
