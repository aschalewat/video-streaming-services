package com.gluereply.videostreamingservices.services.impl;

import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.data.UserDao;
import com.gluereply.videostreamingservices.data.UserDaoImpl;
import com.gluereply.videostreamingservices.services.validation.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RegistrationService Specification")
class RegistrationServiceImplTest {

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Mock
    UserDao userDao;

    AgeValidator ageValidator;
    UsernameValidator usernameValidator;
    PasswordValidator passwordValidator;
    EmptyFieldValidator emptyFieldValidator;
    CreditCardValidator creditCardValidator;
    EmailValidator emailValidator;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        registrationService = spy(RegistrationServiceImpl.class);
        registrationService.setUserDao(userDao);
        ageValidator = new AgeValidator();
        usernameValidator = new UsernameValidator();
        passwordValidator = new PasswordValidator();
        emptyFieldValidator = new EmptyFieldValidator();
        creditCardValidator = new CreditCardValidator();
        emailValidator = new EmailValidator();

        Class c = RegistrationServiceImpl.class;

        Field ageValidatorField = c.getDeclaredField("ageValidator");
        ageValidatorField.setAccessible(true);
        ageValidatorField.set(registrationService, ageValidator);

        Class usernameClaz = UsernameValidator.class;
        Field registrationServiceField = usernameClaz.getDeclaredField("registrationService");
        registrationServiceField.setAccessible(true);
        registrationServiceField.set(usernameValidator, registrationService);

        Field usernameValidatorField = c.getDeclaredField("usernameValidator");
        usernameValidatorField.setAccessible(true);
        usernameValidatorField.set(registrationService, usernameValidator);

        Field passwordValidatorField = c.getDeclaredField("passwordValidator");
        passwordValidatorField.setAccessible(true);
        passwordValidatorField.set(registrationService, passwordValidator);

        Field emptyFieldValidatorField = c.getDeclaredField("emptyFieldValidator");
        emptyFieldValidatorField.setAccessible(true);
        emptyFieldValidatorField.set(registrationService, emptyFieldValidator);

        Field creditCardValidatorField = c.getDeclaredField("creditCardValidator");
        creditCardValidatorField.setAccessible(true);
        creditCardValidatorField.set(registrationService, creditCardValidator);

