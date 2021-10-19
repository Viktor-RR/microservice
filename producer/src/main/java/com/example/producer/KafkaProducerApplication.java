package com.example.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaProducerApplication {
  public static final String REGISTER_PAYMENT = "payments";
  public static final String BANKS_COMMAND = "banksCommand";

  public static void main(String[] args) {
    SpringApplication.run(KafkaProducerApplication.class, args);
  }

  @Bean // Kafka Admin
  public NewTopic messagesTopic() {
    return new NewTopic(REGISTER_PAYMENT, 3, (short)2);
  }
}
