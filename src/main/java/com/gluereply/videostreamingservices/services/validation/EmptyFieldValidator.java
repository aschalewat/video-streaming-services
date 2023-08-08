package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import org.springframework.stereotype.Component;

@Component
public class EmptyFieldValidator extends  AbstractValidator {
    @Override
    public ValidationResult validate(User user, ValidationResult result) {

        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null || user.getDob() == null){
            result.getRejectionReasons().add(RejectionReason.EMPTY_FIELDS);
        }

        return result;
    }
}
