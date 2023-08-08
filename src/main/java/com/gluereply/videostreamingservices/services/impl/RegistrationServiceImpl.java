package com.gluereply.videostreamingservices.services.impl;

import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.data.UserDao;
import com.gluereply.videostreamingservices.data.UserDaoImpl;
import com.gluereply.videostreamingservices.services.RegestrationService;
import com.gluereply.videostreamingservices.services.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegestrationService {

    @Autowired
    UserDao userDao;

    @Autowired
    CreditCardValidator creditCardValidator;
    @Autowired
    EmailValidator emailValidator;
    @Autowired
    EmptyFieldValidator emptyFieldValidator;
    @Autowired
    PasswordValidator passwordValidator;
    @Autowired
    UsernameValidator usernameValidator;
    @Autowired
    AgeValidator ageValidator;

   /* public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }*/

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ValidationResult register(User user) {
        ValidationResult validationResult = new ValidationResult();

        emptyFieldValidator.validate(user, validationResult);
        if (validationResult.getRejectionReasons().size() > 0){
            return validationResult;
        }
        emailValidator.validate(user, validationResult);

        passwordValidator.validate(user, validationResult);

        usernameValidator.validate(user, validationResult);

        ageValidator.validate(user, validationResult);

        if (user.getCreditCard() != null) {
            creditCardValidator.validate(user, validationResult);
        }

        if (validationResult.getRejectionReasons().size() < 1){
            userDao.save(user);
        }
        return validationResult;
    }

    @Override
    public List<User> getUsers(Boolean hasCreditCard) {
        return userDao.getUsers(hasCreditCard);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUser(username);
    }
}
