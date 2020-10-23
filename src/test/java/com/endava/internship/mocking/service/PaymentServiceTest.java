package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

@Mock
 PaymentRepository paymentRepository;

@Mock
 UserRepository userRepository;

@Mock
 ValidationService validationService;

@InjectMocks
 PaymentService paymentService;


 User user;
 User user2;
 Payment payment;
 Payment payment2;
 Double amount;
 Double amount2;
 List<Payment> paymentList;

  @BeforeEach
  void setUp() {
   amount = 100.0;
   amount2 = 200.0;

   user = new User(1,"Ivan", Status.ACTIVE);
   user2 = new User(2,"Nika", Status.ACTIVE);

   payment = new Payment(null,user.getId(),amount);
   payment2 = new Payment(null,user2.getId(),amount2);
   paymentList = Stream.of(payment,payment2).collect(Collectors.toList());

  }

  @Test
  void createPayment() {

   Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
   Mockito.when(paymentRepository.save(payment)).thenReturn(payment);
   Mockito.doNothing().when(validationService).validateUserId(user.getId());
   Mockito.doNothing().when(validationService).validateAmount(any());

   Payment paymentMock = paymentService.createPayment(user.getId(), amount);
   ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
   Mockito.verify(paymentRepository).save(captor.capture());
   Payment p = captor.getValue();

   assertThat(user.getId()).isEqualTo(paymentMock.getUserId());
   assertThat(user2.getId()).isNotEqualTo(paymentMock.getUserId());
   assertThat(user.getId()).isEqualTo(p.getUserId());

   Mockito.verify(validationService).validateUserId(any());
   Mockito.verify(validationService).validateAmount(any());

  }

  @Test
  void editAmount() {

   payment.setId(11);

   Mockito.when(paymentRepository.editAmount(11,amount)).thenReturn(payment);
   Mockito.doNothing().when(validationService).validatePaymentId(any());
   Mockito.doNothing().when(validationService).validateAmount(any());

   Payment payment1 = paymentService.editAmount(11,100.0);

   assertThat(payment1).isEqualTo(payment);

   Mockito.verify(paymentRepository).editAmount(11,100.0);
  }

  @Test
  void getAllByAmountExceeding() {

   Mockito.when(paymentRepository.findAll()).thenReturn((paymentList));

   List<Payment> rez = paymentService.getAllByAmountExceeding(0.5);

   assertThat(rez).isEqualTo(paymentList);

   Mockito.verify(paymentRepository).findAll();
  }
}
