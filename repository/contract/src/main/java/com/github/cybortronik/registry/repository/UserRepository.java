package com.github.cybortronik.registry.repository;

import com.github.cybortronik.registry.bean.User;

import java.util.UUID;

/**
 * Created by stanislav on 10/28/15.
 */
public interface UserRepository extends Repository<User, String> {

    UUID createUser(String displayName, String email, String passwordHash);

    User findByEmail(String email);

    void addUserRole(String userId, String role);
}
