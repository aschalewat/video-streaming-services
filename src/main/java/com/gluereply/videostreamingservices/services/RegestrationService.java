package com.gluereply.videostreamingservices.services;

import com.gluereply.videostreamingservices.data.User;
import com.gluereply.videostreamingservices.services.validation.ValidationResult;

import java.util.List;

public interface RegestrationService {


    ValidationResult register(User user);
    List<User> getUsers(Boolean hasCreditCard);
    User getUserByUsername(String username);
}