        Field emailValidatorField = c.getDeclaredField("emailValidator");
        emailValidatorField.setAccessible(true);
        emailValidatorField.set(registrationService, emailValidator);
    }

    @Nested
    @DisplayName("Registering Customer")
    class RegisterCustomer {

        @Test
        @DisplayName("should return no rejection reason if all fields are filled and valid")
        void register_valid_user() {
            User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

            ValidationResult result = registrationService.register(user);

            assertThat(result.getRejectionReasons()).hasSize(0);
            verify(userDao).save(user);
        }

        @Test
        @DisplayName("should return one rejection reason(USERNAME_EXISTS) if all fields are filled and valid but the username already exists")
        void register_user_exists() {
            User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");
            when(userDao.getUser("james")).thenReturn(user);

            ValidationResult result = registrationService.register(user);

            assertThat(result.getRejectionReasons()).hasSize(1);
            assertThat(result.getRejectionReasons()).contains(RejectionReason.USERNAME_EXISTS);
        }

        @Test
        @DisplayName("should return no rejection reason if all fields but credit card are filled and valid")
        void register_valid_user_without_credit_card() {
            User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"));


            ValidationResult result = registrationService.register(user);

            assertThat(result.getRejectionReasons()).hasSize(0);
            verify(userDao).save(user);
        }

        @Test
        @DisplayName("should return one rejection reason(INVALID_USERNAME) if all fields are filled and invalid username")
        void register_invalid_user_invalid_username() {
            User user = new User("james 12", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

            ValidationResult result = registrationService.register(user);

            assertThat(result.getRejectionReasons()).hasSize(1);
            assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_USERNAME);
        }

        @Test
        @DisplayName("should return one rejection reason(INVALID_PASSWORD) if all fields are filled and invalid password")
        void register_invalid_user_invalid_password() {
            User user = new User("james", "password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

            ValidationResult result = registrationService.register(user);

            assertThat(result.getRejectionReasons()).hasSize(1);
            assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_PASSWORD);
        }

        @Test
        @DisplayName("should return one rejection reason(INVALID_EMAIL) if all fields are filled and invalid email")
        void register_invalid_user_invalid_email() {
            User user = new User("james", "Password12", "@james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

            ValidationResult result = registrationService.register(user);

            assertThat(result.getRejectionReasons()).hasSize(1);
            assertThat(result.getRejectionReasons()).contains(RejectionReason.INVALID_EMAIL);
        }

        @Test
        @DisplayName("should return one rejection reason(EMPTY_FIELDS) if some fields are not filled")
        void register_invalid_user_empty_fields() {
            User user = new User(null, "Password12", null, LocalDate.parse("1990-12-31"), "1236458712364568");

            ValidationResult result = registrationService.register(user);

            assertThat(result.getRejectionReasons()).hasSize(1);
            assertThat(result.getRejectionReasons()).contains(RejectionReason.EMPTY_FIELDS);
        }

        @Test
        @DisplayName("should return one rejection reason(UNDER_AGE) if date of birth field computes to age less than 18")
        void register_invalid_user_age_under_18() {
            User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("2003-12-31"), "1236458712364568");

            ValidationResult result = registrationService.register(user);

            assertThat(result.getRejectionReasons()).hasSize(1);
            assertThat(result.getRejectionReasons()).contains(RejectionReason.UNDER_AGE);
        }
    }

    @Nested
    @DisplayName("Getting customers based on credit card filter")
    class FilteredCustomers {
        @Test
        @DisplayName("should return a list of all registered users with credit card")
        void getUsers_with_credit_card() {

            // given
            User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");
            User user1 = new User("john", "Password45", "john@yahoo.com", LocalDate.parse("1975-12-31"), "6636458712364555");
            List<User> users = new ArrayList<>();
            users.add(user);
            users.add(user1);

            // when
            when(userDao.getUsers(true)).thenReturn(users);
            List<User> actual = registrationService.getUsers(true);

            // then
            assertThat(actual).hasSize(2);
            assertThat(actual).contains(user);
            assertThat(actual).contains(user1);
        }

        @Test
        @DisplayName("should return a list of registered users without credit card")
        void getUsers_without_credit_card() {
            User user1 = new User("david", "Password23", "david@yahoo.com", LocalDate.parse("1980-12-31"));
            List<User> users = new ArrayList<>();
            users.add(user1);
            when(userDao.getUsers(false)).thenReturn(users);
            List<User> actual = registrationService.getUsers(false);

            assertThat(actual).hasSize(1);
            assertThat(actual).contains(user1);
        }

        @Test
        @DisplayName("should return a list of all registered users with and without credit card")
        void getUsers_with_and_without_credit_card() throws Exception {
            User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");
            User user1 = new User("david", "Password23", "david@yahoo.com", LocalDate.parse("1980-12-31"));
            List<User> users = new ArrayList<>();
            users.add(user);
            users.add(user1);

            when(userDao.getUsers(null)).thenReturn(users);
            List<User> actual = registrationService.getUsers(null);

            assertThat(actual).hasSize(2);
            assertThat(actual).contains(user);
            assertThat(actual).contains(user1);
        }
    }

    @Nested
    @DisplayName("Getting Customer by username")
    class CustomerByUsername {
        @Test
        @DisplayName("should return a registered user for a given username")
        void getUserByUsername_username_exists() {
            User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

            when(userDao.getUser("james")).thenReturn(user);

            User result = registrationService.getUserByUsername("james");

            assertThat(result.getEmail()).isEqualTo(user.getEmail());

        }

        @Test
        @DisplayName("should return null if username does not exist")
        void getUserByUsername_username_dos_not_exist() {
            User user = new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568");

            when(userDao.getUser("james")).thenReturn(user);

            User result = registrationService.getUserByUsername("james1");

            assertThat(result).isEqualTo(null);

        }
    }

}