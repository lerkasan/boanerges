package net.lerkasan.capstone.service;

import net.lerkasan.capstone.model.User;

import java.util.List;

public interface UserServiceI {

    User create(User user);

    List<User> findAll();

    User findByToken(String token);

    User update(User user);

    void delete(final long id);

    void delete(User user);

    boolean isEmailAvailable(String email);

    boolean isUsernameAvailable(String username);
}