package com.gluereply.videostreamingservices.services.impl;

import com.gluereply.videostreamingservices.data.Payment;
import com.gluereply.videostreamingservices.data.PaymentDao;
import com.gluereply.videostreamingservices.data.PaymentDaoImpl;
import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.services.PaymentService;
import com.gluereply.videostreamingservices.services.RegestrationService;
import com.gluereply.videostreamingservices.services.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    RegestrationService regestrationService;
    @Autowired
    CreditCardValidator creditCardValidator;
    @Autowired
    UsernameValidator usernameValidator;
    @Autowired
    AmountValidator amountValidator;

    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public void setRegestrationService(RegestrationService regestrationService) {
        this.regestrationService = regestrationService;
    }

    @Override
    public ValidationResult pay(Payment payment) {
        ValidationResult result = new ValidationResult();
        User user = regestrationService.getUserByUsername(payment.getUsername());

        if (user.getCreditCard().equals(payment.getCreditCard())){
            creditCardValidator.validate(user, result);
        } else  {
            creditCardValidator.validate(payment.getCreditCard(), result);
            if (result.getRejectionReasons().size() < 1) {
                result.getRejectionReasons().add(RejectionReason.UNREGISTERED_CREDIT_CARD);
            }
        }
        amountValidator.validate(payment, result);

        if (result.getRejectionReasons().size() < 1){
            paymentDao.save(payment);
        }

        return result;
    }
}
