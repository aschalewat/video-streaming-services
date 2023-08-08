package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Credit Card Validation Specification")
class CreditCardValidatorTest {

    private CreditCardValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CreditCardValidator();
    }

    @Test
    @DisplayName("should return no rejection reason if the entered credit card characters are all digits has length of 16")
    void validate_valid_credit_card() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(0);
    }

    @Test
    @DisplayName("should return one rejection reason if the entered credit card characters are all digits but has length of 15")
    void validate_credit_card_length_15() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"), "236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_CREDIT_CARD);
    }

    @Test
    @DisplayName("should return one rejection reason if the entered credit card characters are all digits but has length of 17")
    void validate_credit_card_length_17() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"), "12364587123645689");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_CREDIT_CARD);
    }

    @Test
    @DisplayName("should return one rejection reason if the entered credit card characters have letters and digits")
    void validate_credit_card_has_letters() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"), "ab6458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_CREDIT_CARD);
    }
}