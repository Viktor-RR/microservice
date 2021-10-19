package com.example.data.controller;

import com.example.data.entity.Payment;
import com.example.data.service.PaymentService;
import com.google.common.net.HttpHeaders;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final PaymentService paymentService;

    public Controller(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getPayments")
    public List<Payment> getPayments(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        final List<Payment> list = paymentService.findAllPayments(token);
        return list;

    }

}