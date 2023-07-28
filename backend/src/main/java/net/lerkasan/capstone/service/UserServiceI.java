package net.lerkasan.capstone.service;

import net.lerkasan.capstone.model.User;

import java.util.List;

public interface UserServiceI {

    String USER_NOT_FOUND_BY_ID = "A user with the id %d not found%n";
    String USER_NOT_FOUND = "A user %s not found%n";

    String INVALID_USER_ID = "User id must be greater than 0";

    User create(User user);

    List<User> findAll();

    User findByToken(String token);

    User update(User user);

    void delete(final long id);

    void delete(User user);

    User findById(long id);

    User findByUsername(String username);


    User findByEmail(String email);

    User getCurrentUser();

    boolean isEmailAvailable(String email);

    boolean isUsernameAvailable(String username);
}