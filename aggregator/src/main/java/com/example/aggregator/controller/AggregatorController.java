package com.example.aggregator.controller;

import com.example.aggregator.client.DataClient;
import com.example.aggregator.client.UserClient;
import com.example.aggregator.dto.FinalResponseDataDto;
import com.example.aggregator.dto.ResponseDataDto;
import com.example.aggregator.service.AggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AggregatorController {
    private final AggregatorService aggregatorService;

    @GetMapping("/payments")
    public List<FinalResponseDataDto> getAllPayments() {
        return aggregatorService.getPayments();
    }
}
