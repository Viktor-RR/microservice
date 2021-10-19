package com.example.consumer.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment implements Payload {
  private long id;
  private long senderId;
  private long amount;
  private String comment;
  private String cardNumber;
}
