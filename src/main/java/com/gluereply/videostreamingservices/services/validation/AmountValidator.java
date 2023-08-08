package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.Payment;
import com.gluereply.videostreamingservices.data.User;
import org.springframework.stereotype.Component;

@Component
public class AmountValidator extends AbstractValidator {

    @Override
    public ValidationResult validate(User user, ValidationResult result) {
        return null;
    }

}
