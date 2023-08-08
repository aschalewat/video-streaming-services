package com.gluereply.videostreamingservices.controllers;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.gluereply.videostreamingservices.data.Payment;
import com.gluereply.videostreamingservices.services.PaymentService;
import com.gluereply.videostreamingservices.services.validation.RejectionReason;
import com.gluereply.videostreamingservices.services.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;


    @PostMapping("/pay")
    ResponseEntity<ValidationResult> pay(@Valid @RequestBody Payment payment){
        ValidationResult result = paymentService.pay(payment);
        if (result.getRejectionReasons().size() == 0){
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else if (result.getRejectionReasons().contains(RejectionReason.UNREGISTERED_CREDIT_CARD) && result.getRejectionReasons().size() == 1 ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}
