package com.example.demo;

import com.example.demo.transaction.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaConfig {
  
  @Value("${kafka.topic}")
  private String kafkaTopic;
  
  @Bean
  public ConsumerFactory<String, Transaction> consumerFactory() {
    // Configure Kafka consumer factory
  }
  
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Transaction> kafkaListenerContainerFactory() {
    // Configure Kafka listener container factory
  }
}
