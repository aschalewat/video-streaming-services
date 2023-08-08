package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.Payment;
import com.gluereply.videostreamingservices.data.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AgeValidator extends AbstractValidator {

    public AgeValidator(){

    }

    @Override
    public ValidationResult validate(User user, ValidationResult result) {

        LocalDate dob = user.getDob();
        if (isUnderAge(dob)) {
            result.getRejectionReasons().add(RejectionReason.UNDER_AGE);
        }
        return result;
    }

    private boolean isUnderAge(LocalDate dob) {
        LocalDate today = LocalDate.now();
        boolean isMiner = false;
        if (today.getYear() - dob.getYear() < 18) {
            isMiner = true;
        } else if(today.getYear() - dob.getYear() == 18){
            if (dob.getMonthValue() < today.getMonthValue()) {
                isMiner = true;
            } else if (dob.getMonthValue() == today.getMonthValue())
                if (dob.getDayOfMonth() > today.getDayOfMonth()) {
                    isMiner = true;
                }
        }

        return isMiner;
    }

}
