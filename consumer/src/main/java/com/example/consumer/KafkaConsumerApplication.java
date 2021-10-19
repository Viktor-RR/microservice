package com.example.consumer;

import com.example.consumer.data.Payment;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@SpringBootApplication
@RequiredArgsConstructor
public class KafkaConsumerApplication {
  private final Log logger = LogFactory.getLog(this.getClass());
  private final KafkaTemplate<String, Payment> template;
  public static final String REGISTER_PAYMENT = "paymentsProcess";
  public static final String BANKS_COMMAND = "banksAnother";

  public static void main(String[] args) {
    SpringApplication.run(KafkaConsumerApplication.class, args);
  }
  @Bean // Kafka Admin
  public NewTopic messagesTopic() {
    return new NewTopic(REGISTER_PAYMENT, 3, (short)2);
  }

  @KafkaListener(groupId = "consumer", topics = "payments")
  public void listen(Payment message, ConsumerRecord<String, Payment> record, Acknowledgment acknowledgment) {
    logger.info(message);
    acknowledgment.acknowledge();
    registerToAnotherTopic(message);
  }

  public void registerToAnotherTopic(Payment payment) {
    final ListenableFuture<SendResult<String, Payment>> future =
            template.send(REGISTER_PAYMENT, BANKS_COMMAND, payment);

    future.addCallback(new ListenableFutureCallback<SendResult<String, Payment>>() {
      @Override
      public void onFailure(@NonNull Throwable ex) {
        ex.printStackTrace();
      }
      @Override
      public void onSuccess(SendResult<String, Payment> result) {
        logger.info(result);
      }
    });
  }
}
