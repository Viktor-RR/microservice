package com.example.producer.service;

import com.example.producer.data.Payment;
import com.example.producer.data.PaymentDto;
import com.example.producer.data.Role;
import com.example.producer.exceptions.WrongAccessException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static com.example.producer.KafkaProducerApplication.BANKS_COMMAND;
import static com.example.producer.KafkaProducerApplication.REGISTER_PAYMENT;

@Service
@RequiredArgsConstructor
public class TransactionService {
    public static final String WRONG_ACCESS = "Wrong access";
    private final Log logger = LogFactory.getLog(this.getClass());
    private final KafkaTemplate<String, Payment> template;
    private final AuthHelper authHelper;

    @SneakyThrows
    public void doRegister(PaymentDto payment, String token) {

        if (authHelper.getRole(token).equals(Role.ROLE_USER.name())) {
            final ListenableFuture<SendResult<String, Payment>> future =
                    template.send(new ProducerRecord<>(REGISTER_PAYMENT, BANKS_COMMAND, new Payment(payment.getId(),
                            authHelper.getId(token), payment.getAmount(),
                            payment.getComment(), payment.getCardNumber())));
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
        } else {
            throw new WrongAccessException(WRONG_ACCESS);
        }
    }

}
