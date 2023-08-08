package com.gluereply.videostreamingservices.services.validation;

import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.services.impl.RegistrationServiceImpl;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Username Validation Specification")
class UsernameValidatorTest {

    @InjectMocks
    private UsernameValidator validator;

    @Mock
    RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("should return validation result with no rejection reasons for a given non-existing valid username")
    void validate_valid_new_username() {
        User user = createUser();
        when(registrationService.getUserByUsername(anyString())).thenReturn(null);
        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(0);
    }

    @Test
    @DisplayName("should return validation result with one rejection reasons for a given existing valid username ")
    void validate_valid_existing_username() {
        User user = createUser();
        when(registrationService.getUserByUsername(anyString())).thenReturn(user);
        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.USERNAME_EXISTS);
    }

    @Test
    @DisplayName("should return a validation result with one rejection reason for a given non-existing invalid username ")
    void validate_invalid_username() {
        User user = createInvalidUser();
        when(registrationService.getUserByUsername(anyString())).thenReturn(null);

        ValidationResult result = validator.validate(user, new ValidationResult());

        assertThat(result.getRejectionReasons()).hasSize(1);
        assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_USERNAME);


    }



    User createUser() {
        User user = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"));
        return user;
    }

    User createInvalidUser() {
        User user = new User("james 12", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"));
        return user;
    }
}