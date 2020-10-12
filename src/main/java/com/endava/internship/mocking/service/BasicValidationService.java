package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;

public class BasicValidationService implements ValidationService {

    @Override
    public void validateAmount(Double amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount must not be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    @Override
    public void validatePaymentId(Integer paymentId) {
        if (paymentId == null) {
            throw new IllegalArgumentException("Payment id must not be null");
        }
    }

    @Override
    public void validateUserId(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id must not be null");
        }
    }

    @Override
    public void validateUser(User user) {
        if (user.getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("User with id " + user.getId() + " not in ACTIVE status");
        }
    }
}
