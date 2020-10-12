package com.endava.internship.mocking.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;

public class PaymentService {

    private UserRepository userRepository;
    private PaymentRepository paymentRepository;
    private ValidationService validationService;

    public PaymentService(UserRepository userRepository, PaymentRepository paymentRepository,
                          ValidationService validationService) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.validationService = validationService;
    }

    public Payment createPayment(Integer userId, Double amount) {
        validationService.validateUserId(userId);
        validationService.validateAmount(amount);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        validationService.validateUser(user);

        Payment payment = new Payment(null, user.getId(), amount);
        return paymentRepository.save(payment);
    }

    public Payment editAmount(Integer paymentId, Double newAmount) {
        validationService.validatePaymentId(paymentId);
        validationService.validateAmount(newAmount);

        return paymentRepository.editAmount(paymentId, newAmount);
    }

    public List<Payment> getAllByAmountExceeding(Double amount) {
        return paymentRepository.findAll()
            .stream()
            .filter(payment -> payment.getAmount() > amount)
            .collect(Collectors.toList());
    }
}
