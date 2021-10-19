package com.example.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment implements Payload {
  @Id
  private long id;
  private long senderId;
  private long amount;
  private String comment;
  private String cardNumber;
}
