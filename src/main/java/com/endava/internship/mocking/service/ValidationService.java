package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.User;

public interface ValidationService {
    void validateAmount(Double amount);

    void validatePaymentId(Integer paymentId);

    void validateUserId(Integer userId);

    void validateUser(User user);
}
