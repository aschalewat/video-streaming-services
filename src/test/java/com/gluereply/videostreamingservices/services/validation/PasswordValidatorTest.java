package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Password Validation Specification")
class PasswordValidatorTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }
//min length 8, at least one upper case letter & number

    @Test
    @DisplayName("should have no rejection reason when password is of just 8 characters, at least one Capital, one number and one small letter like 'Validpa1' ")
    void validate_valid_8_chars() {
        User user = createUser();

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(0);
    }

    @Test
    @DisplayName("should have no rejection reason when password is of greater than 8 characters, at least one Capital, one number and small letters like 'Validpassword1' ")
    void validate_valid_greater_than_8_chars() {
        User user = new User("james", "Validpassword1", "james@yahoo.com", LocalDate.parse("1990-12-31"));

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(0);
    }


    @Test
    @DisplayName("should have one rejection reason when password is of greater than 8 characters, no Capital, one number and small letters like 'validpass1' ")
    void validate_invalid_no_upper(){

        User user = new User("james", "validpass1", "james@yahoo.com", LocalDate.parse("1990-12-31"));

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_PASSWORD);
    }

    @Test
    @DisplayName("should have one rejection reason when password is of greater than 8 characters, at least Capital, no number and small letters like 'Validpass' ")
    void validate_invalid_no_digit(){

        User user = new User("james", "Validpass", "james@yahoo.com", LocalDate.parse("1990-12-31"));

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_PASSWORD);
    }

    @Test
    @DisplayName("should have one rejection reason when password is of less than 8 characters, at least Capital, at least number and small letters like 'Validp1' ")
    void validate_invalid_no_less_than_8_chars(){

        User user = new User("james", "Validp1", "james@yahoo.com", LocalDate.parse("1990-12-31"));

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_PASSWORD);
    }






    User createUser() {
        User user = new User("james", "Validpa1", "james@yahoo.com", LocalDate.parse("1990-12-31"));
        return user;
    }

    User createInvalidUser() {
        User user = new User("james 12", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"));
        return user;
    }
}