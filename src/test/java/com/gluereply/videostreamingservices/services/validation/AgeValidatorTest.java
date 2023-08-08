package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Date of Birth Validation Specification")
class AgeValidatorTest {

    private AgeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AgeValidator();
    }

    @Test
    @DisplayName("should return no rejection reason if the given date of birth computes to 18 years or above")
    void validate_18_years() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("2002-09-25"), "236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(0);
    }


    @Test
    @DisplayName("should return no rejection reason if the given date of birth computes to 19 years or above")
    void validate_19_years() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("2001-09-25"), "236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(0);
    }

    @Test
    @DisplayName("should return one rejection reason if the given date of birth computes to 17 years")
    void validate_17_years() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("2003-09-25"), "236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.UNDER_AGE);
    }

    @Test
    @DisplayName("should return one rejection reason if the given date of birth computes to 17 years and eleven months")
    void validate_17_years_11_months() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("2002-08-25"), "236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.UNDER_AGE);
    }


}