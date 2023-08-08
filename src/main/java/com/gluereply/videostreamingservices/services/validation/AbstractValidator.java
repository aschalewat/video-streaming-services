package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.Payment;
import com.gluereply.videostreamingservices.data.User;

public abstract class AbstractValidator {

    public abstract ValidationResult validate(User user, ValidationResult result);


    public ValidationResult validate(Payment payment, ValidationResult result){
        int amount  = payment.getAmount();
        if (amount < 0 || !checkDigits(amount)) {
            result.getRejectionReasons().add(RejectionReason.INVALID_AMOUNT);
        }
        return result;
    }

    private boolean checkDigits(int amount) {
        String val = String.valueOf(amount);
        char[] chars = val.toCharArray();
        if (chars.length == 3){
            return true;
        } else {
            return false;
        }
    }
}
