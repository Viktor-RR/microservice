package com.example.producer.controller;

import com.example.producer.data.PaymentDto;
import com.example.producer.service.TransactionService;
import com.google.common.net.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@Validated
@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/payments")
    public void paymentRegister(@RequestBody @Valid PaymentDto payment, @RequestHeader(HttpHeaders.AUTHORIZATION) String token ) {
        transactionService.doRegister(payment, token);
    }
}
