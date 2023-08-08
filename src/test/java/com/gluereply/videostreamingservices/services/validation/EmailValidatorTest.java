package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Email Validation Specification")
class EmailValidatorTest {


    EmailValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmailValidator();
    }

    @Test
    @DisplayName("should return no rejection result for a given valid email format")
    void validate_valid_email_format() {
        User user = createUser();

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(0);
    }

    @Test
    @DisplayName("should return one rejection result for a given invalid email format")
    void validate_invalid_email_format() {
        User user = createInvalidUser();

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_EMAIL);
    }


    User createUser() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"));
        return user;
    }

    User createInvalidUser() {
        User user = new User("james", "passwordj", "@james@yahoo.com", LocalDate.parse("1990-12-31"));
        return user;
    }
}