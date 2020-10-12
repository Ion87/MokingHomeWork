package com.endava.internship.mocking.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.endava.internship.mocking.model.Payment;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class InMemPaymentRepository implements PaymentRepository {

    private final List<Payment> paymentList;

    public InMemPaymentRepository() {
        paymentList = new ArrayList<>();
    }

    @Override
    public Optional<Payment> findById(Integer paymentId) {
        if (isNull(paymentId)) {
            throw new IllegalArgumentException("Payment id must not be null");
        }
        return getOriginalPaymentById(paymentId).map(Payment::copyOf);
    }

    @Override
    public List<Payment> findAll() {
        return paymentList.stream()
            .map(Payment::copyOf)
            .collect(toList());
    }

    @Override
    public Payment save(Payment payment) {
        if (isNull(payment)) {
            throw new IllegalArgumentException("Payment must not be null");
        }

        if (nonNull(payment.getId()) && findById(payment.getId()).isPresent()) {
            throw new IllegalArgumentException("Payment with id " + payment.getId() + "already saved");
        }

        payment.setId(isNull(payment.getId()) ? generateId() : payment.getId());
        paymentList.add(Payment.copyOf(payment));

        return payment;
    }

    @Override
    public Payment editAmount(Integer paymentId, Double newAmount) {
        Payment payment = getOriginalPaymentById(paymentId).orElseThrow(
            () -> new NoSuchElementException("Payment with id " + paymentId + " not found"));

        payment.setAmount(newAmount);

        return Payment.copyOf(payment);
    }

    private Integer generateId() {
        return paymentList.stream().mapToInt(Payment::getId).max().orElse(0) + 1;
    }

    private Optional<Payment> getOriginalPaymentById(Integer paymentId) {
        return paymentList.stream().filter(payment -> paymentId.equals(payment.getId())).findFirst();
    }
}
