package com.example.data;

import com.example.data.entity.Payment;
import com.example.data.service.PaymentService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@SpringBootApplication
public class DataApplication {
  private final PaymentService paymentService;

  public DataApplication(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  public static void main(String[] args) {
    SpringApplication.run(DataApplication.class, args);
  }

  @KafkaListener(groupId = "consumer", topics = "paymentsProcess")
  public void listen(Payment message, ConsumerRecord<String, Payment> record, Acknowledgment acknowledgment) {
    paymentService.savePayment(message);
    acknowledgment.acknowledge();
  }
}
