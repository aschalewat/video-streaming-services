package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator extends AbstractValidator {
    @Override
    public ValidationResult validate(User user, ValidationResult result) {

        String regex = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,})";
        String password = user.getPassword();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            result.getRejectionReasons().add(RejectionReason.INVALID_PASSWORD);
        }
        return result;
    }
}
