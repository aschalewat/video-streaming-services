package com.gluereply.videostreamingservices.data;

import java.util.List;
import java.util.Optional;

/**
 * a repository in charge of user data
 */
public interface UserDao {

    /**
     * gets a user by its username
     * @param username of the user we want to retrieve
     * @return a {@link User}. If no user can be found by the given username will return empty.
     */
    User getUser(String username);

    /**
     * gets the list of all users which are currently stored in the repository
     * @return a {@link List}
     */
    List<User> getAll();

    /**
     * gets users based on the given filter
     * @param hasCreditCard
     * @return a {@link List} of users with or without credit card, if filter null then return all registered users
     */
    List<User> getUsers(Boolean hasCreditCard);

    /**
     * saves the gives user
     * @param user the {@link User} to be registered
     */
    void save(User user);

    void clear();

    void  deleteUser(User user);

}
