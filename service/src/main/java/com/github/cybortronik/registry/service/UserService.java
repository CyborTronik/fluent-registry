package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Login;
import com.github.cybortronik.registry.bean.User;

/**
 * Created by stanislav on 10/28/15.
 */
public interface UserService {
    void deleteAll();

    User findById(String uuid);

    User find(Login login);

    void createUser(String displayName, String email, String password);
}
