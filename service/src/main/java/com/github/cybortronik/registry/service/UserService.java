package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Login;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.bean.UserRequest;
import com.github.cybortronik.registry.repository.UserFilter;

import java.util.List;
import java.util.UUID;

/**
 * Created by stanislav on 10/28/15.
 */
public interface UserService {
    void deleteAll();

    User findById(String uuid);

    User find(Login login);

    UUID createUser(String displayName, String email, String password, String details);

    User createUser(UserRequest userRequest);

    void addRoleToUser(String role, String email);

    User updateUser(String uuid, UserRequest request);

    void delete(String uuid);

    List<User> filter(UserFilter userFilter);

    void createUser(String email, String email1, String password);
}
