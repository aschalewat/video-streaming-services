package com.gluereply.videostreamingservices.data;

//import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private UserDaoImpl userDao;

    @BeforeEach
    void setup(){
        userDao = new UserDaoImpl();
    }

    @Test
    @DisplayName("should save the given user")
    void save() {
        User user = new User("david", "passwordd", "david@yahoo.com", LocalDate.parse("1990-12-31"), "2343876412780987");

        userDao.save(user);
        List<User> actual = userDao.getAll();

        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("should return all saved users")
    void getAll() {
        User user1 = new User("david", "passwordd", "david@yahoo.com", LocalDate.parse("1990-12-31"), "2343876412780987");
        User user2 = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"));

        userDao.save(user1);
        userDao.save(user2);

        List<User> actual = userDao.getAll();

        assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("should return all users with credit card")
    void getUsers_with_credit_card() {

        User user1 = new User("david", "passwordd", "david@yahoo.com", LocalDate.parse("1990-12-31"), "2343876412780987");
        User user2 = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"));

        userDao.save(user1);
        userDao.save(user2);

        List<User> actual  = userDao.getUsers(true);

        assertThat(actual).hasSize(1);
        assertThat(actual).contains(user1);
        assertThat(actual).doesNotContain(user2);

    }

    @Test
    @DisplayName("should return all users without credit card")
    void getUsers_without_credit_card() {

        User user1 = new User("david", "passwordd", "david@yahoo.com", LocalDate.parse("1990-12-31"), "2343876412780987");
        User user2 = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"));

        userDao.save(user1);
        userDao.save(user2);

        List<User> actual  = userDao.getUsers(false);

        assertThat(actual).hasSize(1);
        assertThat(actual).doesNotContain(user1);
        assertThat(actual).contains(user2);
    }

    @Test
    @DisplayName("should return all registered users with and without credit card")
    void getUsers_with_and_without_credit_card() {

        User user1 = new User("david", "passwordd", "david@yahoo.com", LocalDate.parse("1990-12-31"), "2343876412780987");
        User user2 = new User("james", "passwordj", "james@yahoo.com", LocalDate.parse("1990-12-31"));

        userDao.save(user1);
        userDao.save(user2);

        List<User> actual  = userDao.getUsers(null);

        assertThat(actual).hasSize(2);
        assertThat(actual).contains(user1);
        assertThat(actual).contains(user2);
    }
}