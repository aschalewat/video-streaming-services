package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.services.impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator extends AbstractValidator {

    @Autowired
    private RegistrationServiceImpl registrationService;

    public void setRegistrationService(RegistrationServiceImpl registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public ValidationResult validate(User user, ValidationResult result) {

        if (user.getUsername().contains(" ")){
            result.getRejectionReasons().add(RejectionReason.INVALID_USERNAME);
        }

        if (registrationService.getUserByUsername(user.getUsername()) != null) {
            result.getRejectionReasons().add(RejectionReason.USERNAME_EXISTS);
        }

        return result;
    }
}
