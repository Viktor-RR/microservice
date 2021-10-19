package com.example.aggregator.client;


import com.example.aggregator.dto.FinalResponseDataDto;
import com.example.aggregator.dto.ResponseDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "user")
public interface UserClient {
    @PostMapping("/usersPayments")
    List<FinalResponseDataDto> getUsers(List<ResponseDataDto> payments);
}
