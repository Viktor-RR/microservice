package com.example.aggregator.service;

import com.example.aggregator.client.DataClient;
import com.example.aggregator.client.UserClient;
import com.example.aggregator.dto.FinalResponseDataDto;
import com.example.aggregator.dto.ResponseDataDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregatorService {

    private final DataClient dataClient;
    private final UserClient userClient;

    public AggregatorService(DataClient dataClient, UserClient userClient) {
        this.dataClient = dataClient;
        this.userClient = userClient;
    }

    public List<FinalResponseDataDto> getPayments() {
        final List<ResponseDataDto> payments = dataClient.getPayments();
        return getAllUpgradedPayments(payments);
    }

    private List<FinalResponseDataDto> getAllUpgradedPayments(List<ResponseDataDto> payments) {
        return userClient.getUsers(payments);
    }
}
