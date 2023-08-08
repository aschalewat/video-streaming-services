package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Empty Fields Validation Specification")
class EmptyFieldValidatorTest {

    private EmptyFieldValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmptyFieldValidator();
    }

    @Test
    @DisplayName("should return no rejection reason if all fields are filled")
    void validate_valid_with_all_fields_filled() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(0);
    }

    @Test
    @DisplayName("should return no rejection reason if all fields except credit card field are filled")
    void validate_valid_with_all_but_creadit_card_fields_filled() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"), null);

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(0);
    }

    @Test
    @DisplayName("should return one rejection reason if all fields except username field are filled")
    void validate_invalid_with_no_username_field_filled() {
        User user = new User(null, "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.EMPTY_FIELDS);
    }

    @Test
    @DisplayName("should return one rejection reason if all fields except password field are filled")
    void validate_invalid_with_no_password_field_filled() {
        User user = new User("james", null, "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.EMPTY_FIELDS);
    }

    @Test
    @DisplayName("should return one rejection reason if all fields except email field are filled")
    void validate_invalid_with_no_email_field_filled() {
        User user = new User("james", "passwordj", null, LocalDate.parse("1990-12-31"), "1236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.EMPTY_FIELDS);
    }

    @Test
    @DisplayName("should return one rejection reason if all fields except date of birth field are filled")
    void validate_invalid_with_no_dob_field_filled() {
        User user = new User("james", "passwordj", "james@yahoo.com", null, "1236458712364568");

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.EMPTY_FIELDS);
    }
}