package com.example.aggregator;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@CommonsLog
public class AggregatorApplication {
  public static void main(String[] args) {
    SpringApplication.run(AggregatorApplication.class, args);
  }
}
