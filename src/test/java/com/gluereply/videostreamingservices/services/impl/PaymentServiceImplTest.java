package com.gluereply.videostreamingservices.services.impl;

import com.gluereply.videostreamingservices.data.Payment;
import com.gluereply.videostreamingservices.data.PaymentDao;
import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.services.validation.*;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;



@DisplayName("Payment Service Specification")
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;
    @Mock
    RegistrationServiceImpl registrationService;
    @Mock
    PaymentDao paymentDao;
    UsernameValidator usernameValidator;
    CreditCardValidator creditCardValidator;
    AmountValidator amountValidator;


    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        paymentService = spy(PaymentServiceImpl.class);
        paymentService.setPaymentDao(paymentDao);
        paymentService.setRegestrationService(registrationService);
        usernameValidator = new UsernameValidator();
        creditCardValidator = new CreditCardValidator();
        amountValidator = new AmountValidator();

        Class c = PaymentServiceImpl.class;

        Class uvClass = UsernameValidator.class;
        Field uvField = uvClass.getDeclaredField("registrationService");
        uvField.setAccessible(true);
        uvField.set(usernameValidator, registrationService);
        Field usernameValidatorField = c.getDeclaredField("usernameValidator");
        usernameValidatorField.setAccessible(true);
        usernameValidatorField.set(paymentService, usernameValidator);

        Field creditCardValidatorField = c.getDeclaredField("creditCardValidator");
        creditCardValidatorField.setAccessible(true);
        creditCardValidatorField.set(paymentService, creditCardValidator);

        Field amountValidatorField = c.getDeclaredField("amountValidator");
        amountValidatorField.setAccessible(true);
        amountValidatorField.set(paymentService, amountValidator);
    }

    @Test
    @DisplayName("should return no rejection reason if all the payment details are valid")
    void pay_valid_payment() {
        Payment payment = new Payment(1l, "james", "2343876412780987", 102);
        User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "2343876412780987");
        when(registrationService.getUserByUsername("james")).thenReturn(user);

        ValidationResult result = paymentService.pay(payment);

        assertThat(result.getRejectionReasons()).hasSize(0);
    }


    @Test
    @DisplayName("should return one rejection reason(INVALID_AMOUNT) if the payment details has invalid amount")
    void pay_invalid_amount() {
        Payment payment = new Payment(1l, "james", "2343876412780987", 10);
        User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "2343876412780987");
        when(registrationService.getUserByUsername("james")).thenReturn(user);

        ValidationResult result = paymentService.pay(payment);

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("should return one rejection reason(UNREGISTERED_CREDIT_CARD) if the payment details has unregistered credit card number")
    void pay_unregistered_credit_card() {
        Payment payment = new Payment(1l, "james", "2343876412780999", 100);
        User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "2343876412780987");
        when(registrationService.getUserByUsername("james")).thenReturn(user);

        ValidationResult result = paymentService.pay(payment);

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.UNREGISTERED_CREDIT_CARD);
    }



}