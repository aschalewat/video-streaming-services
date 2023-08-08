package com.gluereply.videostreamingservices.services;

import com.gluereply.videostreamingservices.data.Payment;
import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.services.validation.ValidationResult;

import java.util.List;

public interface PaymentService {

   ValidationResult pay(Payment payment);
}
