package com.example.producer.data;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDto {
    private long id;
    @AmountValue
    private long amount;
    private String comment;
    @CardNumber
    private String cardNumber;
}
