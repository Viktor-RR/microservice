package com.example.data.service;


import com.example.data.entity.Payment;
import com.example.data.entity.Role;
import com.example.data.exceptions.TransactionExistsException;
import com.example.data.repository.PaymentRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    public static final String WRONG_PAYMENT_ID_NUMBER_CHECK_AGAIN = "Wrong payment ID number, check again";
    private final PaymentRepository paymentRepository;
    private final AuthHelper authHelper;

    public PaymentService(PaymentRepository paymentRepository, AuthHelper authHelper) {
        this.paymentRepository = paymentRepository;
        this.authHelper = authHelper;
    }

    public void savePayment(Payment payment) {
        final Optional<Payment> maybePayment = paymentRepository.findById(payment.getId());
        if (maybePayment.isPresent()) {
            throw new TransactionExistsException(WRONG_PAYMENT_ID_NUMBER_CHECK_AGAIN);
        } else {
            paymentRepository.save(payment);
        }
    }

    @SneakyThrows
    public List<Payment> findAllPayments(String token) {
        if (authHelper.getRole(token).equals(Role.ROLE_ADMIN.name())) {
            final List<Payment> list = paymentRepository.findAll();
            return list;
        }
        if (authHelper.getRole(token).equals(Role.ROLE_USER.name())) {
            final List<Payment> list  = paymentRepository.findAllPaymentsByMyId(authHelper.getId(token));
            return list;
        }
        else {
            throw new RuntimeException("Wrong access");
        }
    }

}
