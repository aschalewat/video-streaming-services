package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.Payment;
import com.gluereply.videostreamingservices.data.User;
import org.springframework.stereotype.Component;

@Component
public class CreditCardValidator extends AbstractValidator {
    @Override
    public ValidationResult validate(User user, ValidationResult result) {
        boolean isLetter = false;
        if (user.getCreditCard() != null || !user.getCreditCard().isEmpty()){
            return validate(user.getCreditCard(), result);
        }
        return result;
    }

    private boolean isLetter(String creditCard){
        boolean isLetter = false;
        for (char aChar:creditCard.toCharArray()){
            if (Character.isLetter(aChar)){
                isLetter = true;
            }
        }
        return isLetter;
    }


    public ValidationResult validate(String creditCard, ValidationResult result){
        if(isLetter(creditCard)){
            result.getRejectionReasons().add(RejectionReason.INVALID_CREDIT_CARD);
            return result;
        }
        if (creditCard.length() != 16){
            result.getRejectionReasons().add(RejectionReason.INVALID_CREDIT_CARD);
            return result;
        }
        return result;
    }


}
