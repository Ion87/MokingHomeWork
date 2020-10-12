package com.endava.internship.mocking.model;

import java.util.Objects;

public class Payment {

    private Integer id;
    private Integer userId;
    private Double amount;

    public Payment(Integer id, Integer userId, Double amount) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
    }

    public static Payment copyOf(Payment originalPayment) {
        return new Payment(originalPayment.id, originalPayment.userId, originalPayment.amount);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
            Objects.equals(userId, payment.userId) &&
            Objects.equals(amount, payment.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, amount);
    }
}
