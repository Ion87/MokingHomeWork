package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class InMemPaymentRepositoryTest {

    InMemPaymentRepository paymentRepository;

    Double amount1;
    Double amount2;
    Double amount3;

    User user1 ;
    User user2 ;
    User user3 ;

    Payment payment1 ;
    Payment payment2 ;
    Payment payment3 ;
    Payment paymentNull;

    @BeforeEach
    void setUp() {

        amount1 = 10.0;
        amount2 = 20.0;
        amount3 = 30.0;

        user1 = new User(1,"Ivan", Status.ACTIVE);
        user2 = new User(2,"Sergiu", Status.ACTIVE);
        user3 = new User(3,"Grigore", Status.INACTIVE);

        payment1 = new Payment(1, user1.getId(), amount1);
        payment2 = new Payment(2, user1.getId(), amount2);
        payment3 = new Payment(3, user1.getId(), amount3);


        paymentRepository = new InMemPaymentRepository();

    }

    @Test
    void findById_Return_Payment() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        Payment testPayment = paymentRepository.findById(user1.getId()).get();

        assertAll(
                ()-> assertThat(testPayment).isEqualTo(payment1),
                ()-> assertThat(testPayment).isNotEqualTo(payment2)
        );
    }

    @Test
    void findById_Return_Throw_When_Param_is_Null() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->paymentRepository.findById(null));
    }

    @Test
    void findAll_When_is_Not_Empty() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        List<Payment> testList = paymentRepository.findAll();

        assert(testList.size() == 2
                && testList.contains(payment1)
                && testList.contains(payment2));
    }

    @Test
    void findAll_When_is_Empty() {

        List<Payment> testList = paymentRepository.findAll();

        assertEquals(testList.size(), 0);
    }
    @Test
    void save_Throw_Exception_When_Param_is_Null() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->paymentRepository.save(null));
    }

    @Test
    void save_Throw_Exception_When_Param_Exists() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->paymentRepository.save(payment1))
                .withMessage("Payment with id " + payment1.getId() + "already saved");
    }

    @Test
    void testSave(){

        assertThat(paymentRepository.save(payment1)).isEqualTo(payment1);
    }

    @Test
    void editAmount_With_Existing_Id() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        paymentRepository.editAmount(payment1.getId(), payment3.getAmount());
        Payment testPayment = paymentRepository.findById(payment1.getId()).get();

        assertEquals(payment3.getAmount(),testPayment.getAmount());
    }

    @Test
    void editAmount_Throw_Exception_With_NonExisting_Id(){

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(()->paymentRepository.editAmount(payment2.getId(),payment1.getAmount()))
        .withMessage("Payment with id " + payment2.getId() + " not found");
    }

}