package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(Integer paymentId);

    List<Payment> findAll();

    Payment save(Payment payment);

    Payment editAmount(Integer paymentId, Double newAmount);
}
