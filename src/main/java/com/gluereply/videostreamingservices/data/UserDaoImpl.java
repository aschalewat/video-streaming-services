package com.gluereply.videostreamingservices.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * a simple in memory implementation of {@link UserDao}
 */
@Component
public class UserDaoImpl implements UserDao {

    private List<User> users = new ArrayList<>();

    @Override
    public User getUser(String username) {
        List<User> user = users.stream()
                .filter(user1 -> user1.getUsername().equals(username))
                .collect(Collectors.toList());
        if (user.size() > 0) {
            return user.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public List<User> getUsers(Boolean hasCreditCard) {
        if (hasCreditCard == null){
            return getAll();
        }
       if (hasCreditCard){
           return users.stream()
                   .filter(user -> user.getCreditCard() != null)
                   .collect(Collectors.toList());
       } else {
           return users.stream()
                   .filter(user -> user.getCreditCard() == null)
                   .collect(Collectors.toList());
       }
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }
}
