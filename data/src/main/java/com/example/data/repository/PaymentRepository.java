package com.example.data.repository;

import com.example.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM payment LIMIT 100")
    List<Payment> findAll();


    @Query(nativeQuery = true, value = "SELECT * FROM payment WHERE id = :id LIMIT 100")
    List<Payment> findAllPaymentsByMyId(@Param("id") Long id);
}
