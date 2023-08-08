package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator extends AbstractValidator {
    @Override
    public ValidationResult validate(User user, ValidationResult result) {

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        String email = user.getEmail();

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()){
            result.getRejectionReasons().add(RejectionReason.INVALID_EMAIL);
        }
        return result;
    }

}
