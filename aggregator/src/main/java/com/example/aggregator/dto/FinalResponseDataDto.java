package com.example.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FinalResponseDataDto {
    private Long id;
    private Long senderId;
    private String amount;
    private String comment;
    private String cardNumber;
    private String username;
    private String role;
}
