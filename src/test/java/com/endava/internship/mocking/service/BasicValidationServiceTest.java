package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class BasicValidationServiceTest {

    BasicValidationService validationService;

    @BeforeEach
    void setUp(){
        validationService = new BasicValidationService();
    }

    @Test
    void validateAmount_Throw_Exception_When_Param_is_Less_or_Equals_0() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validationService.validateAmount(null))
                .withMessage("Amount must not be null") ,

                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy( ()-> validationService.validateAmount(-1.0))
                .withMessage("Amount must be greater than 0")

        );
    }

    @Test
    void validatePaymentId() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validationService.validatePaymentId(null))
        .withMessage("Payment id must not be null");
    }

    @Test
    void validateUserId() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validationService.validateUserId(null))
                .withMessage("User id must not be null");
    }

    @Test
    void validateUser() {
        User user = new User(1,"Nicola", Status.INACTIVE);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validationService.validateUser(user))
                .withMessage("User with id " + user.getId() + " not in ACTIVE status");
    }
}